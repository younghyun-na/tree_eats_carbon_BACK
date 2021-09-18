package smu.earthranger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.member.MemberSignupDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private FollowRepository followRepository;

    public MemberService(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    /**
     * 회원가입
     */
    @Transactional
    public Member save(MemberSignupDto memberSignupDto) {

        // 중복 회원 검증
        if(memberRepository.findUserByEmail(memberSignupDto.getEmail()) != null)
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return memberRepository.save(Member.builder()
                .email(memberSignupDto.getEmail())
                .password(encoder.encode(memberSignupDto.getPassword()))
                .name(memberSignupDto.getName())
                .build());
    }


    /**
     * 회원 정보 수정
     */
}