package org.nextbox.managers;

import org.nextbox.dao.PlanDAO;
import org.nextbox.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by saurabh on 4/23/17.
 */

@Service("planManager")
public class PlanManager {

    @Autowired
    private PlanDAO planDAO;

    public boolean doesPlanExist(double ratePerGB, double spacePerGB) {
        Plan plan = planDAO.getPlan(ratePerGB, spacePerGB);
        if(plan == null) {
            return false;
        }
        return true;
    }

    public boolean createPlan(double ratePerGB, double spacePerGB) {
        Plan plan = planDAO.createPlan(ratePerGB, spacePerGB);
        if(plan != null) {
            return true;
        }
        return false;
    }
}
