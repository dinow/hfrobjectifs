<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib uri="http://hfr.appsport.com/taglib/functions" prefix="hfr"%>

<%@ attribute name="objectif" required="true"
	type="be.dno.hfrobjectifs.entities.Objectif"%>
<%@ attribute name="editable" required="true"
	type="java.lang.Boolean"%>	
	
<tr class="${hfr:getObjectifClass(objectif) }">
<td>${objectif.annee }</td>
<td>${objectif.name }</td>
<td>${objectif.tempsPrevuStr }</td>
<td>${objectif.tempsRealiseStr }</td>
<td>${objectif.poidsPrevu }</td>
<td>${objectif.poidsRealise }</td>
<c:if test="${editable }">
	<td style="vertical-align: top;">
		<form action="/delete_objectif.do" method="post">
			<input type="submit" value="Supprimer" />
			<input type="hidden" value="${objectif.id }" name="objectifId"/>
		</form>
		<form action="/edit_objectif.do" method="post">
			<input type="submit" value="Editer" />
			<input type="hidden" value="${objectif.id }" name="objectifId"/>
		</form>
	</td>
</c:if>
</tr>