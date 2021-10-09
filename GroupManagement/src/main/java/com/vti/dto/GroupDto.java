package com.vti.dto;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class GroupDto {
    private short id;

    private String name;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createDate;

    private AccountDto creator;

    private int memberNum;

    public GroupDto(short id, String name, Date createDate, AccountDto creator, int memberNum) {
        this.id = id;
        this.name = name;
        this.createDate = createDate;
        this.creator = creator;
        this.memberNum = memberNum;
    }

    public short getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public AccountDto getCreator() {
        return creator;
    }

    public int getMemberNum() {
        return memberNum;
    }
}
