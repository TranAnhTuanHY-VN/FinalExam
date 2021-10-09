package com.vti.entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "`Account`")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AccountID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private short id;

    @Column(name = "Email",length = 50, nullable = false,unique = true)
    private String email;

    @Column(name = "Username",length = 50, nullable = false,unique = true)
    private String username;

    @Column(name = "Password",length = 800,nullable = false)
    private String password;

    @Column(name = "FirstName",length = 50, nullable = false)
    private String firstName;

    @Column(name = "lastName",length = 50, nullable = false)
    private String lastName;

    @Formula("concat(FirstName,' ',LastName)")
    private String fullName;

    @Column(name = "Role")
    private String role;

    @ManyToOne
    @JoinColumn(name = "GroupID")
    private Group group;

    @OneToMany(mappedBy = "creator")
    private List<Group> createdGroup;

    @Column(name = "CreateDate")
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

    @Enumerated(EnumType.ORDINAL)
    @Column(name = "`status`")
    private AccountStatus status = AccountStatus.NOT_ACTIVE;

    public Account() {
    }

    public Account(String email, String username, String password, String firstName, String lastName) {
        this.email = email;
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Account(String email, String username, String password) {
        this.email = email;
        this.username = username;
        this.password = password;
    }

    public short getId() {
        return id;
    }

    public void setId(short id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public List<Group> getCreatedGroup() {
        return createdGroup;
    }

    public void setCreatedGroup(List<Group> createdGroup) {
        this.createdGroup = createdGroup;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }
}
