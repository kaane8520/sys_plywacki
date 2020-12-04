package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Coach;
import com.my_app.sys_plywacki.model.Verification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.lang.Nullable;

import java.util.List;

public interface VerificationRepository extends JpaRepository<Verification, Long> {
    @Nullable
    List<Verification> findByIdPerson(Long idPerson);
}
