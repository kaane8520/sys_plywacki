package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Role;
import com.my_app.sys_plywacki.repository.RoleRepository;
import com.my_app.sys_plywacki.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;

@Service
public class PersonServiceImpl implements PersonService {
	@Autowired
	private PersonRepository personRepository;
	@Autowired
	private RoleRepository roleRepository;
	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;


	public PersonServiceImpl() throws SQLException {
	}

	@Override
	public void save(Person person) {
		person.setPassword(bCryptPasswordEncoder.encode(person.getPassword()));
		person.setRoles(new HashSet<>(roleRepository.findAll()));// ?????
		personRepository.save(person);
	}

	@Override
	public Person findByUsername(String username) {
		return personRepository.findByUsername(username);
	}

	@Override
	public void add_role(Person person, Role role) {
		//Set<Role> newRoles = person.getRoles();
		//if (newRoles == null) {
		Set <Role> newRole = new HashSet<Role>();
		//}
		newRole.add(role);
		System.out.println("Person roles: ");
        for (Role x : newRole) {
            System.out.println(x.getName());
        }
		person.setRoles(newRole);
		//szukam id_roli
		roleRepository.save(role); //takie zapisywanie powinno się odbywać przy starcie aplikacji
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities(Role role) {
		final List<SimpleGrantedAuthority> authorities = new LinkedList<>();

		authorities.add(new SimpleGrantedAuthority(role.getName()));
		return authorities;
	}


}