<%@ page contentType="text/html; charset=UTF-8"%>
<%@ page import="com.google.appengine.api.users.*"%>

<div class="userDiv">
	<%UserService userService = UserServiceFactory.getUserService();
		if (userService.getCurrentUser() != null) {
			String userName = userService.getCurrentUser().getNickname();
		%>
		<span class="dnoLink" onclick="displayDiv('preferenceDiv',true);"><%=userName %></span>
	<%
		}
		%>
</div>

<div id="preferenceDiv" class="preferenceDiv">
	<div class="arrow_box">
		<table class="noheadTable">
			<tr><td><a href="/editProfile.do" title="Preferences" class="dnoLink" onclick="displayDiv('preferenceDiv',false);">Preferences</a></td></tr>
			<tr><td><a href="<%= userService.createLogoutURL("/") %>" title="Deconnection" class="dnoLink" onclick="displayDiv('preferenceDiv',false);">Deconnection</a></td></tr>
		</table>
	</div>
</div>

<div class="headerDiv" onmouseover="displayDiv('preferenceDiv',false);">
	<%
	if (userService.getCurrentUser() != null) {
	%>
	<a href="/myObjectives.do" title="Objectifs" class="dnoLink">Objectifs</a>
	<a href="/myEvents.do" title="Evenements" class="dnoLink">Evenements</a>
	<a href="/showUsers.do" title="Utilisateurs" class="dnoLink">Utilisateurs</a>
		<%
		}
		%>
</div>