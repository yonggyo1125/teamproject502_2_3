package com.jmt.activity.controllers;

import com.jmt.activity.entities.Activity;
import com.jmt.activity.services.ActivityInfoService;
import com.jmt.global.ListData;
import com.jmt.global.rests.JSONData;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

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
    public JSONData get(@PathVariable("seq") Long seq) {
        Activity item = infoService.get(seq);

        return new JSONData(item);
    }
}
