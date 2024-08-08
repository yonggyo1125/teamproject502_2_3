package com.jmt.member.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jmt.member.constants.Authority;
import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@IdClass(AuthoritiesId.class)
@NoArgsConstructor
@AllArgsConstructor
public class Authorities {
    @Id
    @JsonIgnore
    @ManyToOne(fetch= FetchType.LAZY)
    private Member member;

    @Id
    @Column(length=20)
    @Enumerated(EnumType.STRING)
    private Authority authority;
}