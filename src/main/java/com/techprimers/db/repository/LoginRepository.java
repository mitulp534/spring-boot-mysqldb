package com.techprimers.db.repository;

import com.techprimers.db.model.Login;
import com.techprimers.db.model.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LoginRepository  extends JpaRepository<Users, Integer>{



}
