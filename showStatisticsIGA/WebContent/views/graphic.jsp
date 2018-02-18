<%@ page import="app.entities.Changes" %>
<%@ page import="java.util.Date" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<html>
<head>
<title>Statistics of changes</title>
   <script src="http://ajax.googleapis.com/ajax/libs/jquery/2.1.3/jquery.min.js"></script>
   <script src="http://code.highcharts.com/highcharts.js"></script>>
   <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
</head>
<body>
<div id="container" style="width: 100%; height: 400px; margin: 0 auto"></div>
<script language="JavaScript">
$(document).ready(function() {
	var title = {
	text: 'Object change statistics'  
	};
	
   	var subtitle = {
  	text: <% out.print("\"Action is " + Changes.changeType + "\""); %>
	};
   	
    var xAxis = {
    categories: [
    			<%
    				//print date + stepX under X-axis
    				long categor = Changes.leftDate;
   					while (categor < Changes.rightDate + Changes.stepX) {
   	    				Date date = new Date(categor);
   						out.print("'" + date + "',");
   						categor += Changes.stepX;
   					}
    			%>
     		    ]
	};
    
   	var yAxis = {
   		title: {
      		text: 'Number of changes'
   		},
   		plotLines: [{
       	    value: 0,
      		width: 1,
      		color: '#808080'
   		}]
	};     

   	var legend = {
   	layout: 'vertical',
   	align: 'right',
   	verticalAlign: 'middle',
   	borderWidth: 0
	};
   	
   	<% 
   		Changes.clear(); //clear old statistics
   		Changes.findCountChanges(); //collect new statistics
   	%>

   	//create json object with statistics
   	var series = [ 
   		<% 
   			for (int i = 0; i < Changes.getSize() - 1; i++) //personal changes statistics
   				out.print("{ name: \"" + Changes.checkedUsers.get(i) +
   						  "\", data: " + Changes.getChanges(i) +
   						  "},");
   		
  			out.print("{ name: 'TOTAL'" + 				//common changes statistics
					  ", data: " + Changes.getChanges(Changes.getSize() - 1) +
					  "}");
   		%> 
   	];
   
   	var json = {};

	json.title = title;
	json.subtitle = subtitle;
	json.xAxis = xAxis;
	json.yAxis = yAxis;
	json.legend = legend;
	json.series = series;

   	$('#container').highcharts(json);
});
</script>
<div class="w3-container w3-grey w3-opacity w3-right-align w3-padding">
    <button class="w3-btn w3-round-large" onclick="location.href='http://localhost:8080/showStatisticsIGA/index.jsp'">Back to form</button>
</div>
</body>
</html>