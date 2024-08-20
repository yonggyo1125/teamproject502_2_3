package com.jmt.activity.constants;

import java.util.List;

public enum Status {
    APPLY("예약접수"),
    CONFIRM("예약확정"),
    CANCEL("예약취소");

    private final String title;

    Status(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public static List<String[]> getList() {
        return List.of(
          new String[] {APPLY.name(), APPLY.title},
          new String[] {CONFIRM.name(), CONFIRM.title},
          new String[] {CANCEL.name(), CANCEL.title}
        );
    }
}
