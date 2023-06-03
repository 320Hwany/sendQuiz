package com.sendquiz.global;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

import static com.sendquiz.global.constant.CommonConstant.ASIA_SEOUL;

@Component
public class TimeZoneInitializer {

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone(ASIA_SEOUL));
    }
}
