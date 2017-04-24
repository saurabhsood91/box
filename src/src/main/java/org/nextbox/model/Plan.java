package org.nextbox.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by saurabh on 4/23/17.
 */

@Entity
@Table(name = "plan")
public class Plan implements Serializable {

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Id
    @GeneratedValue
    @Column(name="id", length=10)
    private long id;

    @Column(name="rate_per_gb")
    private double rate;

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public double getSpace() {
        return space;
    }

    public void setSpace(double space) {
        this.space = space;
    }

    @Column(name="space_limit_gb")
    private double space;
}
