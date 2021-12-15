<?php
    define("TITLE", "Cadastrar");
    define("ROUTE", "cadastrar.php");

    require_once('includes/databaseFunctions.php');
    require_once("includes/form_cadastrar.php");

    if(isset($_POST['title'])) {
        $title = filter_input(INPUT_POST, 'title', FILTER_SANITIZE_STRING);
        $title = trim(preg_replace('/\s+/', " ", $title));
        
        INSERT(array(
                'title' => $title,
                'description' => null,
                'due_date' => $_POST['date'] . ' ' . $_POST['time'],
                'status' => $_POST['status']
        ));

        header("Location: index.php");
    }
?>