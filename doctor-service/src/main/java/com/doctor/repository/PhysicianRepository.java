package com.doctor.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.doctor.model.Doctor;

@Repository
public interface PhysicianRepository extends JpaRepository<Doctor,Long>{
    
    public Optional<Doctor> findByUserId(Long userId);
}
