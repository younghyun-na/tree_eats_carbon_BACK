package smu.earthranger.repository;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import smu.earthranger.domain.Member;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
@ActiveProfiles("test")
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTest {

    @Autowired
    private MemberRepository userRepository;

    private Member user;

    @BeforeEach
    public void setUp() {
        user = Member.builder().email("test@test").name("test").password("abc").build();
    }

    @AfterEach
    public void tearDown() {
        userRepository.deleteAll();
    }

    @Test
    public void findUserByEmail_성공() {
        //given
        userRepository.save(user);

        //when
        Member result = userRepository.findUserByEmail("test@test");

        //then
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
    }
}

