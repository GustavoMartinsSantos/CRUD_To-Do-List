<?php
	require_once('header.php');
    require_once('databaseFunctions.php');

    $conection = getConection();

    //$search = $_GET['search'];
    $search = filter_input(INPUT_GET, 'search', FILTER_SANITIZE_STRING);

    $query = "SELECT ID, title, description, due_date, status FROM tbl_tasks"
    . " WHERE title LIKE '%$search%'"
    . " OR description LIKE '%$search%'";

    echo $search . "<br>";
    echo $query . "<br><br>";

    $tasks = SELECT($query);
    
    if(isset($_POST['date'])) {
        echo $_POST['date'] . ' ' . $_POST['time'];
        
        INSERT($conection, array(
                'title' => "Projeto de SE",
                'description' => "Árvore de natal com arduino no Tinkercad. O projeto terá 5 fileiras, sendo a primeira para a estrela",
                'due_date' => $_POST['date'] . ' ' . $_POST['time'],
                'status' => 2
        ));
    }

    //DELETE(['ID' => 14]);
?>

    <form method="GET" class="form-group mb-4">
        <div class="input-group mx-4">
            <input name="search" id="searchField" placeholder="Pesquisar..." 
            type="text" class="form-control" value="<?= $search ?>">
            <button class="btn bg-dark text-light" type="submit"><i class="bi bi-search"></i></button>
        </div>
    </form>

    <form method="POST" class="form-group mb-4">
        <input type="date" name="date">
        <input type="time" name="time">
        <input type="submit">
    </form>

            <div id="edit_tela">
                <div class="blur"><img src="./Iago.png" height="100%" width="100%" onClick="Telinha('edit_tela')"> </div>
            
                <div class="tela_flut"> </div>
                <div class="task_titulo"> 
                    <input type="text" id="task_bloco_titulo" placeholder="Digite o titulo"/>
                </div>
                
                <div class="task_legenda"> </div>
                <input type="text" id="task_bloco_corpo" placeholder="Digite a descrição"/>
            </div>
            <div class="centraliza">
                <table onClick="Telinha('edit_tela')">
                    <tr>
                        <th>A fazer</th>
                    </tr>

                    <? foreach ($tasks as $task) { 
                        if ($task['status'] == 0) { 
                            $data = new Datetime($task['due_date']);
                    ?>
                            <tr class="tarefa">
                                <td>
                                    <p class="p_tarefa"><?= $task['title'] ?></p>
                                    <?= $data->format('d/m/Y H:i') ?>
                                </td>
                            </tr>
                    <? } } ?>
                </table>

                <table  onClick="Telinha('edit_tela')">
                    <tr>
                        <th>Fazendo</th>
                    </tr>
                    <tr class="tarefa">
                        <td><p>exemplo</p></td>
                    </tr> 
                </table>

                <table onClick="Telinha('edit_tela')">
                    <tr>
                        <th>Feito</th>
                    </tr>
                    <tr class="tarefa">
                        <td><p>exemplo</p></td>
                    </tr>
                </table>
            </div>