package smu.earthranger.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import smu.earthranger.dto.user.MyPageResponseDto;
import smu.earthranger.jwt.SecurityUtil;
import smu.earthranger.service.MyPageService;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("/mypage")
public class MyPageController {

    private final MyPageService myPageService;

    @GetMapping
    public ResponseEntity<?> getMyPage() {
        Optional<Long> userId = SecurityUtil.getCurrentUserId();
        MyPageResponseDto myPage = myPageService.getMyPage(userId.get());
        return ResponseEntity.ok(myPage);
    }
}
