package com.my_app.sys_plywacki.service;

import com.my_app.sys_plywacki.model.Person;
import com.my_app.sys_plywacki.model.Role;
import com.my_app.sys_plywacki.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Service
public class UserDetailsServiceImpl implements UserDetailsService{
    @Autowired
    private PersonRepository personRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) {
        Person person = personRepository.findByUsername(email);
        if (person == null) throw new UsernameNotFoundException(email);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();

        return new org.springframework.security.core.userdetails.User(person.getUsername(), person.getPassword(), grantedAuthorities);
    }

    private static Collection<? extends GrantedAuthority> getAuthorities(Person person) {
        String[] userRoles = person.getRoles().stream().map((role) -> role.getName()).toArray(String[]::new);
        Collection<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList(userRoles);
        return authorities;
    }
}