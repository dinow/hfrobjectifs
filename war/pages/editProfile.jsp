<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.google.appengine.api.users.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
	<title>Objectifs@HFR</title>
	<link rel="stylesheet" type="text/css" href="../css/style.css" />
	<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="../js/hfrobjectifs.js"></script>
</head>
<body>
	
	<%@ include file="header.jsp"%>
	<div class="mainPage">
		<h3>Edition utilisateur</h3>
		<form action="/save_profile.do" method="post"> 
			Id Google : ${user.userID }<br/>
			Pseudo HFR: <input type="text" name="hfrUserName" value="${user.hfrUserName }"><br/>
			<input type="submit" value="Save" />
		</form>
	</div>
</body>
</html>