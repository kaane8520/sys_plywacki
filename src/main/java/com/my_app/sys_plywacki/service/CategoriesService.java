package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Categories;


import java.util.List;

public interface CategoriesService {
    public void addCategories();
    List<Categories> findAll();
}
