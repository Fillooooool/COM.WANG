package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.bean.Employee;
import com.crud.bean.EmployeeExample;
import com.crud.bean.EmployeeExample.Criteria;
import com.crud.dao.EmployeeMapper;

@Service
public class EmployeeService {

	@Autowired
	EmployeeMapper employeeMapper;
	
	
	/*
	 *返回所有数据
	 */
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		List<Employee> list=employeeMapper.selectByExampleWithDept(null);
		return list;
	}
	

	public void saveEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}


	public boolean checkUser(String empName) {
		// TODO Auto-generated method stub
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count = employeeMapper.countByExample(example);
		return count==0;
	}


	public Employee getEmp(Integer id) {
		// TODO Auto-generated method stub
		Employee employee=employeeMapper.selectByPrimaryKey(id);
		return employee;
	}


	public void updateEmp(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}


	public void deleteEmp(Integer id) {
		// TODO Auto-generated method stub
		employeeMapper.deleteByPrimaryKey(id);
	}
	

	public void deleteList(List<Integer> str_ids) {
		// TODO Auto-generated method stub
		EmployeeExample example=new EmployeeExample();
		Criteria criteria = example.createCriteria();
		//delete from xxx where emp_id in(1,2,3,4);
		criteria.andEmpIdIn(str_ids);
		employeeMapper.deleteByExample(example);
	}



}
