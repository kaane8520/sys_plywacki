package com.my_app.sys_plywacki.repository;

import com.my_app.sys_plywacki.model.CategoriesOnCompetition;
import com.my_app.sys_plywacki.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriesOnCompetitionRepository extends JpaRepository<CategoriesOnCompetition, Long> {
    @Override
    List<CategoriesOnCompetition> findAll();
    CategoriesOnCompetition findCategoriesOnCompetitionById(Long along);
    List<CategoriesOnCompetition> findCategoriesOnCompetitionByPlayers(Player player);
}
