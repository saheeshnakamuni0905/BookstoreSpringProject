<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Author Operations</title>
    <link href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-light">
        <a class="navbar-brand" href="#">BookStore</a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarNavAltMarkup" aria-controls="navbarNavAltMarkup" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNavAltMarkup">
            <div class="navbar-nav">
                <a class="nav-item nav-link" href="/logout">Logout</a>
            </div>
        </div>
    </nav>

    <div class="container mt-5">
        <h2>Add Author</h2>
        <form action="/addAuthor" method="post">
            <div class="form-group">
                <label for="authorName">Author Name:</label>
                <input type="text" class="form-control" id="authorName" name="authorName" required>
            </div>
        <div class="row mt-4">
            <div class="col-md-6">
                <button type="submit" class="btn btn-primary">Add Author</button>
            </div>
        </div>
        </form>

    
    <div class="container mt-5">
        <h2>Delete Author</h2>
        <form action="/deleteAuthor" method="post">
            <div class="form-group">
                <label for="authorName">Author Name:</label>
                <input type="text" class="form-control" id="authorName" name="authorName" required>
            </div>
        <div class="row mt-4">
            <div class="col-md-6">
                <button type="submit" class="btn btn-primary">Delete Author</button>
            </div>
        </div>
    </form>
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.9.2/dist/umd/popper.min.js"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
</body>
</html>
    