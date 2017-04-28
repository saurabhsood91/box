package org.nextbox.service;

import org.nextbox.model.Plan;
import org.nextbox.model.User;

/**
 * Created by saurabh on 4/28/17.
 */
public class BillingService {
    public static String calculateBillForUser(User user) {
        // Get the plan for the user
         Plan plan = user.getPlan();
         // Calculate the usage for the user
        String actualUsage = FilesystemAPI.getUsedSpace(user.getHomeDirectory());
        double bill = plan.getRate();

        String[] parts = actualUsage.split("\\s+");
        // Convert the first part to double
        double size = Double.parseDouble(parts[0]);
        double limit = plan.getSpace();

        if(parts[1].compareTo("GB") == 0) {
            if(size > limit) {
                bill += (size - limit);
            }
        }

        String totalBill = "$".concat(Double.toString(bill));

        return totalBill;
    }
}
