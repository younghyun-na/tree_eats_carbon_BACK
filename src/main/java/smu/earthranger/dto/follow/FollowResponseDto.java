package smu.earthranger.dto.follow;

import lombok.*;
import smu.earthranger.domain.Follow;
import smu.earthranger.domain.Member;

@Getter
@NoArgsConstructor
public class FollowResponseDto {

    private String name;
    private int followerCount;
    private int followingCount;

    @Builder
    public FollowResponseDto(Follow follow){
        Member toMem = follow.getToMember();

        this.name = toMem.getName();
        this.followerCount = toMem.getFollowers().size();
        this.followingCount = toMem.getFollowings().size();
    }

}
