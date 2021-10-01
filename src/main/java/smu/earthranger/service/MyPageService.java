package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.user.MyPageResponseDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.MemberRepository;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MyPageService {

    private final MemberRepository memberRepository;

    //프로필 조회
    public MyPageResponseDto getMyPage(long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        return new MyPageResponseDto(member);
    }

}
