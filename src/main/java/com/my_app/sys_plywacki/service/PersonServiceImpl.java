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
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.sql.*;
import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;
import java.util.List;
import java.util.ArrayList;

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
		person.setRoles(new HashSet<>());// ?????
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
	@Override
	public void update_user_role_if_exists() {
       	//System.out.println("update_user_role_if_exists");
    	Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Person p = this.findByUsername(auth.getName());
        System.out.println("User name: "+p.getUsername());
        
        Set<Role> roles = p.getRoles();
        
        List<SimpleGrantedAuthority> updatedAuthorities = new ArrayList<SimpleGrantedAuthority>();
        
        //System.out.println("Checking user authorities...");
        
        for (Role x : roles) {
        	System.out.println("Your role is: "+x.getName());
        	SimpleGrantedAuthority authority = new SimpleGrantedAuthority(x.getName());
        	updatedAuthorities.add(authority);
        	
        }
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(
                        SecurityContextHolder.getContext().getAuthentication().getPrincipal(),
                        SecurityContextHolder.getContext().getAuthentication().getCredentials(),
                        updatedAuthorities)
        		);
        System.out.println("Authorities updates during login process");
        System.out.println(updatedAuthorities);
	}
	public List<Person> findAll(){
		List<Person> result = (List<Person>) personRepository.findAll();
		if(result.size() > 0) {
			return result;
		} else {
			return new ArrayList<Person>();
		}
	}

}