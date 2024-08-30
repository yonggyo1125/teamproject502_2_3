package com.joyfarm.farmstival.activity.controllers;

import com.joyfarm.farmstival.activity.entities.Reservation;
import com.joyfarm.farmstival.activity.services.ReservationCancelService;
import com.joyfarm.farmstival.activity.services.ReservationInfoService;
import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.rests.JSONData;
import com.joyfarm.farmstival.member.MemberUtil;
import com.joyfarm.farmstival.member.entities.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/myreservation")
@RequiredArgsConstructor
public class ReservationController {
    //예약 관리

    private final ReservationInfoService infoService;
    private final ReservationCancelService cancelService;
    private final MemberUtil memberUtil;

    @GetMapping("/list") //예약은 회원가입한 멤버만 예약 조회 가능 -> 회원이 아니면 404
    public JSONData list(ReservationSearch search) {
        Member member = memberUtil.getMember();
        search.setMemberSeqs(List.of(member.getSeq()));

        ListData<Reservation> data = infoService.getList(search);

        return new JSONData(data);
    }

    //예약 정보 상세 조회
    @GetMapping("/info/{seq}")
    public JSONData info(@PathVariable("seq") Long seq) {
        Reservation reservation = infoService.get(seq, true);

        return new JSONData(reservation);
    }

    //예약 취소
    @PostMapping("/cancel/{seq}")
    public JSONData cancel(@PathVariable("seq") Long seq) {
        Reservation item = cancelService.cancel(seq);

        return new JSONData(item);
    }



    //관리자가 조회할 때
    @GetMapping("/admin/list")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public JSONData adminlist(@ModelAttribute ReservationSearch search) {

        ListData<Reservation> data = infoService.getList(search);

        return new JSONData(data);
    }
    
    @GetMapping("/admin/info/{seq}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public JSONData adminInfo(@PathVariable("seq") Long seq) {
        Reservation reservation = infoService.get(seq);

        return new JSONData(reservation);
    }
}
