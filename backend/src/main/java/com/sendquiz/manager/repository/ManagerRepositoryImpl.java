package com.sendquiz.manager.repository;

import com.sendquiz.member.repository.MemberJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@RequiredArgsConstructor
@Repository
public class ManagerRepositoryImpl implements ManagerRepository {

    private final MemberJpaRepository memberJpaRepository;
}
