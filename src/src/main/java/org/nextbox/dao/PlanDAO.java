package org.nextbox.dao;

import org.nextbox.model.Plan;

/**
 * Created by saurabh on 4/23/17.
 */
public interface PlanDAO {
    public Plan createPlan(double ratePerGB, double maxSpace);
    public Plan getPlan(double ratePerGB, double maxSpace);
}
