package com.my_app.sys_plywacki.service;
import org.hibernate.Session;
//import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.my_app.sys_plywacki.model.*;

import javax.persistence.EntityManager;

@Repository
public class FileUploadDAOImpl implements FileUploadDAO {
   /* @Autowired
    private SessionFactory sessionFactory;*/

    @Autowired
    private EntityManager entityManager;


    public FileUploadDAOImpl() {
    }

   /* public FileUploadDAOImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }*/

    @Override
    @Transactional
    public void save(UploadFile uploadFile) {
        entityManager.unwrap(Session.class).getSessionFactory().getCurrentSession().save(uploadFile);
        //sessionFactory.getCurrentSession().save(uploadFile);
    }
}