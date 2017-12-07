<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<nav class="navbar navbar-default">
    <div class="container-fluid">
        <ul class="nav navbar-nav">
			<li class="${requestScope.departments}"><a href="Departments">Departments</a></li>
			<li class="${requestScope.employees}"><a href="Employees">Employees</a></li>
			<li class="${requestScope.group}"><a href="Group">Group</a></li>
			<li class="${requestScope.reports}"><a href="Reports">Reports</a></li>
			<li class="${requestScope.attendance}"><a href="Attendance">Attendance</a></li>        
		</ul>
        <ul class="nav navbar-nav navbar-right">
            <li><p class="navbar-text">Hello ${sessionScope.user}</p></li>
            <li><a href="Logout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
        </ul>
    </div>
</nav>