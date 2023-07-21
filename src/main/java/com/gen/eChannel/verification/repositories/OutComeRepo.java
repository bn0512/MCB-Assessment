package com.gen.eChannel.verification.repositories;

import com.gen.eChannel.verification.entities.OutCome;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutComeRepo extends JpaRepository<OutCome, Long> {
}
