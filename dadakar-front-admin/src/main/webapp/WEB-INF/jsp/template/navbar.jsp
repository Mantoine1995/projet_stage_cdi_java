<nav class="navbar navbar-fixed-top navbar-inverse">
	<div class="container-fluid">
		<div class="navbar-header">
			<a class="navbar-brand" href="/">DaDaKar</a>
		</div>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="nav navbar-nav">
<!-- 				<li><a href="/">Home</a></li> -->
				<li class=<%=request.getParameter("user-active")%>>
					<a href="/user/index">Voir la liste des utilisateurs</a>
				</li>
				<li class=<%=request.getParameter("complaint-active")%>>
					<a href="/plainte/index">Voir la liste des plaintes</a>
				</li>
				<li class=<%=request.getParameter("price-active")%>>
					<a href="/price/index">Sp�cifier les prix et les taux de marge</a>
				</li>
				<% if(request.getSession().getAttribute("role") == "SUPERUSER") { %>
				<li class="<%=request.getParameter("admin-active")%> <%=request.getParameter("admin-enable")%>">
					<a href="/admin/index">G�rer les administrateurs</a>
				</li>
				<% } %>
			</ul>
			<ul class="nav navbar-nav navbar-right">
				<li class=<%=request.getParameter("password-active")%>>
					<a href="/admin/passwordchange/<%= request.getAttribute("javax.servlet.forward.request_uri").toString().replaceAll("/", "-") %>/<%= request.getSession().getAttribute("uid")%>">
						Changer de mot de passe</a>
				</li>
				<li>
					<form action="/logout" method="post" class="form-inline">
						<input type="submit" class="btn btn-sm btn-outline-secondary btn-signout" value="Se d�connecter" />
						<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
					</form>
				</li>
			</ul>
		</div>
	</div>
</nav>
