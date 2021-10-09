package com.vti.form;

import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

public class GroupFormForFilter {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDate;

    public GroupFormForFilter() {
    }

    public Date getMinDate() {
        return minDate;
    }

    public void setMinDate(Date minDate) {
        this.minDate = minDate;
    }

    public Date getMaxDate() {
        return maxDate;
    }

    public void setMaxDate(Date maxDate) {
        this.maxDate = maxDate;
    }
}
