package com.example.internintelligenceportfolioapi.dao.repository;

import com.example.internintelligenceportfolioapi.dao.entity.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExperienceRepository extends JpaRepository<ExperienceEntity, Integer> {
}
