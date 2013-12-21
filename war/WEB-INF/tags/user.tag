<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>
<%@ taglib prefix="tags" tagdir="/WEB-INF/tags" %>
<%@ attribute name="user" required="true" type="be.dno.hfrobjectifs.entities.User"%>

<c:set var="objectifs" value="${hfr:getPublicObjectivesForUser(user) }"/>
<c:set var="participations" value="${hfr:getParticipationsForUser(user) }"/>

<table border="1">
	<tr>
		<th>${user.hfrUserName }</th>
	</tr>
	<tr><td>
		<table>
			<tr><th>Evenements</th></tr>
			<tr><td>
				<c:forEach var="participation" items="${participations }">
					<tags:evenement event="${participation}"  />
				</c:forEach>
			</td></tr>
		</table>
	</td></tr>
	<tr><td>
		<table>
			<tr><th>Objectifs</th></tr>
			<tr><td>
				<c:forEach var="objectif" items="${objectifs }">
					<tags:objectif objectif="${objectif}"  />
				</c:forEach>
			</td></tr>
		</table>
	</td></tr>
</table>