package smu.earthranger.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
// 기본생성자 자동 추가 but 기본생성자의 접근권한을 protected로 제한
public class User extends BaseTimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    // 기본키 생성을 데이터베이스에 위임, AUTO_INCREMENT를 이용해 기본키 생성
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 30)
    private String name;

    @Column(nullable = false)
    private String password;

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

    @Builder
    public User(String email, String name, String password){
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void update(String email, String name,String password){
        this.email=email;
        this.name = name;
        this.password = password;
    }
}
