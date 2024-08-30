package com.joyfarm.farmstival.member.entities;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joyfarm.farmstival.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Builder
@Entity
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Authorities> authorities;
}
