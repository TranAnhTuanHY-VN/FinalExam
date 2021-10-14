package com.vti.entity;

import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Formula;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

@Data
@Entity
@Table(name = "`Account`")
public class Account implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @Column(name = "AccountID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "Email",length = 50, nullable = false)
    private String email;

    @Column(name = "Username",length = 50, nullable = false,unique = true)
    private String username;    // cx có user name thì bên kia có cái gì mình chuyển sang bên kia nó
    // ăn vào các thuộc tính đấy

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
    @Column(name = "`Status`")
    private AccountStatus status = AccountStatus.NOT_ACTIVE;
}
