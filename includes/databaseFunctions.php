<?php
    function getConection () {
        $server   = "database";
        $database = "db_tasks";
        $user     = "User_ToDoList";
        $pass     = "Kjopk9qNHl";

        $conection = new PDO("mysql:host=$server;dbname=$database", $user, $pass);

        return $conection;
    }

    function executeQuery ($query, $values = []) {
        try {
            $stmt = getConection()->prepare($query);

            $stmt->execute(array_values($values));
            
            return $stmt;
        } catch(PDOException $e) {
            echo $query;
            die("<br>ERRO: {$e->getMessage()}<br>");
        }
    }

    function INSERT ($values = [], $table = "tbl_tasks") {
        $keys = array_keys($values);
        $binds = array_fill(0, sizeof($keys), '?');

        $query  = "INSERT INTO $table (" . implode(',',$keys) . ")";
        $query .= " VALUES (" . implode(',', $binds) . ")";

        executeQuery($query, array_values($values));

        return getConection()->lastInsertId();
    }

    function SELECT ($query) {
        return executeQuery($query)->fetchAll();
    }

    function UPDATE ($values = [], $pk = [], $table = "tbl_tasks") {
        $query  = "UPDATE $table ";
        $query .= "SET " . implode('=?, ', array_keys($values)) . '=?';
        $query .= ' WHERE ' . array_keys($pk)[0] . " = " . array_values($pk)[0];

        executeQuery($query, array_values($values));
    }

    function DELETE ($pk = [], $table = "tbl_tasks") {
        $query  = "DELETE FROM $table ";
        $query .= "WHERE " . array_keys($pk)[0] . " = " . array_values($pk)[0];

        return executeQuery($query);
    }
?>