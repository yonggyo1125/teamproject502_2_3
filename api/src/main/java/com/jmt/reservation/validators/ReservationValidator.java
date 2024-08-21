package com.jmt.reservation.validators;

import com.jmt.reservation.controllers.RequestReservation;
import com.jmt.restaurant.entities.Restaurant;
import com.jmt.restaurant.services.RestaurantInfoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@Component("reservationValidator2")
@RequiredArgsConstructor
public class ReservationValidator implements Validator {

    private final RestaurantInfoService infoService;

    @Override
    public boolean supports(Class<?> clazz) {
        return clazz.isAssignableFrom(RequestReservation.class);
    }

    @Override
    public void validate(Object target, Errors errors) {
        RequestReservation form = (RequestReservation) target;

        Long rstrId = form.getRstrId(); // 식당 등록 번호
        Restaurant restaurant = infoService.get(rstrId);

        LocalDate rDate = form.getRDate(); // 예약일
        LocalTime rTime = form.getRTime(); // 예약시간


        // 예약일 검증 S
        List<LocalDate> availableDates = restaurant.getAvailableDates();
        if (availableDates == null || availableDates.isEmpty() || !availableDates.contains(rDate)) {
            errors.rejectValue("rDate", "NotAvailable.restaurant");
        } else { // 예약 가능일인 경우 예약 시간도 검증
            Map<String, List<LocalTime>> availableTimes = restaurant.getAvailableTimes();
            int _yoil = rDate.getDayOfWeek().getValue(); // 1(월) ~ 7(일)
            boolean possible = true;
            List<LocalTime> _availableTimes = null;

            for (Map.Entry<String, List<LocalTime>> entry : availableTimes.entrySet()) {
                String yoil = entry.getKey();
                if (yoil.equals("매일")
                        || (yoil.equals("평일") && _yoil > 0 && _yoil < 6)
                        || (yoil.equals("토요일") && _yoil == 6)
                        || (yoil.equals("일요일") && _yoil == 7)
                        || (yoil.equals("주말") && (_yoil == 6 || _yoil == 7))) {
                    _availableTimes = entry.getValue();
                    break;
                }
            }

            if (_availableTimes == null || _availableTimes.stream().noneMatch(t -> t.equals(rTime))) {
                errors.rejectValue("rTime", "NotAvailable.restaurant");
            }
            // 예약일 검증 E
        }
    }
}
