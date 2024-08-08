package com.jmt.farmfarm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.farmfarm.entities.TourPlace;
import com.jmt.farmfarm.repositories.TourPlaceRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.util.List;
import java.util.Map;

@SpringBootTest
@ActiveProfiles("test")
public class DataTest {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourPlaceRepository repository;

    @Test
    void test1() throws Exception {
        File file = new File("D:/data/data1.json");
        List<Map<String, String>> tmp = om.readValue(file, new TypeReference<>() {});

        List<TourPlace> items = tmp.stream()
                .map(d -> TourPlace.builder()
                        .title(d.get("여행지명"))
                        .latitude(Double.valueOf(d.get("위도")))
                        .longitude(Double.valueOf(d.get("경도")))
                        .tel(d.get("연락처"))
                        .address(d.get("주소"))
                        .description(d.get("설명"))
                        .photoUrl(d.get("사진파일"))
                        .tourDays(Integer.valueOf(d.get("여행일")))
                        .build()).toList();

        repository.saveAllAndFlush(items);
    }

    @Test
    void test2() throws Exception {
        File file = new File("D:/data/data2.json");
        List<Map<String, String>> tmp = om.readValue(file, new TypeReference<>() {});

        List<TourPlace> items = tmp.stream()
                .map(d -> TourPlace.builder()
                        .title(d.get("주제"))
                        .sido(d.get("시도"))
                        .sigungu(d.get("시군구"))
                        .address(d.get("시도") + " " + d.get("시군구"))
                        .description(d.get("요약"))
                        .period(d.get("월"))
                        .photoUrl(d.get("사진파일"))
                        .course(d.get("코스정보")).build()
                ).toList();

        repository.saveAllAndFlush(items);

    }
}
