package com.vti.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table(name = "`Reset_Password_Token`")
public class ResetPasswordToken implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "`id`", unique = true, nullable = false)
	private int id;

	@Column(name = "`token`", nullable = false, length = 36, unique = true)
	private String token;

	@OneToOne(targetEntity = Account.class, fetch = FetchType.EAGER)
	@JoinColumn(nullable = false, name = "account_id")
	private Account account;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "`expiryDate`", nullable = false)
	private Date expiryDate;

	public ResetPasswordToken() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getExpiryDate() {
		return expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

}
