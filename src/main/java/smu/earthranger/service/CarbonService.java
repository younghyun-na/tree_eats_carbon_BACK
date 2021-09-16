package smu.earthranger.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import smu.earthranger.domain.Carbon;
import smu.earthranger.domain.Info;
import smu.earthranger.domain.Member;
import smu.earthranger.dto.carbon.CarbonRequestDto;
import smu.earthranger.dto.carbon.CarbonRequestInfoDto;
import smu.earthranger.repository.CarbonRepository;
import smu.earthranger.repository.InfoRepository;
import smu.earthranger.repository.MemberRepository;


import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CarbonService {

    private final CarbonRepository carbonRepository;
    private final MemberRepository memberRepository;
    private final InfoRepository infoRepository;
    private final double carCo2 = 145;
    private final double subwayCo2 = 60;
    private final double busCo2 =58;


    //탄소 발자국 계산 -> 일정 수준 넘어서면 레벨업
    @Transactional
    public Carbon saveCarbon(Long memberId,CarbonRequestDto dto){

        double distance = dto.getDistance();
        int transport = dto.getTransport();

        double result = calculateCarbon(distance, transport);
        double reduction = distance * carCo2; //자동차를 탔다면 방출할 이산화탄소 - 실제 이산화 탄소

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        Carbon carbon = dto.toEntity(member, result, reduction);
        Carbon savedCarbon = carbonRepository.save(carbon);

        //저장 후 유저.누적 카본 가져옴 -> level up

        return savedCarbon;
    }


    public CarbonRequestInfoDto showInfo(long memberId){
        long count = infoRepository.count();//DB에 저장된 정보 수

        //랜덤 정보
        Long randomId = Long.valueOf((long) Math.random() * count);
        double treeCnt = 0;
        Info info = infoRepository.findById(randomId)
                .orElseThrow(IllegalAccessError::new);

        Member member = memberRepository.findById(memberId)
                .orElseThrow(()-> new IllegalStateException("존재하지 않는 회원"));

        //member의 방출량
        treeCnt = getTreeCnt(memberId);

        CarbonRequestInfoDto dto = CarbonRequestInfoDto.builder()
                .info(info.getContent())
                .tree(treeCnt)
                .build();

        return dto;
    }

    private double getTreeCnt(long memberId) {
        double treeCnt;
        LocalDateTime now = LocalDateTime.now();
        Optional<Carbon> findCarbon = carbonRepository.findByMemberIdAndCreatedDate(memberId, now);
        double emissionTone = gToT(findCarbon.get().getEmission());
        treeCnt = emissionTone * 7.16;
        return treeCnt;
    }

    private double calculateCarbon(double distance, int transport) {
        double result = 0.0;
        //도보 or 자전거 -> 0
        if(transport == 0){
            result = 0;
        }
        //지하철
        else if(transport == 1) {
            result = distance * subwayCo2;
        }
        //버스
        else if(transport == 2){
            result = distance * busCo2;
        }
        return result;
    }

    private void levelUp(double reduction, Member member){
        double lv1 = 10000;
        double lv2 = 30000;
        double lv3 = 50000;
        double lv4 = 70000;
        double lv5 = 90000;
        if (reduction >= lv5)
            member.updateTree(1);
        else if (reduction >= lv4)
            member.updateTree(5);
        else if (reduction >= lv3)
            member.updateTree(4);
        else if (reduction >= lv2)
            member.updateTree(3);
        else if (reduction >= lv1)
            member.updateTree(2);
    }

    private double gToT(double gram){
        return gram * 0.000001;
    }


}
