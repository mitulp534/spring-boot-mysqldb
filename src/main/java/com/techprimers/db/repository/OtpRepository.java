package com.techprimers.db.repository;

import com.techprimers.db.model.OneTimeAuth;
import com.techprimers.db.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OtpRepository extends JpaRepository<OneTimeAuth, Integer> {

    public OneTimeAuth findByEmail(String uid);


    public  OneTimeAuth findByOtp(String otp);


}
