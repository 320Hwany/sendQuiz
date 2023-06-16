package com.sendquiz.certification.application;

import com.sendquiz.certification.exception.CacheNotFoundException;
import com.sendquiz.util.AcceptanceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import java.util.Objects;

import static com.sendquiz.global.eumtype.CommonConstant.CERTIFICATION_CACHE;
import static com.sendquiz.util.TestConstant.*;
import static org.assertj.core.api.Assertions.*;
import static org.assertj.core.api.Assertions.assertThat;
@AcceptanceTest
class CertificationServiceTest {

    @Autowired
    private CertificationService certificationService;

    @Autowired
    private CacheManager cacheManager;

    @BeforeEach
    public void clearCache() {
        Cache cache = cacheManager.getCache(CERTIFICATION_CACHE);
        Objects.requireNonNull(cache).clear();
    }

    @Test
    @DisplayName("이메일에 맞는 인증번호를 캐시에서 가져옵니다")
    void getCertificationNumFromCache() {
        // given
        Cache certificationCache = cacheManager.getCache(CERTIFICATION_CACHE);
        certificationCache.put(TEST_EMAIL, TEST_CERTIFICATION_NUM);

        // when
        String certificationNumFromCache = certificationService.getCertificationNumFromCache(TEST_EMAIL);

        // then
        assertThat(certificationNumFromCache).isEqualTo(TEST_CERTIFICATION_NUM);
    }

    @Test
    @DisplayName("인증번호에 대한 캐시가 존재하지 않으면 예외가 발생합니다")
    void getCertificationNumCacheNotFoundException() {
        // expected
        assertThatThrownBy(() -> certificationService.getCertificationNumFromCache(TEST_EMAIL))
                .isInstanceOf(CacheNotFoundException.class);
    }

    @Test
    @DisplayName("이메일에 맞는 인증번호를 캐시에 저장합니다")
    void putCertificationNumberToCache() {
        // when
        certificationService
                .putCertificationNumberToCache(TEST_EMAIL, TEST_CERTIFICATION_NUM);

        // then
        String certificationNumFromCache =
                cacheManager.getCache(CERTIFICATION_CACHE).get(TEST_EMAIL, String.class);

        assertThat(certificationNumFromCache).isEqualTo(TEST_CERTIFICATION_NUM);
    }
}
