package com.crud.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import com.crud.bean.Employee;
import com.crud.bean.Msg;
import com.crud.service.EmployeeService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;



@Controller
public class EmployeeController {
		
		@Autowired
		EmployeeService employeeService;
		
		/**
		 * 批量或单个
		 * 带-为批量
		 * @param id
		 * @return
		 */
		@ResponseBody
		@RequestMapping(value="/empsJson/{ids}",method=RequestMethod.DELETE)
		public Msg deleteEmp(@PathVariable("ids")String ids) {
			//批量
			if (ids.contains("-")) {
				String [] str_ids=ids.split("-");
				List<Integer> del_ids=new ArrayList<Integer>();
				for (String string : str_ids) {
					del_ids.add(Integer.parseInt(string));
				}
				employeeService.deleteList(del_ids);
				//单个
			}else {
				Integer id= Integer.parseInt(ids);
				employeeService.deleteEmp(id);
			}
			return Msg.success();
		}
		
		/**
		 * 直接发送ajax=put形式的请求
		 * 封装的数据
		 * Employee [empId=1014, empName=null, gender=null, email=null, did=null, department=null]
		 *问题，请求中有数据，但是employee对象封装不上
		 *原始为tomcat问题：
		 *				将请求中的数据，封装成一个map
		 *				request.getParameter("")从这个map中取值;
		 *				springmvc 封装pojo对象的时候会把pojo中的每个属性的值，都用request.getparameter("");
		 *ajax发送的put请求，请求体中的数据，equest.getParameter("")都无法获取，tomcat一看是put请求，就不会封装请求体中的数据为map
		 *只有post请求体才会封装请求体
		 *解决方案，配置put过滤器
		 */
		//员工更新方法
		@ResponseBody
		@RequestMapping(value = "/empsJson/{empId}", method = RequestMethod.PUT)
	    public Msg saveEmp(Employee employee) {
			System.out.println("请求更新的数据为"+employee);
	        employeeService.updateEmp(employee);
	        return Msg.success();
	    }
		
		
		@RequestMapping(value="/empsJson/{id}",method=RequestMethod.GET)
		@ResponseBody
		public Msg getEmp(@PathVariable("id")Integer id) {
			Employee employee=employeeService.getEmp(id);
			return Msg.success().add("emp", employee);
		}
		
		
		@ResponseBody
		@RequestMapping("/checkUser")
		public Msg checkEmp(@RequestParam("empName")String empName) {
			String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\\u4e00-\\u9fa5]{2,5})";
			if (!empName.matches(regx)) {
				return Msg.fail().add("va_msg", "用户名是2-5位中文或者6-16位英文和数字的组合");
			}
			boolean b = employeeService.checkUser(empName);
			if (b) {
				return Msg.success();
			}else {
				return Msg.fail().add("va_msg", "用户名不可用");
			}
		}
		
		@RequestMapping(value="/empsJson",method=RequestMethod.POST)
		@ResponseBody
		public Msg saveEmp(@Valid Employee employee,BindingResult result) {
			if (result.hasErrors()) {
				Map<String, Object> map=new HashMap<String, Object>();
				List<FieldError> errors = result.getFieldErrors();
				for (FieldError fieldError : errors) {
					System.out.println("错误字段名"+fieldError.getField());
					System.out.println("错误信息"+fieldError.getDefaultMessage());
					map.put(fieldError.getField(), fieldError.getDefaultMessage());
					
				}
				return Msg.fail().add("errorFields", map);
			}else {
				
			
			employeeService.saveEmp(employee);
			return Msg.success();
			}
		}
		
		/**
		 * 自动封装jackson @ResponseBody
		 * @param pn
		 * @param model
		 * @return
		 */
		
		@RequestMapping("/empsJson")
		@ResponseBody
		public Msg getEmpsWithJson(@RequestParam(value="pn",defaultValue="1")Integer pn,Model model) {
			PageHelper.startPage(pn,10);
			List<Employee> emps=employeeService.getAll();
			PageInfo page=new PageInfo(emps,5);
			return Msg.success().add("pageInfo",page);
		}
		
		
		@RequestMapping("/emps")
		public String getEmps(@RequestParam(value="pn",defaultValue="1")Integer pn,Model model) {
			PageHelper.startPage(pn,5);
			List<Employee> emps=employeeService.getAll();
			PageInfo page=new PageInfo(emps,5);
			model.addAttribute("pageInfo", page);
			return "list";
			
		}
}
