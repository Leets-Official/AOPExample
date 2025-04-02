package land.leets.AOPExample.aop;

import jakarta.servlet.http.HttpServletRequest;
import java.util.Optional;
import land.leets.AOPExample.exception.UnAuthorizedException;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class AuthAspect {

  @Around("@annotation(AuthRequired)")
  public Object checkAuth(ProceedingJoinPoint joinPoint) throws Throwable {
    // HttpServletRequest에서 Authorization 헤더 추출
    HttpServletRequest httpServletRequest = (HttpServletRequest) joinPoint.getArgs()[0];
    Optional<String> extractedAuthHeader = extractAuthHeader(httpServletRequest);

    // Authorization 헤더가 없으면 인증되지 않은 유저이므로 예외 발생
    if (extractedAuthHeader.isEmpty()) {
      throw new UnAuthorizedException();
    }

    // Authorization 헤더가 있지만, 해당 값이 false이거나 boolean 값이 아니면 인증되지 않은 유저이므로 예외 발생
    String authHeader = extractedAuthHeader.get();
    boolean isAuthenticated = Boolean.parseBoolean(authHeader);
    if (!isAuthenticated) {
      throw new UnAuthorizedException();
    }

    // 인증된 유저이므로 다음 로직 실행
    Object requestResult = joinPoint.proceed();

    // 모든 로직을 실행한 후 결과 값을 그대로 반환
    return requestResult;
  }

  private Optional<String> extractAuthHeader(HttpServletRequest httpServletRequest) {
    try {
      return Optional.of(
          httpServletRequest.getHeader("Authorization").substring("Bearer ".length()));
    } catch (Exception e) {
      return Optional.empty();
    }
  }
}
