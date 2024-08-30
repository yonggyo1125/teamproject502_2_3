package com.joyfarm.farmstival.activity.controllers;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestReservation { //커맨드 객체
    
    @NotNull
    private Long activitySeq; //액티비티 등록 번호
    
    @NotBlank
    private String name; //예약자명

    @NotBlank
    private String email; //예약자 이메일

    @NotBlank
    private String mobile; //예약자 전화번혼

    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate rDate; //예약일
    
    private String ampm; //오전, 오후

    @Min(1)
    private int persons; //예약 인원수, 최소 1명 이상
}
