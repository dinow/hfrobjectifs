<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="event" required="true" type="be.dno.hfrobjectifs.entities.Evenement"%>
<table border="1">
	<tr>
		<th colspan="2">${event.name }</th>
	</tr>
	<tr>
		<td>Date</td><td>${event.dateEvenement }</td>
	</tr>
	<tr>
		<td>Distance</td><td>${event.distance }</td>
	</tr>
	<tr>
		<td>Type</td><td>${event.type }</td>
	</tr>
	<tr>
		<td>Pays</td><td>${event.pays }</td>
	</tr>
	<tr>
		<td>Ville</td><td>${event.ville }</td>
	</tr>
	<tr>
		<td>URL Site officiel</td><td>${event.urlEvenement }</td>
	</tr>
	<tr>
		<td>URL Résultats</td><td>${event.urlResultats }</td>
	</tr>
	<tr>
		<td>Confidentiel</td><td>${event.evenementPrive }</td>
	</tr>
</table>