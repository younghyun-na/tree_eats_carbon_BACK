package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import smu.earthranger.dto.UserSignupDto;
import smu.earthranger.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import smu.earthranger.service.MemberService;

@Controller
@RequiredArgsConstructor
@RequestMapping("/users")
public class LoginController {

    private final UserService userService;

    //회원가입
    @PostMapping("/signup")
    public String signup(@Valid UserSignupDto userSignupDto, BindingResult bindingResult) {
        userService.save(userSignupDto);
        return "redirect:/login";

    // 회원 가입 페이지로 이동
    @GetMapping("/signup")
    public String signup() {
        return "/signup";
    }

    // 로그인 페이지로 이동
    @GetMapping("/login")
    public String login() {
        return "/login";
    }

    // 회원 정보 수정 페이지로 이동
    @GetMapping("/update")
    public String update() {
        return "/update";
    }
}
