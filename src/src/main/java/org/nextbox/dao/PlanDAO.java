package org.nextbox.dao;

import org.nextbox.model.Plan;

import java.util.List;

/**
 * Created by saurabh on 4/23/17.
 */
public interface PlanDAO {
    public Plan createPlan(double ratePerGB, double maxSpace);
    public Plan getPlan(double ratePerGB, double maxSpace);

    List getAllPlans();
    List getAllPlanObjects();

    Plan getPlanById(String id);

    int modifyPlan(long id, double rate, double space);
}
