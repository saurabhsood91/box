package org.nextbox.dao;

/**
 * Created by saurabh on 3/19/17.
 */

import org.hibernate.SessionFactory;
import org.nextbox.model.AbstractUser;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Query;
import java.util.List;

import javax.annotation.Resource;

@Repository("loginDAO")
public class LoginDAOImpl implements LoginDAO {

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public boolean checkLogin(String userName, String password) {
        Session session = sessionFactory.openSession();
        boolean userFound = false;

        String SQL_QUERY = "from AbstractUser as o where o.userName=? and o.password=?";
        Query query = session.createQuery(SQL_QUERY);

        query.setParameter(0, userName);
        query.setParameter(1, password);

        List list = query.list();
        if((list != null) && list.size() > 0) {
            userFound = true;
        }
        session.close();
        return userFound;
    }
}
