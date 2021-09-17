<html>
<!-- We are adding "form" taglib for support for SPRING MVC FORM TAG so we can using model,data binding...-->
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!-- we are adding now SPRING SECURITY TAG LIB, WHICH WE ADDED IN OUR .POM FILE,
SO WE CAN SHOW USERNAME AND ROLES, WHEN WE ARE LOGGED IN SYSTEM. -->
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>


<!-- This is page will be shown for this mapping showHome(/) from controller class 
when we are logged in-->

<head>
	<title>BM Company Home Page</title>
</head>

<body>
	<h2>BM Company Home Page</h2>
	<hr>
	
	<p>
	Welcome to the BM company home page!
	</p>
	
	<hr>
	
	<!-- Here we wil show USERNAME AND ROLES with SPRING SECURITY TAG LIB -->
	<p>
		User: <security:authentication property="principal.username" />
		<br><br>
		Role(s): <security:authentication property="principal.authorities" />
		<br><br>
		
		<!-- Because of loadUserByUsername method in the class UserServiceImple from interface
		UserDetailsService.We can here print out first name, last name and email.
		Because when we log in the system, we have automatically loaded all information about that user,
		thanks to this method. -->
		First name: ${user.firstName}, Last name: ${user.lastName}, Email: ${user.email}
	</p>
	
	
	<!-- NOW WE WILL USE ALSO SECURITY TAG, BECAUSE WE WANT TO SHOW ONLY THIS LINK DOWN FOR
	USERS WITH ROLE MANAGER, BECAUSE OF THAT WE ARE USING access="hasRole('MANAGER')" -->
	<security:authorize access="hasRole('MANAGER')">
		
		<p>
		
		<!-- WE ARE NOW COLLING THIS REQURST MAPING FROM DEMO CONTROLLER CLASS, WHICH CALLING
		THIS .JSP PAGE leaders.jsp -->
			<a href="${pageContext.request.contextPath}/leaders">Leadership Meeting</a>
			(Only for Manager)
		</p>

	</security:authorize>	
	
	
	<!-- we are doing the same for the admin role -->
	<security:authorize access="hasRole('ADMIN')">  
		
		<p>
			<a href="${pageContext.request.contextPath}/systems">IT Systems Meeting</a>
			(Only for Admin)
		</p>
	
	</security:authorize>
	
	<hr>
	
	
	 <!-- NOW WE ARE ADDING LOGOUT BUTTON.LIKE BEFORE BY DEFAULT WHEN APP ADDING ERROR PARAMETER
	 TO THE LINK, SO WE CAN TAKE THAT PARAMETER AND CHECK IT OUT WITH IF STATEMENT.
	 THE SAME APPROACH WE ARE USING HERE AND WE CAN USE THIS PARAMETER logout to show some message:
	 You have been logged out. wE ARE USING post method because we sending data to server,
	 because we want to logout the user from the system and we also removing HTTP SESSION,COOKIES,ITD... -->
	<form:form action="${pageContext.request.contextPath}/logout" method="POST">
	
		<input type="submit" value="Logout" />
	
	</form:form>
	
</body>

</html>









