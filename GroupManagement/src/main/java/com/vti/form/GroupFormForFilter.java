package com.vti.form;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Data
@NoArgsConstructor
public class GroupFormForFilter {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date minDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private Date maxDate;
}
