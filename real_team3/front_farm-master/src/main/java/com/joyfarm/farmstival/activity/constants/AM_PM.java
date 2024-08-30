package com.joyfarm.farmstival.activity.constants;

public enum AM_PM {
    AM("오전"),
    PM("오후");

    private final String title;

    AM_PM(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}
