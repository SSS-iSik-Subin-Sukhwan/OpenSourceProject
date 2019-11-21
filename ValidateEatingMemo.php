<?php

	$con = mysqli_connect("localhost","jkey20","wndltlr9557!","jkey20");

	$userID = $_POST["userText"];

	$statement = mysqli_prepare($con, "SELECT * FROM USER WHERE userText = ?");
	mysqli_stmt_bind_param($statement, "s", $userText);
	mysqli_stmt_execute($statement);
	mysqli_stmt_store_result($statement);
	mysqli_stmt_bind_result($statement, $userText);

	$response = array();
	$response["success"] = true;

	while(mysqli_stmt_fetch($statement)){
		$response["success"] = false;
		$response["userText"] = $userText;
	}

	echo json_encode($response);
?>
