package smu.earthranger.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String userName;
    private String email;

    private Integer tree_level;
    private Integer follower_count;
    private Integer following_count;

    @OneToMany(mappedBy = "user")
    private List<Carbon> carbon = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Tree> tree = new ArrayList<>();

    @OneToMany(mappedBy = "from_user_id")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "to_user_id")
    private List<Follow> followings = new ArrayList<>();








}
