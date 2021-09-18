package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Follow;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.follow.FollowRequestDto;
import smu.earthranger.dto.follow.FollowResponseDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;

    /**
     * 이웃 추가
     */
    @Transactional
    public void followMember(Long fromId, String toName){
        Member fromMember = memberRepository.findById(fromId).orElseThrow(() ->
                new IllegalStateException("Non exist member."));

        Member toMember = memberRepository.findUserByName(toName).orElseThrow(() ->
                new IllegalStateException("Non exist member."));


        if(fromMember.equals(toMember)){
            throw new IllegalStateException("Follow rejected.");
        }

        boolean isFollow = followRepository.findByFromMember_Id(fromId)
                .stream()
                .filter(s -> s.getToMember().getName().equals(toName))
                .findFirst().isEmpty();

        if(!isFollow) throw new IllegalStateException("Already Followed.");

        //follow 객체 생성 후 DB에 저장
        followRepository.save(FollowRequestDto.builder()
                .fromMember(fromMember)
                .toMember(toMember)
                .build()
                .toEntity());

        //follower, following count 업데이트하기
        int followingCount = followRepository.findByFromMember_Id(fromId).size();
        int followerCount = followRepository.findByToMember_Id(toMember.getId()).size();

        fromMember.updateFollowCount(followingCount, followerCount);
    }

    /**
     * 이웃 삭제
     */
    @Transactional
    public void unfollowMember(Long fromId, Long toId){
        Member fromMem = memberRepository.findById(fromId).orElseThrow(() ->
                new IllegalStateException("Non exist member."));

        Member toMem = memberRepository.findById(toId).orElseThrow(() ->
                new IllegalStateException("Non exist member."));

        followRepository.deleteByFromMemberAndToMember(fromMem, toMem);

    }

    /**
     * 이웃 검색_이름 :0, 이메일 :1 : Follow 대상
     */
    public FollowResponseDto findFollowerByOption(Long id, String optionValue, int option){

        FollowResponseDto followDto = null;

        if(option == 0){
            Follow follow  = followRepository.findByFromMember_IdAndToMember_Name(id, optionValue).orElseThrow(() ->
                    new IllegalStateException("Non exist Member."));
            followDto = getFollowResponseDto(follow);

        }else if(option == 1){
            Follow follow = followRepository.findByFromMember_IdAndToMember_Email(id, optionValue).orElseThrow(() ->
                    new IllegalStateException("Non exist Member"));
            followDto = getFollowResponseDto(follow);
        }
        return followDto;
    }

    /**
     * 팔로잉(이웃) 전체 조회
     */

    public List<FollowResponseDto> getFollowList(Long fromId){

        List<Follow> followList = followRepository.findByFromMember_Id(fromId);
        if(followList.isEmpty()){
            throw new IllegalStateException("Follow list does not exist.");
        }
        
        List<FollowResponseDto> followResponseList = new ArrayList<>();

        for(Follow follow : followList){
            FollowResponseDto dto = getFollowResponseDto(follow);
            followResponseList.add(dto);
        }
        return followResponseList;
    }

    private FollowResponseDto getFollowResponseDto(Follow follow){
        Member member = follow.getToMember();

        return FollowResponseDto.builder()
                .name(member.getName())
                .followingCount(member.getFollowingCount())
                .followerCount(member.getFollowerCount())
                .build();
    }

}
