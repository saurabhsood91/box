package org.nextbox.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.nextbox.model.User;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
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

        if(list == null || list.isEmpty()) {
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

    public boolean createAccount(User user) {
        boolean success = false;
        Session session = sessionFactory.openSession();
        try{
            session.save(user);
            success = true;
//            String HQL_QUERY = "from User as o where o.userName=?,?,?";
//            Query query = session.createQuery(HQL_QUERY);
//            query.executeUpdate();
        }catch(Exception e){
            e.printStackTrace();
        }finally{
            session.close();
        }
        return success;
    }

    public List<String> getExistingUsernames(){
        Session session = sessionFactory.openSession();
        List<User> users = (List<User>) session.createCriteria(User.class).list();
        List<String> userNames = new ArrayList<String>();
        for(User u : users){
            userNames.add(u.getUserName());
        }
        return userNames;
    }

    public boolean updateCreditCardDetails(long id, String cardDetails) {
        Session session = sessionFactory.openSession();
        boolean updated = false;
        try {
            String HQL_QUERY = "update User set cardDetails=? where id=?";
            Query query = session.createQuery(HQL_QUERY);
            query.setParameter(0, cardDetails);
            query.setParameter(1, id);
            query.executeUpdate();
            updated = true;
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            session.close();
        }
        return updated;
    }

}
