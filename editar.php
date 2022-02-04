<?php
    ob_start();
    
    require_once('includes/databaseFunctions.php');

    define("TITLE", "Editar");

    if(!isset($_GET['id']) || !is_numeric($_GET['id'])) {
        header("Location: index.php");
        exit;
    }

    $task = SELECT("SELECT title, description, due_date, status FROM tbl_tasks WHERE ID = {$_GET['id']}")[0];

    if(count($task) == 0) {
        header("Location: index.php");
        exit;
    }

    if($WEB) { require_once("includes/form_cadastrar.php"); }

    if(isset($_POST['title'])) {
        $title = filter_input(INPUT_POST, 'title', FILTER_SANITIZE_STRING);
        $title = trim(preg_replace('/\s+/', " ", $title));
        
        $description = filter_input(INPUT_POST, 'description', FILTER_SANITIZE_STRING);
        
        UPDATE(array(
                'title' => $title,
                'description' => $description,
                'due_date' => $_POST['date'] . ' ' . $_POST['time'],
                'status' => $_POST['status']
        ), ['ID' => $_GET['id']]);

        if($WEB)
            header("Location: index.php");
        else
            echo "Editado com sucesso";
    }
?>