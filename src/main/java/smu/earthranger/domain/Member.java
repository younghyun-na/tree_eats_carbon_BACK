package smu.earthranger.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 30, unique = true)
    private String name;

    @Column(nullable = false)
    private String password;

    private int treeLevel;
    private int followerCount;
    private int followingCount;

    @OneToMany(mappedBy = "member")
    private List<Carbon> carbon = new ArrayList<>();

    @OneToMany(mappedBy = "fromMember")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "toMember")
    private List<Follow> followings = new ArrayList<>();

    @Builder
    public Member(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void update(String email, String name,String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    //== 여기서 아예 팔로우 count 바꿀까..//
    public void updateFollowCount(int followerCount, int followingCount){
        this.followerCount = followerCount;
        this.followingCount = followingCount;
    }

      /*
         public void addFollower(User follower) {
        followers.add(follower);
        follower.following.add(this);
       }

    pub lic void addFollowing(User followed) {
        followed.addFollower(this);
    }
         */


    public void updateTree(int treeLevel){
        this.treeLevel = treeLevel;
    }

}
