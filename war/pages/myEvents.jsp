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
		<div class="createNew smallText dnoLink" onclick="toggleDiv('newEvent');">Nouvel evenement</div>
		<div id="conteneur" class="mainPage" onmouseover="displayDiv('preferenceDiv',false);">
		<div id="newEvent" class="newObjective">
			<form action="/save_event.do" method="post">
				<table class="noheadTable">
					<tr><td>Nom</td><td><input type="text" name="name"></input></td></tr>
					<tr><td>Date</td><td><input type="date" name="date"></td></tr>
					<tr><td>Pays</td><td><input type="text" name="pays"></input></td></tr>
					<tr><td>Ville</td><td><input type="text" name="city"></input></td></tr>
					<tr><td>Distance</td><td><input type="text" name="length"></input></td></tr>
					<tr><td>URL Site officiel</td><td><input type="text" name="officialUrl"></input></td></tr>
					<tr><td>URL Résultats</td><td><input type="text" name="resultUrl"></input></td></tr>
					<tr><td>Type</td><td><select name="type">${hfr:getEventsTypes() }</select></td></tr>
					<tr><td>Privé</td><td><input type="checkbox" name="evenementPrive"></input></td></tr>
					<tr><td><input type="submit" value="Sauver" /></td><td>&nbsp;</td></tr>
				</table>
			</form>
		</div>
		<div class="existingObjectives">
			<table class="noheadTable">
				<tr><th>Mes Evénements</th></tr>
				<c:forEach items="${events.my}" var="event">
					<c:set var="event" value="${event}" />
					<tr><td>
						<tags:evenement event="${event}"  />
						<form action="/delete_event.do" method="post">
							<input type="submit" value="Supprimer" />
							<input type="hidden" value="${event.id }" name="eventId"/>
						</form>
					</td></tr>
				</c:forEach>		
			</table>
			<table class="noheadTable">
				<tr><th>Autres Evénements</th></tr>
				<c:forEach items="${events.their}" var="event">
					<c:set var="event" value="${event}" />
					<tr><td>
						<tags:evenement event="${event}"  />
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