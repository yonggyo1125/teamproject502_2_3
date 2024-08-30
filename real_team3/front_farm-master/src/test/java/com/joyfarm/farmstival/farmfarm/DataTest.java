package com.joyfarm.farmstival.farmfarm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.tour.entities.QTourPlace;
import com.joyfarm.farmstival.tour.entities.TourPlace;
import com.joyfarm.farmstival.tour.entities.TourPlaceTag;
import com.joyfarm.farmstival.tour.repositories.TourPlaceRepository;
import com.joyfarm.farmstival.tour.repositories.TourPlaceTagRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StringUtils;

import java.io.File;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@SpringBootTest
//@ActiveProfiles("test")
public class DataTest {

    @Autowired
    private ObjectMapper om;

    @Autowired
    private TourPlaceRepository repository;

    @Autowired
    private TourPlaceTagRepository tagRepository;

    @Test
    @DisplayName("여행지 세부 정보 조회")
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
    @DisplayName("여행지 정보 조회")
    void test2() throws Exception {
        File file = new File("D:/data/data2.json");
        List<Map<String, String>> tmp = om.readValue(file, new TypeReference<>() {});

        tmp.forEach(d -> {
            String tagsTmp = d.get("태그");
            List<TourPlaceTag> tags = null;
            if (StringUtils.hasText(tagsTmp)) {
                tags = Arrays.stream(tagsTmp.split(","))
                        .map(s -> TourPlaceTag.builder().tag(s).build())
                        .toList();
                tagRepository.saveAllAndFlush(tags);
            }

            TourPlace item = TourPlace.builder()
                    .tags(tags)
                    .title(d.get("주제"))
                    .sido(d.get("시도"))
                    .sigungu(d.get("시군구"))
                    .address(d.get("시도") + " " + d.get("시군구"))
                    .description(d.get("요약"))
                    .period(d.get("월"))
                    .photoUrl(d.get("사진파일"))
                    .course(d.get("코스정보")).build();
            repository.saveAndFlush(item);
        });
    }

    @Test
    void test3() {
        QTourPlace tourPlace = QTourPlace.tourPlace;
        List<TourPlace> items = repository.findAll();
        for (TourPlace item : items) {
            String address = item.getAddress();
            String sido = address.substring(0, 2);

            String _sido = null;
            if (sido.equals("충북")) {
                _sido = "충청북도";
            } else if (sido.equals("충남")) {
                _sido = "충청남도";
            } else if (sido.equals("전북")) {
                _sido = "전라북도";
            } else if (sido.equals("전남")) {
                _sido = "전라남도";
            } else if (sido.equals("경북")) {
                _sido = "경상북도";
            } else if (sido.equals("경남")) {
                _sido = "경상남도";
            }

            if (_sido != null) {
                item.setSido(_sido);
                continue;
            }

            List<TourPlace> items2 = (List<TourPlace>)repository.findAll(tourPlace.sido.contains(sido));
            if (items2 == null || items2.isEmpty()) continue;

            TourPlace item2 = items2.get(0);

            if (item2 != null) {
                _sido = item2.getSido();

                item.setSido(_sido);
            }
        }

        repository.saveAllAndFlush(items);

        for (TourPlace item : items) {
            if (StringUtils.hasText(item.getSigungu())) continue;

            String address = item.getAddress();

            String sigungu = address.indexOf("경기도") > -1 ? address.substring(3, 6) : address.substring(2, 5);

            item.setSigungu(sigungu);
        }

        repository.saveAllAndFlush(items);
    }
}