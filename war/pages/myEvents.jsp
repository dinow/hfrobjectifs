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
	
	<c:set var="events" value="${hfr:getEventsForUser() }"/>
	
	
	<c:if test="${message ne null }">
		<div class="popup">${message }</div>
	</c:if>
	
	<%
		if (userService.getCurrentUser() != null) {
		%>
		<div class="createNew smallText dnoLink" onclick="toggleDiv('newEvent');">Nouvel evenement</div>
		<div id="conteneur" class="mainPage" onmouseover="displayDiv('preferenceDiv',false);">
		<div id="newEvent" class="newObjective">
			<form action="/save_event.do" method="post">
				<c:if test="${inputEvent eq null }">
					<table class="noheadTable">
						<tr><td>Nom</td><td><input type="text" name="name"></input></td></tr>
						<tr><td>Date</td><td><input type="date" name="date"></td></tr>
						<tr><td>Pays</td><td><input type="text" name="pays"></input></td></tr>
						<tr><td>Ville</td><td><input type="text" name="city"></input></td></tr>
						<tr><td>Distance</td><td><input type="text" name="length"></input></td></tr>
						<tr><td>URL Site officiel</td><td><input type="text" name="officialUrl"></input></td></tr>
						<tr><td>URL Résultats</td><td><input type="text" name="resultUrl"></input></td></tr>
						<tr><td>Type</td><td><select name="type">${hfr:getEventsTypes(null) }</select></td></tr>
						<tr><td><input type="submit" value="Sauver" /></td><td>&nbsp;</td></tr>
					</table>
				</c:if>
				
				<c:if test="${inputEvent ne null }">
					<table class="noheadTable">
						<input type="hidden" value="${inputEvent.id }" name="eventId"/>
						<tr><td>Nom</td><td><input type="text" name="name" value="${inputEvent.name }"></input></td></tr>
						<tr><td>Date</td><td><input type="date" name="date" value="${inputEvent.dateEvenementStr }"></td></tr>
						<tr><td>Pays</td><td><input type="text" name="pays" value="${inputEvent.pays }"></input></td></tr>
						<tr><td>Ville</td><td><input type="text" name="city" value="${inputEvent.ville }"></input></td></tr>
						<tr><td>Distance</td><td><input type="text" name="length" value="${inputEvent.distance }"></input></td></tr>
						<tr><td>URL Site officiel</td><td><input type="text" name="officialUrl" value="${inputEvent.urlEvenement }"></input></td></tr>
						<tr><td>URL Résultats</td><td><input type="text" name="resultUrl" value="${inputEvent.urlResultats }"></input></td></tr>
						<tr><td>Type</td><td><select name="type">${hfr:getEventsTypes(inputEvent) }</select></td></tr>
						<tr><td><input type="submit" value="Sauver" /></td><td>&nbsp;</td></tr>
					</table>
				</c:if>
			</form>
		</div>
		<div class="existingObjectives">
			<table class="noheadTable">
				<tr><th>Mes Evénements</th></tr>
				<c:forEach items="${events.my}" var="event">
					<c:set var="event" value="${event}" />
					<tr><td>
						<tags:evenement event="${event}"  />
						<c:if test="${!event.usedBySomeoneElse }">
							<form action="/delete_event.do" method="post">
								<input type="submit" value="Supprimer" />
								<input type="hidden" value="${event.id }" name="eventId"/>
							</form>
						</c:if>
						<form action="/edit_event.do" method="post">
							<input type="submit" value="Editer" />
							<input type="hidden" value="${event.id }" name="eventId"/>
						</form>
						<c:if test="${!hfr:isParticipating(event) }">
							<form action="/participate_event.do" method="post">
								<input type="submit" value="Participer" />
								<input type="hidden" value="${event.id }" name="eventId"/>
							</form>
						</c:if>
						<c:if test="${hfr:isParticipating(event) }">
							<form action="/removeParticipation_event.do" method="post">
								<input type="submit" value="Ne plus participer" />
								<input type="hidden" value="${event.id }" name="eventId"/>
							</form>
						</c:if>
					</td></tr>
				</c:forEach>		
			</table>
			<table class="noheadTable">
				<tr><th>Autres Evénements</th></tr>
				<c:forEach items="${events.their}" var="event">
					<c:set var="event" value="${event}" />
					<tr><td>
						<tags:evenement event="${event}"  />
						<c:if test="${!hfr:isParticipating(event) }">
							<form action="/participate_event.do" method="post">
								<input type="submit" value="Participer" />
								<input type="hidden" value="${event.id }" name="eventId"/>
							</form>
						</c:if>
						<c:if test="${hfr:isParticipating(event) }">
							<form action="/removeParticipation_event.do" method="post">
								<input type="submit" value="Ne plus participer" />
								<input type="hidden" value="${event.id }" name="eventId"/>
							</form>
						</c:if>
					</td></tr>
				</c:forEach>		
			</table>
		
		</div>
	</div>
	
	<%
		}
	%>
	<c:if test="${inputEvent ne null }">
		<script>
			toggleDiv('newEvent');
		</script>
	</c:if>
	
</body>
</html>