<%@ tag language="java" body-content="scriptless"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%@ attribute name="objectif" required="true" type="be.dno.hfrobjectifs.entities.Objectif"%>
<table border="1">
	<tr>
		<th colspan="2">${objectif.name }</th>
	</tr>
	<tr>
		<td>Année</td><td>${objectif.annee }</td>
	</tr>
	<tr>
		<td>Temps prévu</td><td>${objectif.tempsPrevu }</td>
	</tr>
	<tr>
		<td>Temps réalisé</td><td>${objectif.tempsRealise }</td>
	</tr>
	<tr>
		<td>Poids</td><td>${objectif.poids }</td>
	</tr>
	<tr>
		<td>Résultat</td><td>${objectif.result }</td>
	</tr>
	<tr>
		<td>Lien vers activité</td><td>${objectif.lienActivite }</td>
	</tr>
	<tr>
		<td>Lien vers CR</td><td>${objectif.lienHFR }</td>
	</tr>
	<tr>
		<td>Confidentiel</td><td>${objectif.objectifPrive }</td>
	</tr>
	

</table>