package com.company.hibernateproj.dao;

import com.company.hibernateproj.entity.Discipline;
import com.company.hibernateproj.entity.Role;
import com.company.hibernateproj.entity.Task;
import com.company.hibernateproj.entity.User;
import com.company.hibernateproj.enums.DisciplinesEnum;
import com.company.hibernateproj.enums.Status;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.*;
import com.company.hibernateproj.HibernateSessionFactoryManager;


import java.util.List;

public class UserDao implements AllFromOrToDatabase {

        private static SessionFactory sessionFactory = HibernateSessionFactoryManager.getSessionFactory();


        public List<User> getUserById(int id) {
                Session s = null;
                Transaction t = null;
                List<User> list = null;

                try {
                        s = sessionFactory.openSession();
                        t = s.beginTransaction();

                        Query query = s.createQuery("from User where id =:id");
                        System.out.println("UserById");
                        query.setInteger("id", id);
                        list = query.list();


                        System.out.println(list);
                        t.commit();

                } catch (Exception ex) {
                        System.out.println(ex);
                        t.rollback();
                } finally {

                        if (s != null)
                                s.close();
                }
                return list;
        }

        public void updateUser(User user1, List<Task> task, List<Role> role, Discipline discipline) {
                user1.setRole(role);
                user1.setTask(task);
                user1.setDiscipline(discipline);
                Session s = null;
                Transaction t = null;
                try {
                        s = sessionFactory.openSession();
                        t = s.beginTransaction();
                        s.update(user1);
                        t.commit();
                } catch (Exception e) {
                        System.out.println(e);
                        t.rollback();
                } finally {
                        if (s != null)
                                s.close();
                }
        }


        public List<User> getUserByTaskStatus(Status status) {
                Session s = null;
                Transaction t = null;
                List<User> list = null;
                try {
                        s = sessionFactory.openSession();
                        t = s.beginTransaction();
                        Query query = s.createQuery(" from User u join fetch  u.task t where t.status =:status ");
                        //   Query query  = s.createQuery("select u from User u join u.task t where t.status =:status ");
                        System.out.println("User By Task Status");
                        query.setParameter("status", status);
                        list = query.list();
                        t.commit();
                } catch (Exception e) {
                        System.out.println(e);
                        t.rollback();
                } finally {
                        if (s != null)
                                s.close();
                }
                return list;
        }

        public List<User> getUserByRole(String role) {
                Session s = null;
                Transaction t = null;
                List<User> list = null;
                try {
                        s = sessionFactory.openSession();
                        t = s.beginTransaction();
                        Query query = s.createQuery(" from User u join u.role r where r.roleName=:roleName ");
                        //   Query query  = s.createQuery("select u from User u join u.task t where t.status =:status ");
                        System.out.println("User By role");
                        query.setParameter("roleName", role);
                        list = query.list();
                        t.commit();
                } catch (Exception e) {
                        System.out.println(e);
                        t.rollback();
                } finally {
                        if (s != null)
                                s.close();
                }
                return list;
        }


        public List<User> getAllUsersByDiscipline(DisciplinesEnum disciplines) {
                Session s = null;
                Transaction t = null;
                List<User> list = null;
                try {
                        s = sessionFactory.openSession();
                        t = s.beginTransaction();
                        Query query = s.createQuery(" from User u join u.discipline d where d.disciplineName=:disciplines");
                        System.out.println("User by discipline");
                        query.setParameter("disciplines", disciplines);
                        list = query.list();
                        t.commit();
                } catch (Exception e) {
                        System.out.println(e);
                        t.rollback();
                } finally {
                        if (s != null)
                                s.close();
                }
                return list;
        }

        public void delete(User user) {
                Session s = null;
                Transaction t = null;

                try {
                        s = sessionFactory.openSession();
                        t = s.beginTransaction();

                        s.delete(user);
                        t.commit();
                } catch (Exception e) {
                        System.out.println(e + " Your new friend");
                        t.rollback();

                } finally {
                        if (s != null)
                                s.close();
                }

        }

}
