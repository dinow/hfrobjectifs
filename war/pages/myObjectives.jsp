<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.google.appengine.api.users.*" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>
<%@taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<html>
<head>
	<title>Objectifs@HFR</title>
	<link rel="stylesheet" type="text/css" href="../css/style.css" />
	<script type="text/javascript" src="../js/jquery-1.10.2.min.js"></script>
	<script type="text/javascript" src="../js/hfrobjectifs.js"></script>
</head>
<body>
	
	<%@ include file="header.jsp"%>
	<%
		if (userService.getCurrentUser() != null) {
		%>
	
	<div class="createNew smallText dnoLink" onclick="toggleDiv('newObjective');">Nouvel objectif</div>
	
	<div id="conteneur" class="mainPage" onmouseover="displayDiv('preferenceDiv',false);">
		<div id="newObjective" class="newObjective">
			<form action="/save_objectif.do" method="post">
				<table class="noheadTable">
					<tr><td>Intitulé</td><td><input type="text" name="name"></input></td></tr>
					<tr><td>Evenement</td><td><select name="evenement">${hfr:getEventsDropDown()}</select></td></tr>
					<tr><td>Annee</td><td><select name="annee">${hfr:getNextYearsForDropDown(5) }</select></td></tr>
					<tr><td>Temps Prévu</td><td><input type="text" name="tempsPrevu"></input></td></tr>
					<tr><td>Poids prévu</td><td><input type="text" name="poids"></input></td></tr>
					<tr><td>Privé</td><td><input type="checkbox" name="objectifPrive"></input></td></tr>
					<tr><td><input type="submit" value="Sauver" /></td><td>&nbsp;</td></tr>
				</table>
			</form>
		</div>
		<div class="existingObjectives">
		
			<table class="noheadTable">
				<tr><th>Objectifs existants</th></tr>
				<c:forEach items="${objectives.my}" var="objective">
					<c:set var="objective" value="${objective}" />
					<tr><td>
						<tags:objectif objectif="${objective}"  />
						<form action="/delete_objectif.do" method="post">
							<input type="submit" value="Supprimer" />
							<input type="hidden" value="${objective.id }" name="objectifId"/>
						</form>
					</td></tr>
				</c:forEach>		
			</table>
			<table class="noheadTable">
				<tr><th>Objectifs publics</th></tr>
				<c:forEach items="${objectives.their}" var="objective">
					<c:set var="objective" value="${objective}" />
					<tr><td>
						<tags:objectif objectif="${objective}"  />
					</td></tr>
				</c:forEach>		
			</table>
		
		</div>
	</div>
	<%
	}
	
	%>
</body>
</html>