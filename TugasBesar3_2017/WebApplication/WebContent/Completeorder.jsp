<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="http://malsup.github.com/jquery.form.js"></script>    
    
<%
    String mytoken = "";
    Cookie[] mycookies = request.getCookies();
    if (mycookies != null) {
        for (int i = 0; i < mycookies.length; ++i) {
        	if (mycookies[i].getName().equals("token")) {
        		mytoken = mycookies[i].getValue();
        			break;
        	}
        }
    }
%>

<jsp:useBean id="completeProxy" scope="request" class="com.services.UserServiceProxy" />
<%
	completeProxy.setEndpoint("http://localhost:8000/WebService/User");
	String idStr = request.getParameter("id_driver");
	String pickup = request.getParameter("pickup");
	String dest = request.getParameter("dest");
	int id = Integer.parseInt(idStr);
	
	com.services.User user = null;
	String redirect = "";
	try{
		user = completeProxy.getUser(mytoken, Integer.parseInt(request.getParameter("id_active")));	
		user = completeProxy.getUserWithoutValidation(id);
	} catch (com.services.TokenException tex) {
		redirect = "LogoutServlet?id=" + request.getParameter("id_active") + "&e=" + completeProxy.getValidation(mytoken, Integer.parseInt(request.getParameter("id_active")));
		user = new com.services.User();
	}
%>

<%
	out.println("<script>id = " + request.getParameter("id_active") + "</script>");
	out.println("<script>id2 = " + request.getParameter("id_driver") + "</script>");
%>

<script>
$.ajax({        
            type : 'POST',
            url : "http://localhost:8080/api/avals/changestat",
            //contentType : 'application/json',
            //dataType: 'json',
            data: {id: id2, status: false, customer:0}
  		});
</script>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<link rel="icon" href="img/icon.png" />
    <title>Ojek Panas | Order</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
	<div id="navbar">
	  <%@ include file="Navbar.jsp" %>
	  <div class="after-box">
	    <div class="centered">
	      <a href="Order.jsp?id_active=<%= request.getParameter("id_active") %>" class="active-order">ORDER</a>
	      <a href="Historyorder.jsp?id_active=<%= request.getParameter("id_active") %>" class="list-item-history">HISTORY</a>
	      <a href="Profile.jsp?id_active=<%= request.getParameter("id_active") %>" class="list-item-profile">MY PROFILE</a>
	    </div>
	  </div>
	</div>
	
	<div id="order-header">
	  <div class="floating-box-left-mo">MAKE AN ORDER</div>
	    <ul class="list-centered">
	      <li class="list-order"><div class="circle">1</div>Select Destination</li>
	      <li class="list-order"><div class="circle">2</div>Select a Driver</li>
		  <li class="list-order"><div class="circle">3</div>Chat Driver</li>
	      <li class="order-active"><div class="circle">4</div>Complete Your Order</li>
	    </ul>
	</div>
	
	<form action="Finishorder?id_active=<%= request.getParameter("id_active") %>" method="POST" onsubmit="return starValidation()">
	  <div id="order-content">
	    <div class="floating-box-left-o">HOW WAS IT?</div><br><br><br><br><br>
	    <img class="picture-o" src="<%= user.getImagePath() %>">
	    <p class="username">@<%= user.getUsername() %></p>
	    <p class="data"><%= user.getName() %></p>
	    <div class="star">
	      <input class="star star-5" id="star-5" type="radio" name="star" value="5"/>
	      <label class="star star-5" for="star-5"></label>
	      <input class="star star-4" id="star-4" type="radio" name="star" value="4"/>
	      <label class="star star-4" for="star-4"></label>
	      <input class="star star-3" id="star-3" type="radio" name="star" value="3"/>
	      <label class="star star-3" for="star-3"></label>
	      <input class="star star-2" id="star-2" type="radio" name="star" value="2"/>
	      <label class="star star-2" for="star-2"></label>
	      <input class="star star-1" id="star-1" type="radio" name="star" value="1"/>
	      <label class="star star-1" for="star-1"></label>
	    </div>
	  </div>
	
	  <input type="hidden" name="id_driver" value="<%= idStr %>">
	  <input type="hidden" name="pickup" value="<%= pickup %>">
	  <input type="hidden" name="dest" value="<%= dest %>">
	
	  <div id="comment-page">
	    <textarea class="input-text-long" name="comment" id="comment"></textarea><br>
	    <button class="button" type="submit">COMPLETE ORDER</button>
	  </div>
	</form>
</body>
</html>

<%
	if (redirect != "") {
		response.sendRedirect(redirect);
	}
%>