package com.gen.eChannel.verification.repositories;

import com.gen.eChannel.verification.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StatusRepo extends JpaRepository<Status, Long> {

    Optional<Status> findByName(String name);

    //Status findByStatusName(String statusName);

}
