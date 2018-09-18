package com.crud.test;


import java.util.UUID;

import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.crud.bean.Department;
import com.crud.bean.Employee;
import com.crud.dao.DepartmentMapper;
import com.crud.dao.EmployeeMapper;

/*
 * 测试dao层
 * 用junit进行mappper测试添加数据是否成功
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml"})
public class MapperTest {
	//自动封装mapper和session
	@Autowired
	DepartmentMapper departmentMapper;
	@Autowired
	EmployeeMapper employeeMapper;
	@Autowired
	SqlSession sqlSession;
	
	@Test
	public void testCRUD() {

		System.out.println(departmentMapper);
		
		EmployeeMapper mapper =sqlSession.getMapper(EmployeeMapper.class);
		for (int i = 0; i < 1000; i++) {
			String uid=UUID.randomUUID().toString().substring(0, 5)+i;
			mapper.insertSelective(new Employee(null, uid, "m", uid+"@meishan.com", 1));
		}
		System.out.println("添加成功");
		
	}
}
