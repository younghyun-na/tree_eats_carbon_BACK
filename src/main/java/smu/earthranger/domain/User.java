package smu.earthranger.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue
    @Column(name = "user_id")
    private Long id;

    private String password;
    private String user_name;
    private String email;

    private Integer rank;
    private Integer tree_level;
    private Integer follower_count;
    private Integer following_count;

    @OneToMany(mappedBy = "user")
    private List<Carbon> carbon = new ArrayList<>();

    @OneToMany(mappedBy = "from_user")
    private List<Follow> followers = new ArrayList<>();

    @OneToMany(mappedBy = "to_user")
    private List<Follow> followings = new ArrayList<>();
}
