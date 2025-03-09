package com.GraduationProject.GraduationProject.repo;

import com.GraduationProject.GraduationProject.entity.Usr;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsrRepo extends JpaRepository<Usr,Integer> {
    Usr findByUsername(String username);
}
