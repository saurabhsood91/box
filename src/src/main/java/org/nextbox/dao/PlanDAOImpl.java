package org.nextbox.dao;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.nextbox.model.Plan;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by saurabh on 4/23/17.
 */

@Repository("planDAO")
public class PlanDAOImpl implements PlanDAO {
    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Resource(name="sessionFactory")
    protected SessionFactory sessionFactory;

    public Plan getPlan(double ratePerGB, double maxSpace) {

        Session session = sessionFactory.openSession();

        String HQL_QUERY = "from Plan as o where o.rate=? and o.space=?";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter(0, ratePerGB);
        query.setParameter(1, maxSpace);

        List list = query.list();
        if(list.size() == 0) {
            return null;
        }
        // Found a plan
        Plan plan = (Plan)list.get(0);
        return plan;
    }

    public List getAllPlans() {
        Session session = sessionFactory.openSession();
        List plans = session.createCriteria(Plan.class).setProjection(Projections.property("id")).list();
        return plans;
    }

    public Plan getPlanById(String id) {
        Session session = sessionFactory.openSession();
        long planId = Integer.parseInt(id);

        String HQL_QUERY = "from Plan as o where o.id=?";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter(0, planId);

        List list = query.list();
        if(list.size() == 0) {
            return null;
        }
        Plan plan = (Plan)list.get(0);
        return plan;

    }

    public int modifyPlan(long id, double rate, double space) {
        Session session = sessionFactory.openSession();
        String HQL_QUERY = "update Plan set rate=?, space=? where id=?";
        Query query = session.createQuery(HQL_QUERY);
        query.setParameter(0, rate);
        query.setParameter(1, space);
        query.setParameter(2, id);
        return query.executeUpdate();
    }

    public Plan createPlan(double ratePerGB, double maxSpace) {
        Session session = sessionFactory.openSession();
        try {
            Plan plan = new Plan();
            plan.setSpace(maxSpace);
            plan.setRate(ratePerGB);
            session.save(plan);
            return plan;
        } catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
