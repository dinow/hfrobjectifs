<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.google.appengine.api.users.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
	<title>Objectifs@HFR</title>
	<link rel="stylesheet" type="text/css" href="../css/style.css" />
	<script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.2/jquery.min.js" ></script>
	<script type="text/javascript" src="../js/hfrobjectifs.js"></script>
</head>
<body>
	
	<%@ include file="header.jsp"%>
	<c:set var="users" value="${hfr:getUsers() }"/>
	<div class="userList">	
		<table class="noheadTable">
			<tr><th>Utilisateurs</th></tr>
			<c:forEach items="${users}" var="user">
				<tr><td><tags:user user="${user}"  /></td></tr>
			</c:forEach>		
		</table>
	</div>
</body>
</html>