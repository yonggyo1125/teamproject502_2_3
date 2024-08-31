package com.joyfarm.farmstival.reservation.controllers;

import com.joyfarm.farmstival.global.ListData;
import com.joyfarm.farmstival.global.exceptions.ExceptionProcessor;
import com.joyfarm.farmstival.menus.Menu;
import com.joyfarm.farmstival.menus.MenuDetail;
import com.joyfarm.farmstival.reservation.entities.Reservation;
import com.joyfarm.farmstival.reservation.services.ReservationInfoService;
import com.joyfarm.farmstival.reservation.services.ReservationStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController implements ExceptionProcessor {

    private final ReservationInfoService infoService;
    private final ReservationStatusService statusService;

    @ModelAttribute("menuCode")
    public String menuCode() {
        return "reservation";
    }

    @ModelAttribute("subMenus")
    public List<MenuDetail> subMenus() {
        return Menu.getMenus(menuCode());
    }

    @ModelAttribute("pageTitle")
    public String pageTitle() {
        return "예약관리";
    }
    
    @GetMapping
    public String list(@ModelAttribute ReservationSearch search, Model model) {
        ListData<Reservation> data = infoService.getList(search);

        model.addAttribute("items", data.getItems());
        model.addAttribute("pagination", data.getPagination());

        return "reservation/list";
    }

    @PatchMapping
    public String change(RequestReservationChange form, Model model) {

        statusService.change(form.getSeq(), form.getStatus());

        model.addAttribute("script", "parent.location.reload()");
        return "common/_execute_script";
    }

    @GetMapping("/view/{seq}")
    public String view(@PathVariable("seq") Long seq) {

        return "reservation/view";
    }
}
