package smu.earthranger.dto.carbon;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class CarbonRequestInfoDto {

    private double tree;
    private String info;


    @Builder
    public CarbonRequestInfoDto(double tree, String info) {
        this.tree = tree;
        this.info = info;
    }
}
