<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
    
<!DOCTYPE html>
<html>
    <head>
        <meta charset="UTF-8">
        <title><c:out value="${product.name}"></c:out></title>
    </head>
    <body>
     	<h1><c:out value="${product.name}"></c:out></h1>
     	<ul>
     		<c:forEach items="${product.categories}" var="category">
     			<li><c:out value="${category.name}"></c:out></li>
     		</c:forEach>
     	</ul>
        <form action="/products/${product.id}/add" method="post">
		    <p>
		        <label for="category">Category</label>
		        <select name="category" id="category">
		        	<c:forEach items="${categories}" var="category">
		        		<option value="${category.id}">
		        			<c:out value="${category.name}"/>
	        			</option>
		        	</c:forEach>
		        </select>
		    </p>
		    <input type="submit" value="Add"/>
		</form>
    </body>
</html>