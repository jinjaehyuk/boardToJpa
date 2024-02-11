package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity //Database Table과 매핑하는 객체.
//@Table(name="user3") //Database Table 이름이 user3와 User라는 객체가 매핑.
@NoArgsConstructor // 기본 생성자가 필요
@Setter
@Getter
//@ToString // 엔티티 관계를 표현할때에는 되도록 사용X
public class Role {

    @Id //이 필드가 Table의 PK
    @Column(name="role_id") //
    @GeneratedValue(strategy = GenerationType.IDENTITY) // userId는 자동으로 생성되도록 함. 1,2,3,4... autoincrement
    private Integer roleID;

    @Column(length = 20)
    private String name;

    @Override
    public String toString() {
        return "Role{" +
                "roleID=" + roleID +
                ", name='" + name + '\'' +
                '}';
    }
}
