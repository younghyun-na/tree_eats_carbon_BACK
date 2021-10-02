package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.domain.carbon.Carbon;
import smu.earthranger.dto.tree.TreeMemberResponseDto;
import smu.earthranger.dto.tree.TreeResponseDto;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class TreeService {

    private final MemberService memberService;

    //본인 식물 조회
    @Transactional
    public TreeResponseDto findSelfTree(Long userId){
        Member member = memberService.findMemberById(userId);

        return TreeResponseDto.builder()
                .treeCount(member.getTreeCount())
                .treeLevel(member.getTreeLevel())
                .carbon(extractOneDayCo2(member.getCarbon())).build();
    }

    public Map<LocalDate, Double> extractOneDayCo2(List<Carbon> carbonList){

        Map<LocalDate, Double> responseMap = new HashMap<>();
        for(Carbon carbon : carbonList){
            responseMap.put(carbon.getCreatedDate(), carbon.getEmission());
        }
        return responseMap;
    }

    //이웃 식물 조회
    @Transactional
    public TreeMemberResponseDto findMemberTree(Long userId){
        Member member = memberService.findMemberById(userId);

        return TreeMemberResponseDto.builder()
                .name(member.getName())
                .treeCount(member.getTreeCount())
                .treeLevel(member.getTreeLevel())
                .followerCount(member.getFollowers().size())
                .followingCount(member.getFollowings().size()).build();
    }
}
