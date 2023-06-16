package com.sendquiz.certification.application;

import com.sendquiz.certification.exception.CacheNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Objects;

import static com.sendquiz.global.eumtype.CommonConstant.CERTIFICATION_CACHE;


@Slf4j
@RequiredArgsConstructor
@Service
public class CertificationService {

    private final CacheManager cacheManager;

    @Cacheable(value = CERTIFICATION_CACHE, key = "#email")
    public String getCertificationNumFromCache(String email) {
        String certificationNumber = Objects
                .requireNonNull(cacheManager.getCache(CERTIFICATION_CACHE)).get(email, String.class);

        if (certificationNumber == null) {
            log.error("CacheNotFoundException");
            throw new CacheNotFoundException();
        }
        log.info("getCertificationNumFromCache");
        return certificationNumber;
    }

    public void putCertificationNumberToCache(String email, String certificationNumber) {
        Cache certificationCache = cacheManager.getCache(CERTIFICATION_CACHE);
        certificationCache.put(email, certificationNumber);
        log.info("putCertificationNumberToCache");
    }
}
