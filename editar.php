<?php
    require_once('includes/databaseFunctions.php');

    define("TITLE", "Editar");
    define("SUBMIT", "Editar");

    if(!isset($_GET['id']) || !is_numeric($_GET['id'])) {
        header("Location: index.php");
        exit;
    }

    $task = SELECT("SELECT title, due_date, status FROM tbl_tasks WHERE ID = {$_GET['id']}")[0];

    if(count($task) == 0) {
        header("Location: index.php");
        exit;
    }
    
    define("ROUTE", "editar.php?id=" . $_GET['id']);

    require_once("includes/form_cadastrar.php");

    if(isset($_POST['title'])) {
        $title = filter_input(INPUT_POST, 'title', FILTER_SANITIZE_STRING);
        $title = trim(preg_replace('/\s+/', " ", $title));
        
        UPDATE(array(
                'title' => $title,
                'description' => null,
                'due_date' => $_POST['date'] . ' ' . $_POST['time'],
                'status' => $_POST['status']
        ), ['ID' => $_GET['id']]);

        header("Location: index.php");
    }
?>