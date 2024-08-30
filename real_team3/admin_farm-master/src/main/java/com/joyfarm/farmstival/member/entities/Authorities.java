package com.joyfarm.farmstival.member.entities;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.joyfarm.farmstival.member.constants.Authority;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Builder
@IdClass(AuthoritiesId.class)
@NoArgsConstructor @AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
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
