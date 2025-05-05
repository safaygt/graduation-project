package com.GraduationProject.GraduationProject.repo;

import com.GraduationProject.GraduationProject.entity.Recycle;
import com.GraduationProject.GraduationProject.entity.Usr;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecycleRepo extends JpaRepository<Recycle, Integer> {
    List<Recycle> findByUsr(Usr usr);
}