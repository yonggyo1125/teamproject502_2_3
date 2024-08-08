package com.jmt.restaurant.entities;

import com.jmt.global.entities.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor @AllArgsConstructor
public class FoodMenu extends BaseEntity {
    @Id
    private Long menuId; // 메뉴 ID

    private String menuNm; // 메뉴명

    private Integer menuPrice; // 메뉴가격

    private Boolean spcltMenuYn; // 지역특산메뉴여부

    private String spcltMenuNm; // 지역특산메뉴명

    private String spcltMenuOgnUrl; // 지역특산메뉴출처 URL

    private String menuDscrn; // 메뉴설명(주재료,조리법,소스,옵션)

    private String menuCtgryLclasNm; // 메뉴카테고리대분류명

    private String menuCtgrySclasNm; // 메뉴카테고리소분류명

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="rstrId")
    private Restaurant restaurant;

    @ToString.Exclude
    @OneToMany(mappedBy = "foodMenu", fetch = FetchType.LAZY)
    private List<FoodMenuImage> images;
}
