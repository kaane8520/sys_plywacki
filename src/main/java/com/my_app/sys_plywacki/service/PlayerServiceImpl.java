package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Player;
import com.my_app.sys_plywacki.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.swing.tree.RowMapper;
import javax.swing.tree.TreePath;
import javax.transaction.Transactional;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class PlayerServiceImpl implements PlayerService{
    @Autowired
    private PlayerRepository repository;
    @Autowired
    JdbcTemplate template;


    public List<Player> findAll(){
        List<Player> result = (List<Player>) repository.findAll();
        if(result.size() > 0) {
            return result;
        } else {
            return new ArrayList<Player>();
        }
    }
	@Override
	public void save(Player player) {
		repository.save(player);
	}

}
