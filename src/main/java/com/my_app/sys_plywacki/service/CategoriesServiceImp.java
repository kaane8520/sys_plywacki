package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Categories;
import com.my_app.sys_plywacki.model.RefereeRoles;
import com.my_app.sys_plywacki.repository.CategoriesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
@Service
public class CategoriesServiceImp implements CategoriesService{
    @Autowired
    CategoriesRepository categoriesRepository;
    @Override
    public void addCategories() {

            List<String> Categories = Arrays.asList("50m dowolny", "50m grzbietowy", "50m klasyczny",
                    "50 motylkowy", "100m dowolny", "100m grzbietowy", "100m klasyczny",
                    "100 motylkowy", "100 zmienny");
            Long i = 1L;
            for(String rn: Categories){
                Categories category = new Categories();
                category.setIdcategory(i);
                category.setNamecategory(rn);

                System.out.println("Id kategorii: " + category.getIdcategory() + " nazwa kategorii: " + category.getNamecategory());
                categoriesRepository.save(category);
                i++;
            }

    }

    @Override
    public List<Categories> findAll() {
        List<Categories> result = (List<Categories>) categoriesRepository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Categories>();
        }
    }
}
