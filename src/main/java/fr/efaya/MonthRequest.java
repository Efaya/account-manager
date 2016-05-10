package fr.efaya;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by KTIFA FAMILY on 07/05/2016.
 */
public class MonthRequest implements Serializable {
    private Date monthRef;
    private Integer pastMonths;

    public Date getMonthRef() {
        return monthRef;
    }

    public void setMonthRef(Date monthRef) {
        this.monthRef = monthRef;
    }

    public Integer getPastMonths() {
        return pastMonths;
    }

    public void setPastMonths(Integer pastMonths) {
        this.pastMonths = pastMonths;
    }
}
