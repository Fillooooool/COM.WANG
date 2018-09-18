package com.crud.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.crud.bean.Department;
import com.crud.dao.DepartmentMapper;

@Service
public class DepartmentService {

	@Autowired
	private DepartmentMapper departmentmapper;
	
	public List<Department> getDepts() {
		// TODO Auto-generated method stub
		List<Department> list=departmentmapper.selectByExample(null);
		return list;
	}
	
}
