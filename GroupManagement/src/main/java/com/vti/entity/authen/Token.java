package com.vti.entity.authen;

import com.vti.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "`Token`")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)//Kế thừa
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
public class Token implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "`id`", unique = true, nullable = false)
    private int id;

    @Column(name = "`token`", nullable = false, unique = true)
    private String token;

    @OneToOne
    @JoinColumn(nullable = false, name = "AccountID")
    private Account account;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "`expiryDate`", nullable = false)
    private Date expiryDate;

    public Token(String token, Account account, long expiryTime) {
        this.token = token;
        this.account = account;
        expiryDate = new Date(System.currentTimeMillis() + expiryTime);//expiryTime đã được cấu hình trong file properties
    }
}
