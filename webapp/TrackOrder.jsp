<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodapp.model.Orders" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Track Your Order</title>
    <link rel="stylesheet" href="TrackOrderStyle.css">
</head>
<body>
    <% Orders order = (Orders) request.getAttribute("order"); %>
    <div class="container">
        <h1>Track Your Order</h1>
        <div class="order-tracking">
            <div class="order-info">
                <!-- <h2>Order #<%= order.getOrderId() %></h2> -->
                <p><strong>Status:</strong> <%= order.getStatus() %></p>
                <p><strong>Total Amount:</strong> Rs<%= String.format("%.2f", order.getTotalAmount()) %></p>
            </div>
            <div class="tracking-progress">
                <div class="progress-step <%= order.getStatus().equalsIgnoreCase("Pending") || order.getStatus().equalsIgnoreCase("Confirmed") || order.getStatus().equalsIgnoreCase("Preparing") || order.getStatus().equalsIgnoreCase("Out for Delivery") || order.getStatus().equalsIgnoreCase("Delivered") ? "active" : "" %>">
                    <div class="step-icon">📋</div>
                    <span>Order Placed</span>
                </div>
                <div class="progress-step <%= order.getStatus().equalsIgnoreCase("Confirmed") || order.getStatus().equalsIgnoreCase("Preparing") || order.getStatus().equalsIgnoreCase("Out for Delivery") || order.getStatus().equalsIgnoreCase("Delivered") ? "active" : "" %>">
                    <div class="step-icon">✅</div>
                    <span>Confirmed</span>
                </div>
                <div class="progress-step <%= order.getStatus().equalsIgnoreCase("Preparing") || order.getStatus().equalsIgnoreCase("Out for Delivery") || order.getStatus().equalsIgnoreCase("Delivered") ? "active" : "" %>">
                    <div class="step-icon">👨‍🍳</div>
                    <span>Preparing</span>
                </div>
                <div class="progress-step <%= order.getStatus().equalsIgnoreCase("Out for Delivery") || order.getStatus().equalsIgnoreCase("Delivered") ? "active" : "" %>">
                    <div class="step-icon">🚚</div>
                    <span>Out for Delivery</span>
                </div>
                <div class="progress-step <%= order.getStatus().equalsIgnoreCase("Delivered") ? "active" : "" %>">
                    <div class="step-icon">🏠</div>
                    <span>Delivered</span>
                </div>
            </div>
        </div>
        <a href="OrderServlet" class="back-button">Back to Orders</a>
    </div>
</body>
</html>