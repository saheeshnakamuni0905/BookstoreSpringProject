<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>View Cart</title>
    <link href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">BookStore</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="/viewCart">View Cart</a>
                <a class="nav-item nav-link" href="/logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container">
        <h1 class="mt-5 mb-4">Your Cart</h1>
        <c:if test="${not empty cartItems}">
            <form action="/placeOrder" method="post">
                <table class="table">
                    <thead>
                        <tr>
                            <th>Title</th>
                            <th>Author</th>
                            <th>Price</th>
                            <th>Quantity</th>
                            <th>Action</th>
                        </tr>
                    </thead>
                    <tbody>
                        <c:forEach items="${cartItems}" var="cartItem">
                            <tr>
                                <td>${cartItem.book.title}</td>
                                <td>${cartItem.book.author.authorName}</td>
                                <td>${cartItem.book.price}</td>
                                <td>${cartItem.quantity}</td>
                                <td>
                                    <input type="hidden" name="cartItemId" value="${cartItem.cartItemId}" />
                                    <a href="/removeCartItem?cartItemId=${cartItem.cartItemId}" class="btn btn-danger btn-sm">Delete</a>
                                </td>
                            </tr>
                        </c:forEach>
                    </tbody>
                </table>
                <input type="hidden" name="cartId" value="${cart.cartId}" />
                <button type="submit" class="btn btn-primary">Place Order</button>
            </form>
        </c:if>

        <c:if test="${empty cartItems}">
            <p>Your cart is empty.</p>
        </c:if>
    </div>

    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
