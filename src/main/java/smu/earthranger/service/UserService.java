package smu.earthranger.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import smu.earthranger.domain.User;
import smu.earthranger.dto.UserSignupDto;
import smu.earthranger.dto.UserUpdateDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.UserRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserService {
    @Autowired
    private UserRepository userRepository;
    private FollowRepository followRepository;

    /**
     * 회원가입
     */
    @Transactional
    public User save(UserSignupDto userSignupDto) {

        // 중복 회원 검증
        if(userRepository.findUserByEmail(userSignupDto.getEmail()) != null)
            throw new IllegalStateException("이미 존재하는 회원입니다.");

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return userRepository.save(User.builder()
                .email(userSignupDto.getEmail())
                .password(encoder.encode(userSignupDto.getPassword()))
                .name(userSignupDto.getName())
                .build());
    }


    /**
     * 회원 정보 수정
     */
}