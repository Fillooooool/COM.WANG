package com.crud.test;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.crud.bean.Employee;
import com.github.pagehelper.PageInfo;


/*
 * 模拟url，对控制层测试
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations= {"classpath:applicationContext.xml","file:src/main/webapp/WEB-INF/dispatcherServlet-servlet.xml"})
@WebAppConfiguration
public class MvcTest {

	@Autowired
	WebApplicationContext context;
	
	MockMvc mockMvc;
	
	@Before
	public void init() {
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	@Test
	public void testPage() throws Exception {
			MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "5")).andReturn();
			MockHttpServletRequest request=result.getRequest();
			PageInfo page=(PageInfo) request.getAttribute("pageInfo");
			System.out.println("��ǰҳ��===>"+page.getPageNum());
			System.out.println("��ҳ��====>"+page.getPages());
			System.out.println("�ܼ�¼��====>"+page.getTotal());
			int[] nums=page.getNavigatepageNums();
			for (int i = 0; i < nums.length; i++) {
				System.out.print(" "+nums[i]);
			}
			
			List<Employee> list=page.getList();
			for (Employee employee : list) {
				System.out.println("ID:"+employee.getEmpId()+"===>Name:"+employee.getEmpName()+"=======>"+employee.getDepartment().getDeptName());
			}
	}
	
}
