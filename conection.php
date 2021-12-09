<?php
    /*function getConection () {
        $server   = "localhost";
        $database = "";
        $user     = "db_Tarefas";
        $pass     = "";

        $conection = new PDO("mysql:host=$server;dbname=$database", $user, $pass);

        return $conection
    }*/
    $server = "localhost";
    $user   = "root";
    $passwd = "G4jIS9D2d62";
    $bd     = "bd_usuarios";

    $connection = mysqli_connect($server, $user, $passwd, $bd);

    if($connection->connect_errno)
        echo "Error: Falha ao conectar-se com o banco de dados MySQL.";
?>