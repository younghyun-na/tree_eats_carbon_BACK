package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.follow.FollowResponseDto;
import smu.earthranger.dto.user.MemberRankingResponseDto;
import smu.earthranger.repository.FollowRepository;
import smu.earthranger.repository.MemberRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RankService {

    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public List<MemberRankingResponseDto> getByPermission(Long memberId,String user, Pageable pageable){
        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        List<MemberRankingResponseDto> list = notFoundMembers();

        if(user.equals("all")) {
            return getAllMember(pageable);
        }
        else if(user.equals("follow")){
            return getFollowMember(pageable,member);
        }

        return list;

    }

    private List<MemberRankingResponseDto> notFoundMembers() {
        List<MemberRankingResponseDto> list = new ArrayList<>();
        MemberRankingResponseDto dto = null;
        list.add(dto);
        return list;
    }

    private List<MemberRankingResponseDto> getAllMember(Pageable pageable) {
        Page<Member> sorted = memberRepository.findAll(pageable);

        return sorted.stream()
                .map(MemberRankingResponseDto::new)
                .collect(Collectors.toList());
    }

    private List<MemberRankingResponseDto> getFollowMember(Pageable pageable, Member member) {
        Page<Member> sorted = followRepository.findFollowByFromMember(member);

        return sorted.stream()
                .map(MemberRankingResponseDto::new)
                .collect(Collectors.toList());
    }
}
