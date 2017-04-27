package org.nextbox.managers;

import org.nextbox.dao.PlanDAO;
import org.nextbox.dao.PlanDAOImpl;
import org.nextbox.model.Plan;
import org.nextbox.service.PlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by saurabh on 4/23/17.
 */

@Service("planManager")
public class PlanManager {

    @Autowired
    private PlanService planService;

    private static Map<Long,Plan> allPlans = new HashMap<Long,Plan>();

    public boolean doesPlanExist(double ratePerGB, double spacePerGB) {
        Plan plan = planService.getPlan(ratePerGB, spacePerGB);
        if(plan == null) {
            return false;
        }
        return true;
    }

    public boolean createPlan(double ratePerGB, double spacePerGB) {
        Plan plan = planService.createPlan(ratePerGB, spacePerGB);
        if(plan != null) {
            return true;
        }
        return false;
    }

    public List getAllPlans() {
        checkPlanMap();
        return new ArrayList(allPlans.values());
    }

    public Plan getPlanById(String id) {
        this.checkPlanMap();
        return allPlans.get(Long.parseLong(id));
    }

    public int modifyPlan(String id, String rate, String space) {
        long planId = Integer.parseInt(id);
        double planRate = Double.parseDouble(rate);
        double planSpace = Double.parseDouble(space);
        return planService.modifyPlan(planId, planRate, planSpace);
    }

    private void checkPlanMap(){
        if(allPlans.isEmpty()) {
            List<Plan> plans = planService.getAllPlans();
            for(Plan p : plans){
                allPlans.put(p.getId(),p);
            }
        }
    }
}
