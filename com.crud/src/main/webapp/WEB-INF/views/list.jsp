<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page isELIgnored="false" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>员工列表</title>
<% 
pageContext.setAttribute("APP_PATH",request.getContextPath());
%>
<!-- 不以/开始的相对路径，找资源，是以当前资源的路径为基准，容易出问题 -->
<!-- 以/开始的相对路径，找资源，以当前服务器的路径为基准例如(http://localhost:3306),需要加上项目名 -->
<!-- Bootstrap -->
    <link href="${APP_PATH}/static/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
	<script src="https://cdn.bootcss.com/jquery/1.12.4/jquery.min.js"></script>
	<script src="${APP_PATH}/static/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
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
  		<button  class="btn btn-info">新增</button>
  		<button  class="btn btn-warning">删除</button>
  		</div>
		</div>
	<!-- 显示表格数据 -->
		<div class="row">
		<div class="col-md-12" style="margin-top:20px">
			<table class="table table-hover">
				<tr>
					<th>#</th>
					<th>姓名</th>
					<th>性别</th>
					<th>邮箱</th>
					<th>部门</th>
					<th>操作</th>
				</tr>
				<c:forEach items="${pageInfo.list}" var="emp">
								<tr>
									<th>${emp.empId}</th>
									<th>${emp.empName}</th>
									<th>${emp.gender=="m"?"男	":"女"}</th>
									<th>${emp.email}</th>
									<th>${emp.department.deptName}</th>
									<th>
									<button type="button" class="btn btn-primary btn-sm">
									<span class="glyphicon  glyphicon-align-left" aria-hidden="true"></span>
									修改</button>
									<button type="button" class="btn btn-danger btn-sm">
									<span class="glyphicon glyphicon-trash" aria-hidden="true"></span>
									删除</button>
									</th>
								</tr>
				</c:forEach>
			</table>
		</div>
		</div>
	<!-- 显示分页信息 -->
		<div class="row">
		<!-- 分页文字信息 -->
			<div class="col-md-6">
				当前：第${pageInfo.pageNum }页,总.${pageInfo.pages }页,总${pageInfo.total }条记录;
			</div>
		<!-- 分页条 -->
			<div class="col-md-6">
			<nav aria-label="Page navigation">
  <ul class="pagination pagination-lg">
  	<li>
  	<a href="${APP_PATH }/emps?pn=1">首页</a>
  	</li>
  	<c:if test="${pageInfo.pageNum==1}">
  		<li class="disabled">
      <a href="#" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
  	</c:if>
  	<c:if test="${pageInfo.pageNum!=1}">
  	<li>
      <a href="${APP_PATH }/emps?pn=${pageInfo.pageNum-1}" aria-label="Previous">
        <span aria-hidden="true">&laquo;</span>
      </a>
    </li>
  	</c:if>
    <c:forEach items="${pageInfo.navigatepageNums }"	var="page_Number">
	<c:if test="${page_Number==pageInfo.pageNum }">
	 <li class="active"><a href="#">${page_Number}</a></li> 
	 </c:if>
	 <c:if test="${page_Number!=pageInfo.pageNum }">
	 <li><a href="${APP_PATH }/emps?pn=${page_Number}">${page_Number}</a></li> 
	 </c:if>
   	
    </c:forEach>
    
    <c:if test="${pageInfo.pageNum==pageInfo.pages}">
    	<li class="disabled">
      <a href="#" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    </c:if>
    <c:if test="${pageInfo.pageNum!=pageInfo.pages}">
    	<li>
      <a href="${APP_PATH }/emps?pn=${pageInfo.pageNum+1}" aria-label="Next">
        <span aria-hidden="true">&raquo;</span>
      </a>
    </li>
    </c:if>
    <li>
  	<a href="${APP_PATH }/emps?pn=${pageInfo.pages}">末页</a>
  	</li>
  </ul>
</nav>
</div>
		</div>
	</div>

</body>
</html>