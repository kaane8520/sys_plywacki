package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoleRepository extends JpaRepository<Role, Long>{
    Role findByPerson(Person person);
    boolean existsByPerson(Person person);
}