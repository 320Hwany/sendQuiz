package com.sendquiz.global;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Component;

import java.util.TimeZone;

@Component
public class TimeZoneInitializer {

    @PostConstruct
    public void initialize() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }
}
