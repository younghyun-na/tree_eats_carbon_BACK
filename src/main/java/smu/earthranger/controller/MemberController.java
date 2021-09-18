package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import smu.earthranger.dto.member.MemberUpdateDto;
import smu.earthranger.service.MemberService;

import javax.validation.Valid;

@Controller
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    /**
     * 회원 정보 수정 페이지로 이동
     */
    @GetMapping("/member/update")
    public String update() {
        return "member/update";
    }

    /**
     * 회원 정보 업데이트
     */
    @PutMapping("/member/update")
        public String updateUser(@Valid MemberUpdateDto memberUpdateDto, BindingResult bindingResult) {

        return "redirect:/member/profile";
    }

}
