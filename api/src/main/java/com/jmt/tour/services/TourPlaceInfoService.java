package com.jmt.tour.services;

import com.jmt.global.ListData;
import com.jmt.tour.controllers.TourPlaceSearch;
import com.jmt.tour.entities.TourPlace;
import com.jmt.tour.repositories.TourPlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TourPlaceInfoService {

    private final TourPlaceRepository repository;

    public ListData<TourPlace> getList(TourPlaceSearch search) {
        int page = Math.max(search.getPage(), 1);
        int limit = search.getLimit();
        limit = limit < 1 ? 20 : limit;

        /* 검색 처리 S */
        String sopt = search.getSopt();
        String skey = search.getSkey();

        /* 검색 처리 E */

        return null;
    }

    public TourPlace get(Long seq) {

        return null;
    }
}
