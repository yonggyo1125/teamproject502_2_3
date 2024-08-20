package com.jmt.activity.constants;

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

    public static List<String[]> getList() {
        return List.of(
          new String[] {APPLY.name(), APPLY.title},
          new String[] {CANCEL.name(), CANCEL.title}
        );
    }
}
