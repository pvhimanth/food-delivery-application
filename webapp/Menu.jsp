<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.foodapp.model.Menu" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Restaurant Menu</title>
    <link rel="stylesheet" href="MenuStyle.css">
</head>
<body>
    <header class="hero">
        <div class="hero-content">
            <h1>Restaurant Menu</h1>
            <p class="hero-subtitle">Discover our delicious offerings</p>
        </div>
    </header>
    
    <main class="container">
        <% ArrayList<Menu> menus = (ArrayList<Menu>) request.getAttribute("menus"); %>
        <% if (menus != null && !menus.isEmpty()) { %>
            <div class="menu-list">
                <% for (Menu m : menus) { %>
                <% if(m.isAvailable() !=false){ %>
                    <div class="menu-item">
                        <div class="menu-item-image">
                            <img src="<%= m.getImagePath() %>" alt="<%= m.getName() %>">
                        </div>
                        <div class="menu-item-content">
                            <h2><%= m.getName() %></h2>
                            <p class="description"><%= m.getDescription() %></p>
                            <div class="menu-item-footer">
                                <p class="price">₹<%= String.format("%.2f", m.getPrice()) %></p>
                                <form action="CartServlet" method="post">
                                    <input type="hidden" name="action" value="add">
                                    <input type="hidden" name="itemId" value="<%= m.getMenuId() %>">
                                    <input type="hidden" name="quantity" value="1">
                                    <button type="submit" class="add-to-cart">ADD</button>
                                </form>
                            </div>
                        </div>
                    </div>
                <% } %>
                <% } %>
            </div>
        <% } else { %>
            <div class="no-items">
                <img src="empty-plate.svg" alt="No items" class="empty-icon">
                <p>No menu items available for this restaurant.</p>
            </div>
        <% } %>
    </main>
</body>
</html>