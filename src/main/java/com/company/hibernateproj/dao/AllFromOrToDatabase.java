package com.company.hibernateproj.dao;

import com.company.hibernateproj.HibernateSessionFactoryManager;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.util.List;

public interface AllFromOrToDatabase<E> {


    default void toDatabase(List<E> list){
        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();
        Session s = null;
        Transaction t = null;

        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            for (E c : list)
                s.save(c);
            t.commit();
        } catch (Exception e) {
            System.out.println(e);
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
    }

    default List<E> allFomDatabase(E e){
        SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();
        Session s = null;
        Transaction t = null;
        List<E> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery("from "+e.getClass().getCanonicalName());
            list = query.list();
            t.commit();
        } catch (Exception ex) {
            t.rollback();
        } finally {
            if (s != null)
                s.close();
        }
        return list;
    }
}

