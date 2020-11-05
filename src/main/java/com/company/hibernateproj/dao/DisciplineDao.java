package com.company.hibernateproj.dao;

import com.company.hibernateproj.HibernateSessionFactoryManager;
import com.company.hibernateproj.entity.Discipline;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class DisciplineDao implements AllFromOrToDatabase{

    private static SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();

    public List<Discipline> getDisciplinesByMembersJoins(int number){
        Session s = null;
        Transaction t = null;
        List<Discipline> list = null;
        try {
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            Query query  = s.createQuery(" from Discipline d where d.members.size <: number");
            query.setParameter("number",number);


            list = query.list();
            t.commit();
        }catch (Exception e){
            System.out.println(e);
            t.rollback();
        }finally{
            if(s!=null)
                s.close();
        }
        return list;
    }


    public void updateDiscipline( Discipline discipline){
        Session s = null;
        Transaction t = null;
        try{
            s = sessionFactory.openSession();
            t = s.beginTransaction();
            s.update(discipline);
            t.commit();
        }
        catch(Exception e){
            System.out.println(e);
            t.rollback();
        }finally{
            if(s != null)
                s.close();
        }
    }
}
