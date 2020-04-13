package com.techprimers.db.repository;

import com.techprimers.db.model.Vote;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VoteRepository extends JpaRepository<Vote,Integer> {
    public Vote findByVoter(String vote);

    public List<Vote> findAllByOrderByIdAsc();
}
