package com.joyfarm.farmstival.member.entities;

import com.joyfarm.farmstival.file.entities.FileInfo;
import com.joyfarm.farmstival.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long seq;

    @Column(length=45, nullable=false)
    private String gid;

    @Column(length=65, unique = true, nullable = false)
    private String email;

    @Column(length=65, nullable = false)
    private String password;

    @Column(length=40, nullable = false)
    private String userName;

    @Column(length=15, nullable = false)
    private String mobile;

    @ToString.Exclude
    @OneToMany(mappedBy = "member")
    private List<Authorities> authorities;

    @Transient
    private FileInfo profileImage; //프로필이미지
}
