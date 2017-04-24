package org.nextbox.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nextbox.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by saurabh on 3/21/17.
 */
@Repository("userDAO")
public class UserDAOImpl implements UserDAO {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public User getUserByUsername(String username) {
        Session session = sessionFactory.openSession();

        String HQL_QUERY = "from User as o where o.userName=?";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter(0, username);

        List list = query.list();

        if(list == null) {
            return null;
        }
        User user = (User)list.get(0);
        return user;
    }

    public boolean modifyActivationStatus(String userName, String activationStatus) {

        Session session = sessionFactory.openSession();

        String HQL_QUERY = "update User set activation_status=? where userName=?";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter(0, activationStatus);
        query.setParameter(1, userName);
        query.executeUpdate();
        return true; //
    }




}
