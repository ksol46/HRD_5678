<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="DTO.Rank" %>
<%
request.setCharacterEncoding("utf-8");
ArrayList<Rank> r_list = new ArrayList<Rank>();
r_list = (ArrayList<Rank>) request.getAttribute("r_list");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="style.css">
</head>
<body>
<%@ include file="topmenu.jsp" %>
	<section>
		<div class="title">후보자등수</div>
		<div class="wrapper">
		<table>
		<tr>
		<th>후보번호</th>
		<th>성명</th>
		<th>총투표건수</th>
		</tr>
		<% for (Rank r : r_list) {%>
		<tr>
		<td><%=r.getM_no() %></td>
		<td><%=r.getM_name() %></td>
		<td><%=r.getTotal() %></td>
		</tr>
		<%
		}
		%>
		</table>
		</div>
	</section>
<%@ include file="footer.jsp" %>
</body>
</html>