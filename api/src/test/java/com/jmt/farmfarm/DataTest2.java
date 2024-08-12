package com.jmt.farmfarm;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmt.farmfarm.entities.Festival;
import com.jmt.farmfarm.repositories.FestivalRepository;
import com.jmt.global.rests.gov.api.ApiResult2;
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

@SpringBootTest
//@ActiveProfiles("test")
public class DataTest2 {

    @Autowired
    private FestivalRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private ObjectMapper om;

    private String serviceKey = "0Sgv0AKMOayMo1mD5VMSUVb%2BUcFF8gNjLps7PmIgLMFiD8YDLAZ9NDzmF7863XkTC0DkRBCSzqM4RROiej%2BrIw%3D%3D";

    @Test
    void test1() {
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
                            .latitude(d.get("mapy") == null? null : Double.valueOf(d.get("mapy")))
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
    void test2() throws Exception {
        File file = new File("D:/data/data.txt");

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
                if (item.size() > 2) festival.setContent(item.get(2).trim());
                if (item.size() > 3) festival.setHostMain(item.get(3).trim());
                if (item.size() > 4) festival.setHostSub(item.get(4).trim());
                if (item.size() > 5) festival.setPageLink(item.get(5).trim());

                items.add(festival);
            }

            repository.saveAllAndFlush(items);
        }
    }


}
