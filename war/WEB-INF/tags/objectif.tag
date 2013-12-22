<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>

<%@ attribute name="objectif" required="true" type="be.dno.hfrobjectifs.entities.Objectif"%>
<table border="1" style="margin-bottom: 5px;">
	<tr>
		<th colspan="4" class="${hfr:getObjectifClass(objectif) }">${objectif.name } (${objectif.annee })</th>
	</tr>
	<c:if test="${!hfr:isEmptyString(objectif.tempsPrevu) }">
		<tr>
			<td>Temps pr�vu</td><td>${objectif.tempsPrevuStr }</td>
			<td>Temps r�alis�</td><td>${objectif.tempsRealiseStr }</td>
			
		</tr>
	</c:if>
	<c:if test="${!hfr:isEmptyString(objectif.poidsPrevu) }">
		<tr>
			<td>Poids Pr�vu</td><td>${objectif.poidsPrevu }</td>
			<td>Poids Atteind</td><td>${objectif.poidsRealise }</td>
		</tr>
	</c:if>
	<c:if test="${!hfr:isEmptyString(objectif.evenementId) && objectif.evenementId != -1 }">
		<tr>
			<td>Evenement</td>
			<td colspan="3">
				<c:set var="event" value="${hfr:getEventFromObjectif(objectif) }"/>
				<a href="#" title="todo">${event.name }</a>
			</td>
		</tr>
	</c:if>
	<c:if test="${!hfr:isEmptyString(objectif.lienActivite) }">
		<tr>
			<td>Lien vers activit�</td><td colspan="3"><a href="${objectif.lienActivite }" target="_blank">${objectif.lienActivite }</a></td>
		</tr>
	</c:if>
	<c:if test="${!hfr:isEmptyString(objectif.lienHFR) }">
		<tr>
			<td>Lien vers CR</td><td colspan="3"><a href="${objectif.lienHFR }" target="_blank">${objectif.lienHFR }</a></td>
		</tr>
	</c:if>
</table>