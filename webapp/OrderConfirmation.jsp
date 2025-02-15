<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.foodapp.model.Orders" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Order Confirmation</title>
    <link rel="stylesheet" href="OrderConfirmationStyle.css">
    <script src="OrderConfirmationJs.js" defer></script>
</head>
<body>
    <% 
    Orders order = (Orders) request.getAttribute("order");
    if (order != null) {
    %>
    <div class="container">
        <div class="confirmation-card">
            <div class="content-wrapper">
                <h1>Thank You For Your Order!</h1>
                <p>Your order #<%= order.getOrderId() %> has been placed successfully.</p>
                
                <div class="order-details">
                    <div class="detail-item">
                        <span class="detail-label">Total Amount:</span>
                        <span class="detail-value">Rs<%= String.format("%.2f", order.getTotalAmount()) %></span>
                    </div>
                    <div class="detail-item">
                        <span class="detail-label">Payment Mode:</span>
                        <span class="detail-value"><%= order.getPaymentMode() %></span>
                    </div>
                </div>
                
                <div class="progress-container">
                    <div class="road"></div>
                    <div class="scooter-container">
                        <img src="delivery-bike.png" alt="Delivery Scooter" class="scooter-icon" onerror="this.onerror=null; this.src='data:image/svg+xml;charset=UTF-8,<svg xmlns=\'http://www.w3.org/2000/svg\' width=\'80\' height=\'80\' viewBox=\'0 0 24 24\' fill=\'none\' stroke=\'%23333333\' stroke-width=\'2\' stroke-linecap=\'round\' stroke-linejoin=\'round\'><circle cx=\'12\' cy=\'12\' r=\'10\'/><path d=\'M8 14s1.5 2 4 2 4-2 4-2\'/><line x1=\'9\' y1=\'9\' x2=\'9.01\' y2=\'9\'/><line x1=\'15\' y1=\'9\' x2=\'15.01\' y2=\'9\'/></svg>';">
                    </div>
                </div>

                <div class="button-group">
                    <a href="Home.jsp" class="home-button">Return to Home</a>
                    <a href="TrackOrder?orderId=<%= order.getOrderId() %>" class="track-button">Track Order</a>
                    <a href="OrderServlet" class="view-all-orders-button">View All Orders</a>
                </div>
            </div>
        </div>
    </div>
    <% 
    } else {
    %>
        <div class="confirmation-card">
            <h1>Error</h1>
            <p>Order details not found. Please try again.</p>
            <ahref="Home.jsp" class="home-button">Return to Home</a>
        </div>
    <% 
    }
    %>
</body>
</html>