package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.PlayerPersonConnection;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerPersonConnectionRepository extends JpaRepository<PlayerPersonConnection, Long> {
    @Override
    Optional<PlayerPersonConnection> findById(Long aLong);


}
