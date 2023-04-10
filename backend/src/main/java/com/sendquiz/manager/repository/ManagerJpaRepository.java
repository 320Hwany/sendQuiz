package com.sendquiz.manager.repository;

import com.sendquiz.manager.domain.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerJpaRepository extends JpaRepository<Manager, Long> {
}
