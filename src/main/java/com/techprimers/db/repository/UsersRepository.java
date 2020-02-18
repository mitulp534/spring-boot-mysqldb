package com.techprimers.db.repository;

import com.techprimers.db.model.AadharcardData;
import com.techprimers.db.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UsersRepository extends JpaRepository<Users, Integer> {
    public Users findByaadharNumber(String adharNumber);
}
