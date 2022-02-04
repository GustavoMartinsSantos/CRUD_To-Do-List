<?php
    require_once('includes/databaseFunctions.php');

    if(!isset($_GET['id']) || !is_numeric($_GET['id'])) {
        header("Location: index.php");
        exit;
    }

    $task = SELECT("SELECT Title FROM tbl_tasks WHERE ID = {$_GET['id']}");

    if($task == false) {
        header("Location: index.php");
        exit;
    }
    
    DELETE(['ID' => $_GET['id']]);
    
    if($WEB) 
        header("Location: index.php"); 
    else
        echo "Excluído com sucesso";
?>