<?php
    ob_start();

    define("TITLE", "Cadastrar");
    define("ROUTE", "cadastrar.php");

    require_once('includes/databaseFunctions.php');
    if($WEB) { require_once("includes/form_cadastrar.php"); }

    if(isset($_POST['title'])) {
        $title = filter_input(INPUT_POST, 'title', FILTER_SANITIZE_STRING);
        $title = trim(preg_replace('/\s+/', " ", $title));
        
        $description = filter_input(INPUT_POST, 'description', FILTER_SANITIZE_STRING);

        if($title == null) {
            echo 'Digite um título válido!';

            if($WEB)
                header('Location: cadastrar.php');

            exit;
        }

        $ID = INSERT(array(
                'title' => $title,
                'description' => $description,
                'due_date' => $_POST['date'] . ' ' . $_POST['time'],
                'status' => $_POST['status']
        ));

        echo "Cadastro realizado com sucesso";

        if($WEB)
            header("Location: index.php");
    }
?>