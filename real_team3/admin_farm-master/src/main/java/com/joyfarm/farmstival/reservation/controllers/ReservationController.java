package com.joyfarm.farmstival.reservation.controllers;

import com.joyfarm.farmstival.global.exceptions.ExceptionProcessor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController implements ExceptionProcessor {
    @ModelAttribute("menuCode")
    public String menuCode() {
        return "reservation";
    }
    
    @ModelAttribute("pageTitle")
    public String pageTitle() {
        return "예약관리";
    }
    
    @GetMapping
    public String list(@ModelAttribute ReservationSearch search) {
        
        return "reservation/list";
    }

    @PatchMapping
    public String change(@RequestParam("seq") List<Long> seq, @RequestParam("status") String status, Model model) {

        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq) {

        return "reservation/view";
    }
}
