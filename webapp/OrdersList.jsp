<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.Collections" %>
<%@ page import="java.util.Comparator" %>
<%@ page import="com.foodapp.model.Orders" %>
<%@ page import="com.foodapp.model.Restaurant" %>
<%@ page import="com.foodapp.model.Orderitems" %>
<%@ page import="com.foodapp.model.Menu" %>
<%@ page import="com.foodapp.daoimpl.OrderitemsDAOImpl" %>
<%@ page import="com.foodapp.daoimpl.MenuDAOImpl" %>
<%
    // Fetched the logged-in user's orders and restaurant list
    ArrayList<Orders> userOrders = (ArrayList<Orders>) request.getAttribute("userOrders");
    ArrayList<Restaurant> restaurantList = (ArrayList<Restaurant>) request.getAttribute("restaurantList");

    // Initialize DAOs
    OrderitemsDAOImpl orderItemsDAO = new OrderitemsDAOImpl();
    MenuDAOImpl menuDAO = new MenuDAOImpl();

    // Sorted orders by orderId in descending order (assuming orderId is chronological)
    if (userOrders != null && !userOrders.isEmpty()) {
        Collections.sort(userOrders, new Comparator<Orders>() {
            @Override
            public int compare(Orders o1, Orders o2) {
                return Integer.compare(o2.getOrderId(), o1.getOrderId());
            }
        });
    }
%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Your Orders</title>
    <link rel="stylesheet" href="OrdersListStyle.css">
</head>
<body>
    <div class="container">
        <div class="header-actions">
            <a href="Home.jsp" class="home-button">Return to Home</a>
        </div>
        <h1>Your Orders</h1>
        <div class="orders-container">
            <% 
                if (userOrders == null || userOrders.isEmpty()) { 
            %>
                <p class="no-orders">No orders found for your account.</p>
            <% 
                } else { 
                    int totalOrders = userOrders.size();
                    for (int i = 0; i < totalOrders; i++) {
                        Orders order = userOrders.get(i);
                        int orderNumber = totalOrders - i; // Calculate descending order number
                        
                        // Finding the matching restaurant by ID
                        Restaurant matchedRestaurant = null;
                        for (Restaurant restaurant : restaurantList) {
                            if (restaurant.getRestaurantId() == order.getRestaurantId()) {
                                matchedRestaurant = restaurant;
                                break;
                            }
                        }

                        // Fetched order items for this order
                        ArrayList<Orderitems> orderItems = orderItemsDAO.fetchByOrderId(order.getOrderId());
            %>
                <div class="order-card">
                    <div class="order-header">
                        <h2>Order #<%= orderNumber %></h2>
                        <span class="status <%= order.getStatus().toLowerCase() %>"><%= order.getStatus() %></span>
                    </div>
                    <div class="order-body">
                        <div class="restaurant-info">
                            <h3><%= matchedRestaurant != null ? matchedRestaurant.getName() : "Unknown Restaurant" %></h3>
                            <p><%= matchedRestaurant != null ? matchedRestaurant.getAddress() : "Unknown Address" %></p>
                        </div>
                        <div class="order-items">
                            <h4>Ordered Items:</h4>
                            <ul>
                                <% for (Orderitems item : orderItems) {
                                    Menu menuItem = menuDAO.fetchOne(item.getMenuId());
                                    if (menuItem != null) {
                                %>
                                    <li class="order-item">
                                        <img src="<%= menuItem.getImagePath() %>" alt="<%= menuItem.getName() %>" class="item-image">
                                        <div class="item-details">
                                            <span class="item-name"><%= menuItem.getName() %></span>
                                            <span class="item-quantity">Quantity: <%= item.getQuantity() %></span>
                                            <span class="item-price">Price: Rs<%= String.format("%.2f", menuItem.getPrice()) %></span>
                                        </div>
                                    </li>
                                <% 
                                    }
                                } 
                                %>
                            </ul>
                        </div>
                        <div class="order-details">
                            <p><strong>Total Amount:</strong> Rs<%= String.format("%.2f", order.getTotalAmount()) %></p>
                            <p><strong>Payment Mode:</strong> <%= order.getPaymentMode() %></p>
                        </div>
                        <div class="order-actions">
                            <a href="TrackOrder?orderId=<%= order.getOrderId() %>" class="track-button">Track Order</a>
                        </div>
                    </div>
                </div>
            <% 
                    } 
                } 
            %>
        </div>
    </div>
</body>
</html>