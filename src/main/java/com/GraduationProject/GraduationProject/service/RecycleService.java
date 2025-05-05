package com.GraduationProject.GraduationProject.service;

import com.GraduationProject.GraduationProject.entity.Recycle;
import com.GraduationProject.GraduationProject.entity.Usr;
import com.GraduationProject.GraduationProject.repo.RecycleRepo;
import com.GraduationProject.GraduationProject.repo.UsrRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RecycleService {

    @Autowired
    private RecycleRepo recycleRepo;

    @Autowired
    private UsrRepo usrRepo;

    public List<Recycle> getRecyclesByUserId(Integer userId) {
        Usr user = usrRepo.findById(userId).orElse(null);
        if (user != null) {
            return recycleRepo.findByUsr(user);
        }
        return null;
    }
}