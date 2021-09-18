package smu.earthranger.dto.follow;

import lombok.*;
import smu.earthranger.domain.Follow;

@Getter
@NoArgsConstructor
public class FollowResponseDto {

    private String name;
    private int followerCount;
    private int followingCount;

    @Builder
    public FollowResponseDto(String name, int followerCount, int followingCount){
        this.name = name;
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

}
