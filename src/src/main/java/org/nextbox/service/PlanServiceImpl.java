package org.nextbox.service;

import org.nextbox.dao.PlanDAO;
import org.nextbox.model.Plan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by aniru on 4/26/2017.
 */
@Service("planService")
public class PlanServiceImpl implements PlanService {
    @Autowired
    private PlanDAO planDAO;

    public Plan createPlan(double ratePerGB, double maxSpace) {
        return planDAO.createPlan(ratePerGB,maxSpace);
    }

    public Plan getPlan(double ratePerGB, double maxSpace) {
        return planDAO.getPlan(ratePerGB,maxSpace);
    }

    public List getAllPlans() {
        return planDAO.getAllPlans();
    }

    public Plan getPlanById(String id) {
        return planDAO.getPlanById(id);
    }

    public int modifyPlan(long id, double rate, double space) {
        return planDAO.modifyPlan(id,rate,space);
    }
}
