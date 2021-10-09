package com.vti.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor  // contructor k tham số
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
    private Account creator;    // tên giống mình chỉ bổ sung thông tin thôi

    @Column(name = "ModifiedDate", updatable = true)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date modifiedDate;

    @Column(name = "CreateDate", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    private Date createDate;

}
