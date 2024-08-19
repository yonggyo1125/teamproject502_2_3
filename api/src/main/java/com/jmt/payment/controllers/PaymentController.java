package com.jmt.payment.controllers;

import com.jmt.global.Utils;
import com.jmt.payment.services.PaymentProcessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentProcessService processService;
    //private final OrderPayService orderPayService;
    private final Utils utils;

    @PostMapping("/process")
    public String process(PayAuthResult result) {
        /*
        PayConfirmResult confirmResult = processService.process(result);
        if (confirmResult == null) { // 결제 실패시에는 주문 실패 페이지로 이동
            return "redirect:" + utils.redirectUrl("/order/fail");
        }

        OrderInfo orderInfo = orderPayService.update(confirmResult);

        // 주문 성공시에는 주문 상세 페이지로 이동
        return "redirect:" + utils.redirectUrl("/order/detail/" + orderInfo.getOrderNo());

         */
        return null;
    }
    /*
    @RequestMapping("/close")
    public String close(Model model) {

        String script = "const modalBg = document.querySelector('.inipay_modal-backdrop');" +
        "const modal = document.getElementById('inicisModalDiv');" +
        "if (modalBg) modalBg.parentNode.removeChild(modalBg);" +
        "if (modal) modal.parentNode.removeChild(modal);";

        model.addAttribute("script", script);

        return "common/_execute_script";
    }
     */
}
