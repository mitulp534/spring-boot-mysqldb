package com.techprimers.db.repository;

import com.techprimers.db.model.AadharcardData;
import com.techprimers.db.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


public interface AadharRepository extends JpaRepository<AadharcardData, Integer> {
    public AadharcardData findByaadharNumber(String adharNumber);
}
