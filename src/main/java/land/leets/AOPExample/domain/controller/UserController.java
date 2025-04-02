package land.leets.AOPExample.domain.controller;

import land.leets.AOPExample.aop.AuthRequired;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

  @GetMapping("/auth")
  @AuthRequired // 인증이 필요한 API
  public String getUser() {
    return "당신은 인증된 사용자입니다.";
  }

  @GetMapping("/non-auth") // 인증이 필요 없는 API
  public String getNonAuthUser() {
    return "당신은 인증되지 않은 사용자입니다.";
  }
}
