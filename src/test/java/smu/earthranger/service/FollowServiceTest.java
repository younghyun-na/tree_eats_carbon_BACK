package smu.earthranger.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.follow.FollowResponseDto;
import smu.earthranger.repository.FollowRepository;
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

    @BeforeEach
    public void init(){
        memberA = Member.builder()
                .email("semi' s email").name("semi").password("semi123").build();

        memberB = Member.builder()
                .email("jimin' s email").name("jimin").password("jimin123").build();

        memberRepository.save(memberA);
        memberRepository.save(memberB);
    }

    @Test
    public void follow(){

        //when
        followService.followMember(memberA.getId(), memberB.getName());   //A => B
        followService.followMember(memberB.getId(), memberA.getName());

        //then
        Assertions.assertEquals(1, memberRepository.findById(memberA.getId()).get().getFollowingCount());
        Assertions.assertEquals(1, memberRepository.findById(memberA.getId()).get().getFollowerCount());
        Assertions.assertEquals(1, memberRepository.findById(memberB.getId()).get().getFollowingCount());
        Assertions.assertEquals(1, memberRepository.findById(memberB.getId()).get().getFollowerCount());

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

    }

    @Test
    void unfollow(){
        //given
        followService.followMember(memberA.getId(), memberB.getName());   //A => B
        followService.followMember(memberB.getId(), memberA.getName());

        //when
        followService.unfollowMember(memberA.getId(), memberB.getId());  //A -> B unfollow

        //then
        //Assertions.assertEquals(0, memberRepository.findById(memberA.getId()).get().getFollowingCount());

    }




}
