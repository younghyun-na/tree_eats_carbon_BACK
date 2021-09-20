package smu.earthranger.domain;

import lombok.*;
import smu.earthranger.domain.carbon.Carbon;

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

    private int treeCount;

    private double totalReduction;

    @OneToMany(mappedBy = "member")
    private List<Carbon> carbon = new ArrayList<>();

    @OneToMany(mappedBy = "fromMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followings = new ArrayList<>();

    @OneToMany(mappedBy = "toMember", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Follow> followers = new ArrayList<>();

    //==업데이트 메서드==//

    public void update(String email, String name,String password) {
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void updateTree(int treeLevel){
        this.treeLevel = treeLevel;
    }


    public void updateTreeCnt(int treeLevel){
        this.treeLevel = treeLevel;
        this.treeCount += 1;
    }

    public void updateTotal(double reduction){
        this.totalReduction += reduction;
    }

    @Builder
    private Member(String email, String name, String password, double totalReduction) {
        this.email = email;
        this.name = name;
        this.password = password;
        this.totalReduction = totalReduction;
    }
}
