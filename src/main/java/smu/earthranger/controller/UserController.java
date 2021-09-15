package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import smu.earthranger.dto.user.UserUpdateDto;
import smu.earthranger.service.UserService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    /**
     * 회원 정보 수정 페이지로 이동
     */
    @GetMapping("/users/update")
    public String update() {
        return "users/update";
    }

    /**
     * 회원 정보 업데이트
     */
    @PutMapping("/users/update")
        public String updateUser(@Valid UserUpdateDto userUpdateDto, BindingResult bindingResult) {

        return "redirect:/user/profile";
    }

}
