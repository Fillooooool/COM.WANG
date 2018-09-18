<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page isELIgnored="false"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<%
	pageContext.setAttribute("APP_PATH", request.getContextPath());
%>
<!-- 不以/开始的相对路径，找资源，是以当前资源的路径为基准，容易出问题 -->
<!-- 以/开始的相对路径，找资源，以当前服务器的路径为基准例如(http://localhost:3306),需要加上项目名 -->
<!-- Bootstrap -->
<link
	href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css"
	rel="stylesheet">
<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
<script
	src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
</head>
<body>
	<!-- 搭建显示页面 -->
	<div class="container">
		<!-- 标题 -->
		<div class="row">
			<div class="col-md-12">
				<h1>SSM-CRUD</h1>
			</div>
		</div>
		<!-- 按钮 -->
		<div class="row">
			<div class="col-md-4 col-md-offset-8">
				<button id="add" class="btn btn-info">新增</button>
				<button class="btn btn-warning" id="emp_deleteall_btn">删除</button>
			</div>
		</div>
		<!-- 显示表格数据 -->
		<div class="row">
			<div class="col-md-12" style="margin-top: 20px">
				<table class="table table-hover" id="emps_table">
					<thead>
						<tr>
							<th>
									<input type="checkbox" id="check_all">
							</th>
							<th>#</th>
							<th>姓名</th>
							<th>性别</th>
							<th>邮箱</th>
							<th>部门</th>
							<th>操作</th>
						</tr>
					</thead>
					<tbody>

					</tbody>
				</table>
			</div>
		</div>
		<!-- 显示分页信息 -->
		<div class="row">
			<!-- 分页文字信息 -->
			<div class="col-md-6" id="page_info_area"></div>
			<!-- 分页条 -->
			<div class="col-md-6" id="page_nav_area"></div>
		</div>
		<div class="modal fade" id="myModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title" id="myModalLabel">员工添加</h4>
					</div>
					<div class="modal-body">
						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">姓名</label>
								<div class="col-sm-9">
									<input type="text" name="empName" class="form-control"
										id="empName_add_input" placeholder="姓名">
										<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱</label>
								<div class="col-sm-9">
									<input type="text" name="email" class="form-control"
										id="email_add_input" placeholder="1000@qq.com">
										<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">性别</label>
								<div class="col-sm-10">
									<label class="radio-inline"> <input type="radio"
										name="gender" id="gender1_add_input" value="m" checked="checked"> 男
									</label> <label class="radio-inline"> <input type="radio"
										name="gender" id="gender2_add_input" value="f"> 女
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">部门</label>
								<div class="col-sm-4">
									<!-- 部门提交部门ID -->
									<select class="form-control" name="dId" id="selectdId">
									</select>
								</div>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary"  id="emp_sava_btn">保存</button>
					</div>
				</div>
			</div>
		</div>
		
		<!-- 员工修改 -->
		<div class="modal fade" id="myUpdateModal" tabindex="-1" role="dialog"
			aria-labelledby="myModalLabel">
			<div class="modal-dialog" role="document">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
						<h4 class="modal-title">员工修改</h4>
					</div>
					<div class="updatemodal-body">
						<form class="form-horizontal">
							<div class="form-group">
								<label class="col-sm-2 control-label">姓名</label>
								<div class="col-sm-9">
									<p class="form-control-static" id="empName_update_static"></p>
										<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">邮箱</label>
								<div class="col-sm-9">
									<input type="text" name="email" class="form-control"
										id="email_update_input" placeholder="1000@qq.com">
										<span class="help-block"></span>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">性别</label>
								<div class="col-sm-10">
									<label class="radio-inline"> <input type="radio"
										name="gender" id="gender1_update_input" value="m" checked="checked"> 男
									</label> <label class="radio-inline"> <input type="radio"
										name="gender" id="gender2_update_input" value="f"> 女
									</label>
								</div>
							</div>
							<div class="form-group">
								<label class="col-sm-2 control-label">部门</label>
								<div class="col-sm-4">
									<!-- 部门提交部门ID -->
									<select class="form-control" name="dId" id="updatedId">
									</select>
								</div>
							</div>

						</form>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-default" data-dismiss="modal">取消</button>
						<button type="button" class="btn btn-primary"  id="emp_update_btn">修改</button>
					</div>
				</div>
			</div>
		</div>
		
		
	</div>


	<script type="text/javascript">
		
		var totalRecord,curr;
		//页面加载完成后，发送ajax请求要到分页数据
		$(function() {
			to_page(1);
		});

		function to_page(pn) {
			$.ajax({
				url : "${APP_PATH}/empsJson",
				data : "pn=" + pn,
				type : "get",
				success : function(result) {
					console.log(result);
					//1.解析并显示员工数据
					build_emps_table(result);
					//2.解析并显示分页信息
					build_page_info(result);
					//3.解析显示分页条数据
					build_page_nav(result);
				}

			});

		}

		//1.解析并显示员工数据方法
		function build_emps_table(result) {
			//清空table
			$("#emps_table tbody").empty();
			var emps = result.extend.pageInfo.list;
			$.each(emps, function(index, item) {
				var checkBoxTd= $("<td><input type='checkbox' class='check_item'></td>");
				var empIdTd = $("<td></td>").append(item.empId);
				var empNameTd = $("<td></td>").append(item.empName);
				var genderTd = $("<td></td>").append(
						item.gender == "m" ? "男" : "女");
				var emailTd = $("<td></td>").append(item.email);
				var deptNameTd = $("<td></td>")
						.append(item.department.deptName);
				var editBtn = $("<button></button>").addClass(
						"btn btn-primary btn-sm edit_btn").append(
						$("<span></span>").addClass(
								"glyphicon  glyphicon-align-left"))
						.append("修改");
				//给修改按钮添加一个自定义属性，保存ID，为后续修改提供员工ID值
				editBtn.attr("edit-id",item.empId);
				var delBtn = $("<button></button>").addClass(
						"btn btn-danger btn-sm delete_btn").append(
						$("<span></span>")
								.addClass("glyphicon glyphicon-trash")).append(
						"删除");
				delBtn.attr("delete-id",item.empId);
				var btn = $("<td></td>").append(editBtn).append(" ").append(
						delBtn);
				//append方法执行完以后还会返回原来的元素
				$("<tr></tr>").append(checkBoxTd).append(empIdTd).append(empNameTd).append(
						genderTd).append(emailTd).append(deptNameTd)
						.append(btn).appendTo("#emps_table tbody");
			})

		}
		//2.解析并显示分页信息方法
		function build_page_info(result) {
			//清空
			$("#page_info_area").empty();
			$("#page_info_area").append(
					"当前：第" + result.extend.pageInfo.pageNum + "页,总"
							+ result.extend.pageInfo.pages + "页,总"
							+ result.extend.pageInfo.total + "条记录;");
			totalRecord=result.extend.pageInfo.total;
			curr=result.extend.pageInfo.pageNum;
		}

		//3.解析并显示分页条
		function build_page_nav(result) {
			$("#page_nav_area").empty();
			var ul = $("<ul></ul>").addClass("pagination pagination-lg");
			var firstPageLi = $("<li></li>").append($("<a></a>").append("首页"))
					.attr("href", "#");
			firstPageLi.click(function() {
				to_page(1);
			});
			var prePageLi = $("<li></li>").append(
					$("<a></a>").append("&laquo;"));

			if (result.extend.pageInfo.hasPreviousPage == false) {
				prePageLi.addClass("disabled");
			} else {
				prePageLi.click(function() {
					to_page(result.extend.pageInfo.pageNum - 1);
				});
			}
			;
			var nextPageLi = $("<li></li>").append(
					$("<a></a>").append("&raquo;"));

			if (result.extend.pageInfo.hasNextPage == false) {
				nextPageLi.addClass("disabled");
			} else {
				nextPageLi.click(function() {
					to_page(result.extend.pageInfo.pageNum + 1);
				});
			}
			var lastPageLi = $("<li></li>").append($("<a></a>").append("末页"))
					.attr("href", "#");
			lastPageLi.click(function() {
				to_page(result.extend.pageInfo.pages);
			});
			//添加首页和前一页
			ul.append(firstPageLi).append(prePageLi)
			//遍历出页码
			$.each(result.extend.pageInfo.navigatepageNums, function(index,
					item) {

				var numLi = $("<li></li>").append($("<a></a>").append(item));
				if (result.extend.pageInfo.pageNum == item) {
					numLi.addClass("active");
				}
				numLi.click(function() {
					to_page(item);
				});
				ul.append(numLi);
			});
			//添加下一页和末页
			ul.append(nextPageLi).append(lastPageLi);
			//添加到nav元素中
			var navEle = $("<nav></nav>").append(ul);
			navEle.appendTo("#page_nav_area");
		};

		//完整的表单清空方法
		function reset_form(ele){
			//重置表单内容
			$(ele)[0].reset();
			//清空表单样式
			$(ele).find("*").removeClass("has-error has-success");
			$(ele).find(".help-block").text("");
		}
		
		//添加员工dialog
		$("#add").click(function() {
			//清除表单数据(表单重置)及表单样式;
			reset_form(".modal-body form");
			//发送ajax，查出部门信息，显示是dialog中
			getDepts("#selectdId");
			//弹出模态框
			$('#myModal').modal({
				backdrop : "static"
			});
		});
		//查出所有部门信息方法,显示在dialog中
		function getDepts(ele){
			$(ele).empty();
			$.ajax({
				url : "${APP_PATH}/depts",
				type : "get",
				success : function(result) {
					//console.log(result);
					build_select(result,ele);
				}
			});
		};
		function build_select(result,ele){
			var selects=result.extend.depts;
			$.each(selects,function(index,item){
				var  select=$("<option></option>").append(item.deptName).attr("value",item.deptId);
				select.appendTo(ele);
			});
		}
		
		//校验表单数据
		function validate_add_from(){
			//1.获取表单的数据，使用正则
			var empName=$("#empName_add_input").val();
			var regName=/(^[a-zA-Z0-9_-]{6,16}$)|(^[\u4e00-\u9fa5]{2,5})/;
			if(!regName.test(empName)){
				//alert("用户名是2-5位中文或者6-16位英文和数字的组合");
				show_validate_msg("#empName_add_input","error","用户名是2-5位中文或者6-16位英文和数字的组合");
				return false
			}else{
				show_validate_msg("#empName_add_input","success","");
			};
			var email=$("#email_add_input").val();
			var regEmail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				//alert("邮箱格式不正确");
				show_validate_msg("#email_add_input","error","邮箱格式不正确");
				return false;
			}else {
				show_validate_msg("#email_add_input","success","");
			};
			
			return true;
		};
		function show_validate_msg(ele,status,msg){
			//清除状态；
			$(ele).parent().removeClass("has-error has-success");
			$(ele).next("span").text("");
				if ("error"==status) {
					$(ele).parent().addClass("has-error");
					$(ele).next("span").text(msg);
				}else if ("success"==status) {
					$(ele).parent().addClass("has-success");
					$(ele).next("span").text(msg);
					
				}
			
		};
		//点击保存
		$("#emp_sava_btn").click(function(){
				//dialog填写的表单数据校验
				if(!validate_add_from()){
					return false;
				};
				//判断用户名校验是否重复
				if ($(this).attr("ajax-va")=="error") {
					return false;
				};
				
				//发送ajax请求保存员工
 				$.ajax({
 					url:"${APP_PATH}/empsJson",
 					type:"post",
 					data:$(".modal-body form").serialize(),
					success:function(result){
						if (result.code==100) {
							alert(result.msg);
							//关闭模态框，并前往最后一页
							$('#myModal').modal('hide');
							//发送ajax请求，显示最后一页数据；
							to_page(totalRecord);
						}else {
							//有哪个字段的错误信息，就显示哪个字段的
							if (undefined != result.extend.errorFields.email) {
								show_validate_msg("#email_add_input","error",result.extend.errorFields.email);
							}
							if (undefined != result.extend.errorFields.empName) {
								show_validate_msg("#empName_add_input","error",result.extend.errorFields.empName);
							}
						}
						
					}
 				});
			
		});
		
		$("#empName_add_input").on("change",function(){
				//发送ajax校验用户名是否可用;
				var empName=this.value;
				$.ajax({
					url:"${APP_PATH}/checkUser",
					data:"empName="+empName,
					type:"post",
					success:function(result){
							if (result.code==100) {
								show_validate_msg("#empName_add_input","success","用户名可用");
								$("#emp_sava_btn").attr("ajax-va","success");
							}else {
								show_validate_msg("#empName_add_input","error",result.extend.va_msg);
								$("#emp_sava_btn").attr("ajax-va","error");
							}
					}
				});
		});
		$("#email_add_input").on("change",function(){
			var email=this.value;
			var regEmail=/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/;
			if(!regEmail.test(email)){
				//alert("邮箱格式不正确");
				show_validate_msg("#email_add_input","error","邮箱格式不正确");
			}else {
				show_validate_msg("#email_add_input","success","");
			}
});
		//给修改按钮添加绑定事件，由于jquery页面列表加载在后，绑定在前，用on方法,也就是预绑定
		$(document).on("click",".edit_btn",function(){
			//查出员工信息和部门信息，并显示
			getDepts("#updatedId");
			getEmp($(this).attr("edit-id"));
			$("#emp_update_btn").attr("edit-id",$(this).attr("edit-id"));
			$('#myUpdateModal').modal({
				backdrop : "static"
			});
			
		});
		
		
		function getEmp(id){
			$.ajax({
				url:"${APP_PATH}/empsJson/"+id,
				type:"get",
				success:function(result){
					console.log(result);
					var empDate=result.extend.emp;
					$("#empName_update_static").text(empDate.empName);
					$("#email_update_input").val(empDate.email);
					$("#myUpdateModal input[name=gender]").val([empDate.gender]);
					$("#myUpdateModal select").val([empDate.dId]);
				}
			});
		};
		
		
		$("#emp_update_btn").click(function(){
			//alert($(".updatemodal-body form").serialize());
			$.ajax({
				url:"${APP_PATH}/empsJson/"+$(this).attr("edit-id"),
				type:"put",
				data:$(".updatemodal-body form").serialize(),
				success:function(result){
					//console.log(result);
					alert(result.msg);
					//关闭对话框，回到更改前的页面
					$("#myUpdateModal").modal("hide");
					to_page(curr);
				}
			});
			
		});
		
		//删除
		$(document).on("click",".delete_btn",function(){
			//alert($(this).parents("tr").find("td:eq(1)").text());
			var empName=$(this).parents("tr").find("td:eq(2)").text()
			if(confirm("确认删除【"+empName+"】吗？")){
				$.ajax({
					url:"${APP_PATH}/empsJson/"+$(this).attr("delete-id"),
					type:"delete",
					success:function(result){
						alert(result.msg);
						to_page(curr);
					}
				});
			}
		});
		
		$("#check_all").click(function(){
			//原生dom，用prop属性的值，自定义用attr
			$(".check_item").prop("checked",$(this).prop("checked"));
		});
		$(document).on("click",".check_item",function(){
			var flag=$(".check_item:checked").length==$(".check_item").length;
			$("#check_all").prop("checked",flag);
		});
		
		//全部删除
		$("#emp_deleteall_btn").click(function(){
			var empNames="";
			var del_idList="";
			$.each($(".check_item:checked"),function(){
				empNames +=$(this).parents("tr").find("td:eq(2)").text()+",";
				del_idList +=$(this).parents("tr").find("td:eq(1)").text()+"-";
			});
			empNames=empNames.substring(0,empNames.length-1);
			del_idList=del_idList.substring(0,del_idList.length-1);
			if(confirm("确认删除【"+empNames+"】吗？")){
				$.ajax({
					url:"${APP_PATH}/empsJson/"+del_idList,
					type:"delete",
					success:function(result){
						alert(result.msg);
						to_page(curr);
					}
				});
			}
		});
	</script>

</body>
</html>