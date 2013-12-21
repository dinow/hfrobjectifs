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
	<c:set var="objectives" value="${hfr:getObjectivesForUser() }"/>
	<%
		if (userService.getCurrentUser() != null) {
		%>
	
	<div class="createNew smallText dnoLink" onclick="toggleDiv('newObjective');">Nouvel objectif</div>
	
	<div id="conteneur" class="mainPage" onmouseover="displayDiv('preferenceDiv',false);">
		<div id="newObjective" class="newObjective">
			<form action="/save_objectif.do" method="post">
				<c:if test="${objectif eq null }">
					<table class="noheadTable">
						<tr><td>Intitulé</td><td><input type="text" name="name"></input></td></tr>
						<tr><td>Evenement</td><td><select name="evenement">${hfr:getEventsDropDown(null)}</select></td></tr>
						<tr><td>Annee</td><td><select name="annee">${hfr:getNextYearsForDropDown(5,null) }</select></td></tr>
						<tr><td>Temps Prévu</td><td><input type="text" name="tempsPrevu"></input>HH:MM:SS</td></tr>
						<tr><td>Temps Réalisé</td><td><input type="text" name="tempsRealise"></input></td></tr>
						<tr><td>Poids prévu</td><td><input type="text" name="poidsPrevu"></input></td></tr>
						<tr><td>Poids Réalisé</td><td><input type="text" name="poidsRealise"></input></td></tr>
						<tr><td>Lien vers activité</td><td><input type="text" name="linkActivite"></input></td></tr>
						<tr><td>Lien vers CR</td><td><input type="text" name="linkCR"></input></td></tr>
						<tr><td>Privé</td><td><input type="checkbox" name="objectifPrive"></input></td></tr>
						<tr><td><input type="submit" value="Sauver" /></td><td>&nbsp;</td></tr>
					</table>
				</c:if>
				<c:if test="${objectif ne null }">
					<table class="noheadTable">
						<input type="hidden" value="${objectif.id }" name="objectifId"/>
						<tr><td>Intitulé</td><td><input type="text" name="name" value="${objectif.name }"></input></td></tr>
						<tr><td>Evenement</td><td><select name="evenement">${hfr:getEventsDropDown(objectif)}</select></td></tr>
						<tr><td>Annee</td><td><select name="annee">${hfr:getNextYearsForDropDown(5, objectif) }</select></td></tr>
						<tr><td>Temps Prévu</td><td><input type="text" name="tempsPrevu" value="${objectif.tempsPrevuStr }"></input></td></tr>
						<tr><td>Temps Réalisé</td><td><input type="text" name="tempsRealise" value="${objectif.tempsRealiseStr }"></input></td></tr>
						<tr><td>Poids prévu</td><td><input type="text" name="poidsPrevu" value="${objectif.poidsPrevu }"></input></td></tr>
						<tr><td>Poids Réalisé</td><td><input type="text" name="poidsRealise" value="${objectif.poidsRealise }"></input></td></tr>
						<tr><td>Lien vers activité</td><td><input type="text" name="linkActivite" value="${objectif.lienActivite }"></input></td></tr>
						<tr><td>Lien vers CR</td><td><input type="text" name="linkCR" value="${objectif.lienHFR }"></input></td></tr>
						<c:set var="checked" value=""/>
						<c:if test="${objectif.objectifPrive }">
							<c:set var="checked" value="checked"/>
						</c:if>
						<tr><td>Privé</td><td><input type="checkbox" name="objectifPrive" ${checked }></input></td></tr>
						<tr><td><input type="submit" value="Sauver" /></td><td>&nbsp;</td></tr>
					</table>
				</c:if>
			</form>
		</div>
		<div class="existingObjectives">
		
			<table class="noheadTable">
				<tr><th>Objectifs existants</th></tr>
				<c:forEach items="${objectives}" var="objective">
					<c:set var="objective" value="${objective}" />
					<tr><td>
						<tags:objectif objectif="${objective}"  />
						<form action="/delete_objectif.do" method="post">
							<input type="submit" value="Supprimer" />
							<input type="hidden" value="${objective.id }" name="objectifId"/>
						</form>
						<form action="/edit_objectif.do" method="post">
							<input type="submit" value="Editer" />
							<input type="hidden" value="${objective.id }" name="objectifId"/>
						</form>
					</td></tr>
				</c:forEach>		
			</table>
		</div>
	</div>
	<%
	}
	
	%>
	
	<c:if test="${objectif ne null }">
		<script>
			toggleDiv('newObjective');
		</script>
	</c:if>
	
</body>
</html>