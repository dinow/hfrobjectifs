<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags"%>
<%@ attribute name="user" required="true"
	type="be.dno.hfrobjectifs.entities.User"%>

<c:set var="objectifs" value="${hfr:getPublicObjectivesForUser(user) }" />
<c:set var="participations"
	value="${hfr:getParticipationsForUser(user) }" />


<tr>
	<td>${user.hfrUserName }</td>
	<td>
		<table border="1">
			<c:forEach var="participation" items="${participations }">
				<tags:evenement event="${participation}" />
			</c:forEach>
		</table>
	</td>
	<td>
		<table border="1">
			<c:forEach var="objectif" items="${objectifs }">
				<tags:objectif objectif="${objectif}" editable="${false }" />
			</c:forEach>
		</table>
	</td>
</tr>
