package com.joyfarm.farmstival.farmfarm;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.joyfarm.farmstival.farmfarm.entities.Festival;
import com.joyfarm.farmstival.farmfarm.repositories.FestivalRepository;
import com.joyfarm.farmstival.global.rests.gov.api.ApiResult2;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@SpringBootTest
//@ActiveProfiles("test")
public class DataTest2 {

    @Autowired
    private FestivalRepository repository;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private RestTemplate restTemplate;

    private String serviceKey = "0Sgv0AKMOayMo1mD5VMSUVb%2BUcFF8gNjLps7PmIgLMFiD8YDLAZ9NDzmF7863XkTC0DkRBCSzqM4RROiej%2BrIw%3D%3D";

    @Test
    @DisplayName("Festvial-축제 정보 데이터(이미지 있는 데이터)")
    void test1(){
        String url = String.format("https://apis.data.go.kr/B551011/KorService1/searchFestival1?MobileOS=AND&MobileApp=test&_type=json&eventStartDate=20240101&serviceKey=%s&pageNo=%d&numOfRows=1000", serviceKey, 1);

        ResponseEntity<ApiResult2> response = restTemplate.getForEntity(URI.create(url), ApiResult2.class);
        List<Map<String, String>> tmp = response.getBody().getResponse().getBody().getItems().getItem();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        List<Festival> items = tmp.stream()
                .map(d -> Festival.builder()
                        .seq(Long.valueOf(d.get("contentid")))
                        .cat1(d.get("cat1"))
                        .cat2(d.get("cat2"))
                        .cat3(d.get("cat3"))
                        .title(d.get("title"))
                        .latitude(d.get("mapy") == null ? null : Double.valueOf(d.get("mapy")))
                        .longitude(d.get("mapx") == null ? null : Double.valueOf(d.get("mapx")))
                        .tel(d.get("tel"))
                        .address(d.get("addr1") + " " + d.get("addr2"))
                        .photoUrl1(d.get("firstimage"))
                        .photoUrl2(d.get("firstimage2"))
                        .startDate(d.get("eventstartdate") == null ? null : LocalDate.parse(d.get("eventstartdate"), formatter))
                        .endDate(d.get("eventenddate") == null ? null : LocalDate.parse(d.get("eventenddate"), formatter)).build()
                ).toList();

        repository.saveAllAndFlush(items);
    }

    @Test
    @DisplayName("Festvial-축제 상세 정보 데이터 추가")
    void test2() throws Exception {
        File file = new File("D:/data/fest2.txt");

        try (FileInputStream fis = new FileInputStream(file);
             InputStreamReader isr = new InputStreamReader(fis, StandardCharsets.UTF_8);
             BufferedReader br = new BufferedReader(isr)) {
            String line = null;
            List<Festival> items = new ArrayList<>();
            while ((line = br.readLine()) != null) {
                List<String> item = new ArrayList<>(Arrays.stream(line.split("__")).toList());

                Long seq = Long.valueOf(item.get(0).trim());
                Festival festival = repository.findById(seq).orElse(null);
                if (festival == null) continue;

                if (item.size() > 1) festival.setLocation(item.get(1).trim());
                if (item.size() > 2) {
                    String content = item.get(2).trim().replace("+", ", ");
                    festival.setContent(content);
                }
                if (item.size() > 3) festival.setHostMain(item.get(3).trim());
                if (item.size() > 4) festival.setHostSub(item.get(4).trim());
                if (item.size() > 5) festival.setPageLink(item.get(5).trim());

                items.add(festival);
            }

            repository.saveAllAndFlush(items);
        }
    }

    // 작업 조회용 (건드리지 마세요!)
    @Test
    void test3() throws Exception {
        File file = new File("D:/data/fest1.json");

        Map<String, List<Map<String, String>>> data = om.readValue(file, new TypeReference<>(){});
        List<Map<String, String>> records = data.get("records");

        //System.out.println(records);
        List<Festival> items = records.stream()
                .map(r -> Festival.builder()
                        .title(r.get("축제명"))
                        .location("__" + r.get("개최장소") + "__")
                        .content(r.get("축제내용") + "__")
                        .hostMain(r.get("주최기관명") + "__")
                        .hostSub(r.get("주관기관명") + "__")
                        .pageLink(r.get("홈페이지주소"))
                        .build()
                ).collect(Collectors.toList());

        items.forEach(System.out::println);

        //Map<String, Object> m1 = om.readValue(file, new TypeReference<>(){});
        //List<Map<String, String>> records = (List<Map<String, String>>) m1.get("records");
        /*
        List<Festival> items = records.stream()
                .map(r -> Festival.builder()
                        .title(r.get("축제명"))
                        .latitude(parseDouble(r.get("위도")))
                        .longitude(parseDouble(r.get("경도")))
                        .tel(r.get("전화번호"))
                        .address(r.get("소재지도로명주소"))
                        .location(r.get("개최장소"))
                        .content(r.get("축제내용"))
                        .hostMain(r.get("주최기관명"))
                        .hostSub(r.get("주관기관명"))
                        .startDate(LocalDate.parse(r.get("축제시작일자")))
                        .endDate(LocalDate.parse(r.get("축제종료일자")))
                        .pageLink(r.get("홈페이지주소"))
                        .build()
                ).collect(Collectors.toList());

        items.forEach(System.out::println);
    }

    private Double parseDouble(String value) {
        if (value == null || value.trim().isEmpty()) {
            return null; // 또는 기본값, 예: 0.0
        }
        return Double.valueOf(value);
        */
    }


}