package com.sendquiz.certification.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Certification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private Long id;

    private String email;

    private String certificationNum;

    @Builder
    public Certification(String email, String certificationNum) {
        this.email = email;
        this.certificationNum = certificationNum;
    }

    public static Certification toCertification(String email, String certificationNum) {
        return Certification.builder()
                .email(email)
                .certificationNum(certificationNum)
                .build();
    }
}
