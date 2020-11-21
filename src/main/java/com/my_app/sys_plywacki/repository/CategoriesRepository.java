package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.Categories;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriesRepository extends JpaRepository<Categories, Long> {
}
