package com.vti.form;

import lombok.Data;

import javax.validation.constraints.Pattern;

@Data
public class GroupFormForCreating {
    private String name;

    private int creatorID;

}
