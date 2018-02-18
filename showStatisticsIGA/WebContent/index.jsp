<%@ page import="app.entities.Users" %>
<%@ page import="app.entities.Changes" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Сhange statistics</title>
        <link rel="stylesheet" href="https://www.w3schools.com/w3css/4/w3.css">
    </head>

    <body class="w3-light-grey">
        <div class="w3-container w3-blue-grey w3-opacity w3-right-align">
            <h1>Сhange statistics!</h1>
        </div>

        <div class="w3-container w3-padding">
            <div class="w3-card-4">
                <div class="w3-container w3-center w3-green">
                    <h2>Fill in all the form fields</h2>
                </div>
                <form method="post" class="w3-selection w3-light-grey w3-padding" onsubmit="return sub()">
                	<div>
						<div style="float:left; width: 25%">
		                    <h3>Users</h3>
							<%
								Users.clear(); //clear old users array
								Users.addUsers(); //collect users from user.txt
								for (int i = 0; i < Users.getSize(); i++)
									out.println("<p><label><input type='checkbox' name='" + i + "' class='chekbox' value='something' />" + Users.getUser(i) + "</label></p>");
							%>
						</div>
			           
			            <div style="float:left; width: 25%">
		                    <h3>Type of change</h3>
							<p><label><input type='radio' class="w3-date w3-border w3-round-large"name="changeType" value="checkin"/>Checkin</label></p>
							<p><label><input type='radio' name="changeType" value="checkout"/>Checkout</label></p>
							<p><label><input type='radio' checked name="changeType" value="create"/>Create</label></p>
							<p><label><input type='radio' name="changeType" value="connect"/>Connect</label></p>
							<p><label><input type='radio' name="changeType" value="modify"/>Modify</label></p>
							<p><label><input type='radio' name="changeType" value="move files"/>Move files</label></p>
						</div>
						
			            <div style="float:left; width: 25%">
		                    <h3>Date range</h3>
		                    <label>First date:<br/>
				            	<input type="date" class="w3-date w3-border w3-round-large" id="leftDate" name="leftDate" placeholder="Дата" required style="width: 80%"><br/><br/>
				            </label>
				            <label>Second date:<br/>
				        		<input type="date" class="w3-date w3-border w3-round-large" id="rightDate" name="rightDate" placeholder="Дата" 
				        			   onblur="if (document.getElementById('rightDate').value <= document.getElementById('leftDate').value) 
				        		 	  		   alert('Incorrect date range!\nThe first date is greater than or equal to the last date');" required style="width: 80%">
				        	</label>
			        	</div>
						
			            <div style="float:left; width: 25%">
		                    <h3>Time step to X-axis</h3>
							<p><label><input type='radio' name="stepX" value="300000"/> 5 minutes</label></p>
							<p><label><input type='radio' name="stepX" value="3600000"/>Hour</label></p>
							<p><label><input type='radio' name="stepX" value="86400000"/>Day</label></p>
							<p><label><input type='radio' name="stepX" value="604800000"/>Week</label></p>
							<p><label><input type='radio' checked name="stepX" value="2635200000"/>Month</label></p>
							<p><label><input type='radio' name="stepX" value="31536000000"/>Year</label></p>
						</div>
						
                    	<button type="submit" class="w3-btn w3-green w3-round-large w3-margin-bottom" 
	                    		style="margin: auto; position: relative; top: 0; left: 25%; bottom: 0; right: 0; width: 50%">View graphic</button>
	                </div>
                </form>
	                
				<script>
					function sub() {
				  	  var a = !!document.querySelector(".chekbox:checked");
				    	a || alert("At least one user must be selected!");
				    	return a
					};
				</script>
	            </div>
	        </div>
    </body>
</html>