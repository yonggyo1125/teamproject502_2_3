package com.jmt.activity.controllers;

import com.jmt.activity.entities.Activity;
import com.jmt.activity.services.ActivityInfoService;
import com.jmt.global.ListData;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/activity")
@RequiredArgsConstructor
public class ActivityController {
    private final ActivityInfoService infoService;

    @GetMapping
    public JSONData getList(@ModelAttribute ActivitySearch search) {
        ListData<Activity> data = infoService.getList(search);

        return new JSONData(data);
    }

    @GetMapping("/info/{seq}")
    public JSONData get(Long seq) {
        Activity item = infoService.get(seq);

        return new JSONData(item);
    }
}
