package com.crud.bean;

import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

public class Employee {
	
	public Integer getdId() {
		return dId;
	}
	public void setdId(Integer dId) {
		this.dId = dId;
	}
	public Integer getEmpId() {
		return empId;
	}
	public void setEmpId(Integer empId) {
		this.empId = empId;
	}
	public String getEmpName() {
		return empName;
	}
	public void setEmpName(String empName) {
		this.empName = empName;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Department getDepartment() {
		return department;
	}
	public void setDepartment(Department department) {
		this.department = department;
	}
	public Employee() {
		// TODO Auto-generated constructor stub
	}
    public Employee(Integer empId, String empName, String gender, String email, Integer dId) {
		super();
		this.empId = empId;
		this.empName = empName;
		this.gender = gender;
		this.email = email;
		this.dId = dId;
		
	}
    
	private Integer empId;
	
	@Pattern(regexp="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u4e00-\\u9fa5]{2,5})"
			,message="用户名是2-5位中文或者6-16位英文和数字的组合")
    private String empName;

    private String gender;

    @Pattern(regexp="^([a-z0-9_\\.-]+)@([\\da-z\\.-]+)\\.([a-z\\.]{2,6})$"
    		,message="邮箱格式不正确")
    private String email;

    private Integer dId;
    
    private Department department;
    
	@Override
	public String toString() {
		return "Employee [empId=" + empId + ", empName=" + empName + ", gender=" + gender + ", email=" + email
				+ ", dId=" + dId + ", department=" + department.getDeptName() + "]";
	}
	
    
}