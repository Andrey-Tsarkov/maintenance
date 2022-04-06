package com.example.maintenance.repository;

import com.example.maintenance.entity.EventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventJpaRepository extends JpaRepository<EventEntity, Integer> {
}
