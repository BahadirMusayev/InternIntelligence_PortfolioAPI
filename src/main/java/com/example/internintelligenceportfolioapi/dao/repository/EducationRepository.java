package com.example.internintelligenceportfolioapi.dao.repository;

import com.example.internintelligenceportfolioapi.dao.entity.EducationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducationRepository extends JpaRepository<EducationEntity, Integer> {
}
