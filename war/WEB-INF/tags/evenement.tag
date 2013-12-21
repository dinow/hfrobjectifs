<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>
<%@ attribute name="event" required="true" type="be.dno.hfrobjectifs.entities.Evenement"%>

<c:set var="participants" value="${hfr:getParticipants(event)}"/>

<table border="1">
	<tr>
		<th colspan="4">${event.name } (${event.dateEvenementStr })</th>
	</tr>
	<tr>
		<td>Type</td><td>${event.type }</td>
		<td>Distance</td><td>${event.distance }</td>
	</tr>	
	<tr>
		<td>Lieu</td><td colspan="3">${event.ville } / ${event.pays }</td>
	</tr>
	<c:if test="${!hfr:isEmptyString(event.urlEvenement) }">
		<tr>
			<td>URL Site officiel</td><td colspan="3"><a href="${event.urlEvenement }" target="_blank">${event.urlEvenement }</a></td>
		</tr>
	</c:if>
	<c:if test="${!hfr:isEmptyString(event.urlResultats) }">
		<tr>
			<td>URL Résultats</td><td colspan="3"><a href="${event.urlResultats }" target="_blank">${event.urlResultats }</a></td>
		</tr>
	</c:if>
	<tr>
		<td colspan="4">
			<table style="width: 100%;">
				<tr><th colspan="4">Participants</th></tr>
				<c:forEach var="participant" items="${participants }">
					<tr><td>${participant.hfrUserName }</td></tr>
				</c:forEach>
			</table>
		</td>
	</tr>
</table>