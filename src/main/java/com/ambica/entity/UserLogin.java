package com.ambica.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;


@Entity(name="login_tab")
public class UserLogin {

	@Id
	@Column(name="ph_no")
	public String phNo;
	

	@Column(name="f_name")
	public String firstName;
	

	@Column(name="l_name")
	public String lastName;
	

	@Column(name="pwd")
	public String pwd;
	
	@Column(name="last_login_time")
	public String lld;

	public String getLld() {
		return lld;
	}

	public void setLld(String lld) {
		this.lld = lld;
	}

	public String getPhNo() {
		return phNo;
	}

	public void setPhNo(String phNo) {
		this.phNo = phNo;
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

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	@Override
	public String toString() {
		return "UserLogin [phNo=" + phNo + ", firstName=" + firstName + ", lastName=" + lastName + ", pwd=" + pwd + "]";
	}
	
	
}
