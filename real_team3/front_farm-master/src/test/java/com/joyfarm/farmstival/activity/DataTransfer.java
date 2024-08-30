package com.joyfarm.farmstival.activity;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.activity.entities.Activity;
import com.joyfarm.farmstival.activity.entities.ActivityTag;
import com.joyfarm.farmstival.activity.repositories.ActivityRepository;
import com.joyfarm.farmstival.activity.repositories.ActivityTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.File;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
//@ActiveProfiles("test")
public class DataTransfer {
    @Autowired
    private ObjectMapper om;

    @Autowired
    private ActivityRepository activityRepository;

    @Autowired
    private ActivityTagRepository tagRepository;

    @Test
    @DisplayName("휴양마을 데이터 DB 저장")
    void transfer1() throws Exception {
        File file = new File("D:/data/farmData.json");
        Map<String, List<Map<String, String>>> tmp = om.readValue(file, new TypeReference<>() {});
        List<Map<String, String>> records = tmp.get("records");

        /* 체험활동 처리 S */
        List<Activity> items = records.stream()
                .map(d -> Activity.builder()
                        .townName(d.get("체험마을명"))
                        .sido(d.get("시도명"))
                        .sigungu(d.get("시군구명"))
                        .division(d.get("체험프로그램구분"))
                        .activityName(d.get("체험프로그램명").trim().replace("+", ", "))
                        .facilityInfo(d.get("보유시설정보"))
                        .townArea(d.get("체험휴양마을면적"))
                        .townImage(d.get("체험휴양마을사진"))
                        .doroAddress(d.get("소재지도로명주소"))
                        .ownerName(d.get("대표자명"))
                        .ownerTel(d.get("대표전화번호"))
                        .createdDate(LocalDate.parse(d.get("지정일자")))
                        .wwwAddress(d.get("홈페이지주소"))
                        .manageAgency(d.get("관리기관명"))
                        .latitude(Double.valueOf(d.get("위도")))
                        .longitude(Double.valueOf(d.get("경도")))
                        .uploadedData(LocalDate.parse(d.get("데이터기준일자")))
                        .providerCode(d.get("제공기관코드"))
                        .providerName(d.get("제공기관명"))
                        .build()
                ).toList();

        activityRepository.saveAllAndFlush(items);
        /* 체험활동 처리 E */

        /* 태그 처리 S */
        //추가 필요하면 해당 부분에서 추가하면 됨
        for (Activity item : items) {
            String tagNames = item.getActivityName();
            String facilityInfo = item.getFacilityInfo();
            if (StringUtils.hasText(facilityInfo)) tagNames += "+" + facilityInfo;
            String division = item.getDivision();
            if (StringUtils.hasText(division)) tagNames += "+" + division;

            if (!StringUtils.hasText(tagNames)) continue;

            List<ActivityTag> tags = Arrays.stream(tagNames.split("\\+")) //메타 문자여서 \\+로 분리
                    .filter(s -> !s.isBlank())
                    .distinct() //중복 제거
                    .map(tag -> ActivityTag.builder().tag(tag).build()).toList();
            if (tags == null || tags.isEmpty()) continue;

            tagRepository.saveAllAndFlush(tags);
            item.setAcTags(tags); // 태그 저장

            activityRepository.saveAndFlush(item); // ManyToMany 매핑 처리

        }
        /* 태그 처리 E */
    }
}
