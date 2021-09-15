package smu.earthranger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.user.UserSignupDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.MemberRepository;

@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private MemberRepository userRepository;
    private FollowRepository followRepository;

    /**
     * 회원가입
     */
    @Transactional
    public Member save(UserSignupDto userSignupDto) {

        // 중복 회원 검증
        if(userRepository.findUserByEmail(userSignupDto.getEmail()) != null)
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(Member.builder()
                .email(userSignupDto.getEmail())
                .password(encoder.encode(userSignupDto.getPassword()))
                .name(userSignupDto.getName())
                .build());
    }


    /**
     * 회원 정보 수정
     */
}