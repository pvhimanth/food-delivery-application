<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodapp.model.User" %>
<%
    // Fetching the user object from the session
    User user = (User) session.getAttribute("user");
    if (user == null) {
        // If no user is found in session, redirect to the login page
        response.sendRedirect("LoginPage.html");
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>User Profile</title>
    <link rel="stylesheet" type="text/css" href="ProfileStyle.css">
</head>
<body>
    <div class="container">
        <h1>Welcome, <%= user.getUsername() %>!</h1>
        <div class="profile-details">
            <p><strong>Username:</strong> <%= user.getUsername() %></p>
            <p><strong>Email:</strong> <%= user.getEmail() %></p>
            <p><strong>Address:</strong> <%= user.getAddress() %></p>
        </div>
        <div class="logout-container">
            <form action="LogoutServlet" method="get">
                <button type="submit" class="logout-button">Logout</button>
            </form>
        </div>
    </div>
</body>
</html>
