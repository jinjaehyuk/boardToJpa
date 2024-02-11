package com.example.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity //Database Table과 매핑하는 객체.
//@Table(name="user3") //Database Table 이름이 user3와 User라는 객체가 매핑.
@NoArgsConstructor // 기본 생성자가 필요
@Setter
@Getter
public class User {

    @Id //이 필드가 Table의 PK
    @Column(name="user_id") //
    @GeneratedValue(strategy = GenerationType.IDENTITY) // userId는 자동으로 생성되도록 함. 1,2,3,4... autoincrement
    private Integer userId;

    @Column(length = 255)
    private String email;

    @Column(length = 50)
    private String name;

    @Column(length = 500)
    private String password;

    @CreationTimestamp //현재시간이 저장될 때 자동으로 생성.
    private LocalDateTime regdate;

    @ManyToMany
    @JoinTable(name = "user_role",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    Set<Role> roles = new HashSet<>();


    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", name='" + name + '\'' +
                ", password='" + password + '\'' +
                ", regdate=" + regdate +
                '}';
    }
}
//User ---> Role
