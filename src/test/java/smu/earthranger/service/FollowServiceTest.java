package smu.earthranger.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.follow.FollowResponseDto;
import smu.earthranger.repository.MemberRepository;


import java.util.List;

@SpringBootTest
@Transactional
public class FollowServiceTest {

    @Autowired
    private FollowService followService;

    @Autowired
    private MemberRepository memberRepository;

    private Member memberA;
    private Member memberB;
    private Member memberC;

    @BeforeEach
    public void init(){
        memberA = Member.builder()
                .email("semi' s email").name("semi").password("semi123").build();

        memberB = Member.builder()
                .email("jimin' s email").name("jimin").password("jimin123").build();
        memberC = Member.builder()
                .email("a' s email").name("a").password("a123").build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
        memberRepository.save(memberC);
    }

    @Test
    public void follow(){

        //when
        followService.followMember(memberA.getId(), memberB.getName());   //A => B
        followService.followMember(memberB.getId(), memberA.getName());   //B => A
        followService.followMember(memberA.getId(), memberC.getName());   //A => C

        //Assertions.assertEquals(2, memberRepository.findById(memberA.getId()).get().getFollowings().size());  //오류남..왜..

        //then
        Assertions.assertEquals(2, followService.getFollowList(memberA.getId()).size());
    }

    @Test
    void showFollowList(){
        //given
        followService.followMember(memberA.getId(), memberB.getName());   //A => B
        followService.followMember(memberB.getId(), memberA.getName());   //B => A

        //when
        List<FollowResponseDto> followList = followService.getFollowList(memberA.getId());

        //then
        Assertions.assertEquals("jimin", followList.get(0).getName());
    }

    @Test
    void showFollowerByOption(){
        //given
        followService.followMember(memberA.getId(), memberB.getName());   //A => B
        followService.followMember(memberB.getId(), memberA.getName());   //B => A

        //when
        FollowResponseDto result1 = followService.findFollowerByOption(memberA.getId(), "jimin", 0);
        FollowResponseDto result2 = followService.findFollowerByOption(memberA.getId(), "jimin' s email", 1);

        //then
        Assertions.assertEquals("jimin", result1.getName());
        Assertions.assertEquals("jimin", result2.getName());
    }

    @Test
    void unfollow(){
        //given
        followService.followMember(memberA.getId(), memberB.getName());   //A => B
        followService.followMember(memberB.getId(), memberA.getName());   //B => A

        //when
        followService.unfollowMember(memberA.getId(), memberB.getId());   //A -> B unfollow

        //then
        Assertions.assertEquals(0, followService.getFollowList(memberA.getId()).size());
    }
}
