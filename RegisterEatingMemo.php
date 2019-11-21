<?php
	 $con = mysqli_connect("localhost","jkey20","wndltlr9557!","jkey20");

	 $userText = $_POST["userText"];
	 $userLat = $_POST["userLat"];
	 $userLon = $_POST["userLon"];
	 
	 
	 $statement = mysqli_prepare($con, "INSERT INTO USER VALUES (?, ?, ?)");
	 mysqli_stmt_bind_param($statement, "sss", $userText, $userLat,$userLon);
	 mysqli_stmt_execute($statement);

	 $response = array();
	 $response["success"] = true;

	 echo json_encode($response);	

 ?>