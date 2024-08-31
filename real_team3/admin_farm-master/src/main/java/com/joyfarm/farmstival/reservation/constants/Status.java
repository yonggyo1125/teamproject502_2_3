package com.joyfarm.farmstival.reservation.constants;

import java.util.List;

public enum Status {
    APPLY("예약"),
    CANCEL("취소");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    //관리자가 사용할 상태 목록 반환
    public static List<String[]> getList() {
        return List.of(
                new String[]{APPLY.name(), APPLY.title},
                new String[]{CANCEL.name(), CANCEL.title}
        ); //관리자에서 사용할 예정
    }
}
