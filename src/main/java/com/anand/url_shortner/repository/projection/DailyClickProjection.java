package com.anand.url_shortner.repository.projection;

import java.time.LocalDate;

public interface DailyClickProjection {

    LocalDate getDate();

    Long getClicks();

}