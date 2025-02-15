<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.foodapp.cart.Cart"%>
<%@ page import="com.foodapp.cart.CartItem"%>
<%@ page import="com.foodapp.*"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Your Cart</title>
    <link rel="stylesheet" href="CartStyle.css">
</head>
<body>
    <div class="container">
        <h1>Your Cart</h1>
        <% 
            Cart cart = (Cart) session.getAttribute("cart");
            if (cart == null || cart.getItems().isEmpty()) { 
        %>
        <div class="empty-cart">
            <p>Your cart is empty</p>
            <a href="Home.jsp" class="btn btn-primary">Go to Home</a>
        </div>
        <% 
            } else { 
                int restaurantId = 0;
                if (!cart.getItems().isEmpty()) {
                    restaurantId = cart.getItems().values().iterator().next().getRestaurantId();
                }
                double totalAmount = 0;
        %>
        <div class="cart-items">
            <% 
                for (Map.Entry<Integer, CartItem> entry : cart.getItems().entrySet()) {
                    CartItem item = entry.getValue();
                    totalAmount += item.getPrice() * item.getQuantity();
            %>
            <div class="cart-item">
                <div class="item-details">
                    <h3><%= item.getName() %></h3>
                    <p class="price">₹<%= item.getPrice() %></p>
                </div>
                <div class="item-actions">
                    <div class="quantity-controls">
                        <form action="CartServlet" method="post" class="quantity-form">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                            <button type="submit" name="quantity" value="<%= item.getQuantity() - 1 %>" class="btn btn-quantity">-</button>
                        </form>
                        <span class="quantity"><%= item.getQuantity() %></span>
                        <form action="CartServlet" method="post" class="quantity-form">
                            <input type="hidden" name="action" value="update">
                            <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                            <button type="submit" name="quantity" value="<%= item.getQuantity() + 1 %>" class="btn btn-quantity">+</button>
                        </form>
                    </div>
                    <form action="CartServlet" method="post" class="remove-form">
                        <input type="hidden" name="action" value="remove">
                        <input type="hidden" name="itemId" value="<%= item.getItemId() %>">
                        <button type="submit" class="btn btn-remove">Remove</button>
                    </form>
                </div>
                <p class="item-total">₹<%= item.getPrice() * item.getQuantity() %></p>
            </div>
            <% 
                } 
            %>
        </div>
        <div class="cart-summary">
            <h2>Order Summary</h2>
            <p class="total-amount">Total Amount: <span>₹<%= totalAmount %></span></p>
            <div class="cart-actions">
                <a href="MenuServlet?restaurantId=<%= restaurantId %>" class="btn btn-secondary">Add More Items</a>
                <a href="CheckOut.jsp" class="btn btn-primary">Proceed to Checkout</a>
            </div>
        </div>
        <% 
            } 
        %>
    </div>
</body>
</html>