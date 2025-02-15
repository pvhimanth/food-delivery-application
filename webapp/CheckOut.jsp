<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Checkout</title>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;500;600&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="CheckOutStyle.css">
</head>
<body>
    <div class="checkout-container">
        <h1 class="checkout-title">Checkout</h1>
        <form action="CheckOutServlet" method="post" class="checkout-form">
            <div class="form-section">
                <h2 class="section-title">Personal Information</h2>
                <div class="input-group">
                    <div class="form-field">
                        <label for="firstname">First Name</label>
                        <input type="text" id="firstname" name="firstname" required>
                    </div>
                    <div class="form-field">
                        <label for="lastname">Last Name</label>
                        <input type="text" id="lastname" name="lastname" required>
                    </div>
                </div>
                <div class="form-field">
                    <label for="phone">Phone</label>
                    <input type="tel" id="phone" name="phone" required pattern="\d{10}" title="Please enter a valid 10-digit phone number">
                </div>
                <div class="form-field">
                    <label for="email">Email</label>
                    <input type="email" id="email" name="email" required>
                </div>
            </div>

            <div class="form-section">
                <h2 class="section-title">Delivery Address</h2>
                <div class="form-field">
                    <label for="address">Area/Street/Floor</label>
                    <input type="text" id="address" name="address" required>
                </div>
                <div class="input-group">
                    <div class="form-field">
                        <label for="city">City</label>
                        <input type="text" id="city" name="city" required>
                    </div>
                    <div class="form-field">
                        <label for="state">State</label>
                        <input type="text" id="state" name="state" required>
                    </div>
                </div>
                <div class="form-field">
                    <label for="zipcode">Zipcode</label>
                    <input type="text" id="zipcode" name="zipcode" required pattern="\d{6}" title="Please enter a valid 6-digit Zipcode">
                </div>
            </div>

            <div class="form-section">
                <h2 class="section-title">Payment</h2>
                <div class="form-field">
                    <label for="paymentMode">Payment Mode</label>
                    <select id="paymentMode" name="paymentMode" required>
                        <option value="">Select payment mode</option>
                        <option value="Credit">Credit Card</option>
                        <option value="Debit">Debit Card</option>
                        <option value="COD">Pay On Delivery</option>
                        <option value="Rupay">Rupay</option>
                    </select>
                </div>
            </div>

            <div class="logo-section">
                <img src="delivery-bike.png" alt="Food Delivery Logo" class="logo">
            </div>

            <button type="submit" class="submit-btn">Place Order</button>
        </form>
    </div>
</body>
</html>
