<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="com.foodapp.model.User" %>
<%@ page import="com.foodapp.model.Restaurant" %>
<%@ page import="com.foodapp.daoimpl.RestaurantDAOImpl" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>FoodExpress - Restaurants</title>
    <link rel="stylesheet" href="RestaurantListStyle.css">
</head>
<body>
    <% 
        String username = "Guest";
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            username = user.getUsername();
        }
        
        String searchQuery = request.getParameter("query");
        
        ArrayList<Restaurant> resList = (ArrayList<Restaurant>) session.getAttribute("resList");
        if (resList == null) {
            RestaurantDAOImpl rdao = new RestaurantDAOImpl();
            resList = rdao.fetchAll();
            session.setAttribute("resList", resList);
        }
        
        ArrayList<Restaurant> filteredList = new ArrayList<>();
        if (searchQuery != null && !searchQuery.trim().isEmpty()) {
            for (Restaurant r : resList) {
                if (r.getName().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    r.getCuisineType().toLowerCase().contains(searchQuery.toLowerCase()) ||
                    r.getAddress().toLowerCase().contains(searchQuery.toLowerCase())) {
                    filteredList.add(r);
                }
            }
        } else {
            filteredList = new ArrayList<>(resList); 
        }
    %>


    <nav class="navbar">
        <div class="nav-container">
            <div class="nav-left">
                <a href="#" class="logo">
                    <img src="delivery-bike.png" alt="FoodExpress Logo" class="app-logo">
                    <span>FoodExpress</span>
                </a>
                
                <div class="location-selector">
                    <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/map-pin.svg" alt="Location" class="icon">
                    <select id="location" class="location-select">
                        <option value="bengaluru">Bengaluru</option>
                        <option value="mumbai">Mumbai</option>
                        <option value="delhi">Delhi</option>
                        <option value="chennai">Chennai</option>
                    </select>
                </div>
            </div>

            <div class="nav-center">
                <form class="search-bar" action="Home.jsp" method="get">
                    <input type="text" name="query" placeholder="Search restaurants..." value="<%= searchQuery != null ? searchQuery : "" %>">
                    <button type="submit">
                        <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/search.svg" alt="Search" class="icon">
                    </button>
                </form>
            </div>
            
            <div class="nav-right">
                <div class="nav-links">
                    <a href="Offers.html" class="nav-link">
                        <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/gift.svg" alt="Offers" class="icon">
                        <span>Offers</span>
                    </a>
                    <a href="Cart.jsp" class="nav-link">
                        <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/shopping-cart.svg" alt="Cart" class="icon">
                        <span>Cart</span>
                    </a>
                    <a href="Help.html" class="nav-link">
                        <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/help-circle.svg" alt="Help" class="icon">
                        <span>Help</span>
                    </a>
                    <div class="profile-menu">
                        <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/user.svg" alt="Profile" class="icon">
                        <span class="username"><%= username %></span>
                        <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/chevron-down.svg" alt="Expand" class="icon-small">
                        <div class="dropdown-menu">
                            <a href="Profile.jsp" class="dropdown-item">
                                <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/user.svg" alt="Profile" class="icon-small">
                                Profile
                            </a>
                            <a href="OrderServlet" class="dropdown-item">
                                <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/list.svg" alt="Orders" class="icon-small">
                                Orders
                            </a>
                            <a href="LogoutServlet" class="dropdown-item">
                                <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/log-out.svg" alt="Logout" class="icon-small">
                                Logout
                            </a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </nav>

    <main>
        <section class="top-restaurants">
            <h2>Top Rated Restaurants</h2>
            <div class="top-restaurants-scroll">
                <% 
                for (Restaurant r : filteredList) {
                    if (r.getRatings() >= 4.7) {
                %>
                    <article class="restaurant-card top-restaurant-card">
                        <a href="MenuServlet?restaurantId=<%= r.getRestaurantId() %>">
                            <div class="card-image">
                                <img src="<%= r.getImagePath() %>" alt="<%= r.getName() %>">
                            </div>
                            <div class="card-content">
                                <div class="card-header">
                                    <h3 class="truncate"><%= r.getName() %></h3>
                                    <span class="rating"><%= r.getRatings() %>★</span>
                                </div>
                                <div class="card-details">
                                    <div class="details-row">
                                        <span class="cuisine-tag"><%= r.getCuisineType() %></span>
                                        <span class="delivery-time">
                                            <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/clock.svg" alt="Time" class="icon-small">
                                            <%= r.getDeliveryTime() %> mins
                                        </span>
                                    </div>
                                    <div class="details-row">
                                        <span class="address">
                                            <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/map-pin.svg" alt="Location" class="icon-small">
                                            <%= r.getAddress() %>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </article>
                <%
                    }
                }
                %>
            </div>
        </section>

        <section class="all-restaurants">
            <h2>All Restaurants</h2>
            <div class="restaurant-grid">
                <% 
                if (filteredList.isEmpty()) {
                %>
                    <p>No restaurants found matching your search.</p>
                <% 
                } else {
                    for (Restaurant r : filteredList) {
                %>
                    <article class="restaurant-card">
                        <a href="MenuServlet?restaurantId=<%= r.getRestaurantId() %>">
                            <div class="card-image">
                                <img src="<%= r.getImagePath() %>" alt="<%= r.getName() %>">
                            </div>
                            <div class="card-content">
                                <div class="card-header">
                                    <h3 class="truncate"><%= r.getName() %></h3>
                                    <span class="rating"><%= r.getRatings() %>★</span>
                                </div>
                                <div class="card-details">
                                    <div class="details-row">
                                        <span class="cuisine-tag"><%= r.getCuisineType() %></span>
                                        <span class="delivery-time">
                                            <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/clock.svg" alt="Time" class="icon-small">
                                            <%= r.getDeliveryTime() %> mins
                                        </span>
                                    </div>
                                    <div class="details-row">
                                        <span class="address">
                                            <img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/map-pin.svg" alt="Location" class="icon-small">
                                            <%= r.getAddress() %>
                                        </span>
                                    </div>
                                </div>
                            </div>
                        </a>
                    </article>
                <%
                    }
                }
                %>
            </div>
        </section>
    </main>

    <footer class="footer">
        <div class="footer-container">
            <div class="footer-section brand-section">
                <div class="footer-logo">
                    <img src="delivery-bike.png" alt="FoodExpress Logo" class="footer-app-logo">
                    <span>FoodExpress</span>
                </div>
                <p>Delivering happiness to your doorstep</p>
            </div>
            <div class="footer-section">
                <h3>About Us</h3>
                <ul>
                    <li><a href="#">Our Story</a></li>
                    <li><a href="#">Team</a></li>
                    <li><a href="#">Careers</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Contact</h3>
                <ul>
                    <li><a href="#">Help & Support</a></li>
                    <li><a href="#">Partner with us</a></li>
                    <li><a href="#">Ride with us</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Legal</h3>
                <ul>
                    <li><a href="#">Terms & Conditions</a></li>
                    <li><a href="#">Privacy Policy</a></li>
                    <li><a href="#">Cookie Policy</a></li>
                </ul>
            </div>
            <div class="footer-section">
                <h3>Follow Us</h3>
                <div class="social-links">
                    <a href="#"><img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/facebook.svg" alt="Facebook" class="social-icon"></a>
                    <a href="#"><img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/twitter.svg" alt="Twitter" class="social-icon"></a>
                    <a href="#"><img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/instagram.svg" alt="Instagram" class="social-icon"></a>
                    <a href="#"><img src="https://cdn.jsdelivr.net/npm/feather-icons/dist/icons/linkedin.svg" alt="LinkedIn" class="social-icon"></a>
                </div>
            </div>
        </div>
        <div class="footer-bottom">
            <p>&copy; 2023 FoodExpress. All rights reserved.</p>
        </div>
    </footer>
</body>
</html>

