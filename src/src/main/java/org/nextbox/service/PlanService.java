package org.nextbox.service;

import org.nextbox.model.Plan;

import java.util.List;

/**
 * Created by aniru on 4/26/2017.
 */
public interface PlanService {
    public Plan createPlan(double ratePerGB, double maxSpace);
    public Plan getPlan(double ratePerGB, double maxSpace);

    public List getAllPlans();

    public Plan getPlanById(String id);

    public int modifyPlan(long id, double rate, double space);
}
