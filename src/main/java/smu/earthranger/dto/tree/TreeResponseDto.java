package smu.earthranger.dto.tree;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import smu.earthranger.domain.Member;
import smu.earthranger.domain.carbon.Carbon;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor
public class TreeResponseDto {
    private int treeLevel;
    private int treeCount;
    private Map<LocalDate, Double> carbonMap = new HashMap<>();   //날짜 : 이산화탄소 배출 양

    @Builder
    public TreeResponseDto(int treeLevel, int treeCount, Map<LocalDate, Double> carbon){
        this.treeLevel = treeLevel;
        this.treeCount = treeCount;
        this.carbonMap = carbon;
    }

}

