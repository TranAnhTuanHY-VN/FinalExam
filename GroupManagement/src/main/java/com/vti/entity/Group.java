package com.vti.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Group`")
public class Group implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "GroupID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(name = "GroupName",length = 50,nullable = false,unique = true)
    private String name;

    @Column(name = "MemberNum")
    private int memberNum;

    @OneToMany(mappedBy = "group")
    private List<Account> accounts;

    @ManyToOne
    @JoinColumn(name = "CreatorID",nullable = false,updatable = false)
    private Account creator;

    @Column(name = "ModifiedDate", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date modifiedDate;

    @Column(name = "CreateDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    public Group() {
    }

    public Group(String name) {
        this.name = name;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMemberNum() {
        return memberNum;
    }

    public void setMemberNum(int memberNum) {
        this.memberNum = memberNum;
    }

    public List<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(List<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getCreator() {
        return creator;
    }

    public void setCreator(Account creator) {
        this.creator = creator;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}
