package com.vti.entity.authen;

import com.vti.entity.Account;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

// cx như bthg thôi lát sửa lại db
@Entity
@Table(name = "`Token`")
@Data
@NoArgsConstructor
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)   // kế t hừa
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)   // cái này nó như
// kiểu class kế thừa thì phải có cái gì đó phân biệt giữa class con ấy
//name = "Type" cái này dưới db , discriminatorType này là phân biệt dạng string
// như kiểu type refreshtoken vs registoken ấy
// k phải 1 2 3 đau
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

    // cái time này nên cấu hình file properties ấy dùng để thế này vì k chỉnh đc
    public Token(String token, Account account, long expiryTime) {
        this.token = token;
        this.account = account;
        expiryDate = new Date(System.currentTimeMillis() + expiryTime);
    }
}
