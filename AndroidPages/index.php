<?php
    require_once('../includes/databaseFunctions.php');

    $conection = getConection();

    $search = filter_input(INPUT_GET, 'search', FILTER_SANITIZE_STRING);
    $id = filter_input(INPUT_GET, 'id', FILTER_SANITIZE_STRING);
    $status = filter_input(INPUT_GET, 'status', FILTER_SANITIZE_STRING);

    $query = "SELECT ID, title, description, due_date, status FROM tbl_tasks WHERE ";

    if($id != null)
        $query .= "id = $id";
    else {
        if($status != null)
            $query .= "status = $status AND ";
        $query .= "(title LIKE '%".str_replace(' ', '%', $search)."%' OR"
        . " description LIKE '%".str_replace(' ', '%', $search)."%')";
    }

    $query .= " ORDER BY 4";

    $tasks = SELECT($query);?>

    <?php echo "<table>";
        for($y = 0; $y < count($tasks); $y++) {
            echo "<tr>";
            for($x = 0; $x < 5; $x++)
                echo "<td>" . utf8_decode(nl2br($tasks[$y][$x]));
        }
?>