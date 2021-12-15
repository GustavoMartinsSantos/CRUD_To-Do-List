<?php
	require_once('includes/header.php');
    require_once('includes/databaseFunctions.php');

    $conection = getConection();

    $search = filter_input(INPUT_GET, 'search', FILTER_SANITIZE_STRING);

    $query = "SELECT ID, title, description, due_date, status FROM tbl_tasks"
    . " WHERE title LIKE '%$search%'"
    . " OR description LIKE '%$search%'";

    $tasks = SELECT($query);

    $not_found1 = true;
    $not_found2 = true;
    $not_found3 = true;
?>
    <form method="GET" class="form-group mb-4">
        <div class="input-group mx-4 mt-3">
            <input name="search" id="searchField" placeholder="Pesquisar..." 
            type="text" class="form-control" value="<?= $search ?>">
            <button class="btn bg-dark text-light" type="submit"><i class="bi bi-search"></i></button>
            <a href="cadastrar.php">
                <button type="button" class="btn btn-success btn_tam">+</button>
            </a>
        </div>
    </form>

        <div class="centraliza">
            <table onClick="Telinha('edit_tela')">
                <tr>
                    <th>A fazer</th>
                </tr>

                    <?php 
                        if(count($tasks) != 0) {
                            foreach ($tasks as $task) { 
                                if ($task['status'] == 0) { 
                                    $data1 = new Datetime($task['due_date']);
                                    $not_found1 = false;
                                ?>
                                    <tr class="tarefa">
                                        <td>
                                            <p><?php echo $task['title']; ?></p>
                                            <?php echo isset($data1) ? $data1->format('d/m/Y H:i') : null ?>

                                            <div class="col mb-3">
                                                <a href="editar.php?id=<?php echo $task['ID']; ?>">
                                                    <button type="button" class="btn btn-primary">Editar</button>
                                                </a>
                                                <a href="excluir.php?id=<?php echo $task['ID']; ?>">
                                                    <button type="button" class="btn btn-danger">Excluir</button>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                    <?php       }
                            } 
                        }

                        if ($not_found1) { ?>
                            <tr class="tarefa">
                                <td>
                                    <p><?php echo "Tarefas não encontradas" ?></p>
                                </td>
                            </tr>
                    <?php   } ?>
            </table>

                <table onClick="Telinha('edit_tela')">
                    <tr>
                        <th>Fazendo</th>
                    </tr>

                    <?php 
                        if(count($tasks) != 0) {
                            foreach ($tasks as $task) { 
                                if ($task['status'] == 1) { 
                                    $data2 = new Datetime($task['due_date']);
                                    $not_found2 = false;
                                ?>
                                    <tr class="tarefa">
                                        <td>
                                            <p><?php echo $task['title']; ?></p>
                                            <?php echo isset($data2) ? $data2->format('d/m/Y H:i') : null ?>

                                            <div class="col mb-3">
                                                <a href="editar.php?id=<?php echo $task['ID']; ?>">
                                                    <button type="button" class="btn btn-primary">Editar</button>
                                                </a>
                                                <a href="excluir.php?id=<?php echo $task['ID']; ?>">
                                                    <button type="button" class="btn btn-danger">Excluir</button>
                                                </a>
                                            </div>
                                        </td>
                                    </tr>
                <?php           }
                            } 
                        }

                        if ($not_found2) { ?>
                            <tr class="tarefa">
                                <td>
                                    <p><?php echo "Tarefas não encontradas" ?></p>
                                </td>
                            </tr>
                <?php   } ?>
                </table>

                <table onClick="Telinha('edit_tela')">
                    <tr>
                        <th>Feito</th>
                    </tr>

                    <?php 
                        if(count($tasks) != 0) {
                            foreach ($tasks as $task) { 
                                if ($task['status'] == 2) { 
                                    $data3 = new Datetime($task['due_date']);
                                    $not_found3 = false;
                                ?>

                                    <tr class="tarefa">
                                        <td>
                                            <p><?php echo $task['title']; ?></p>
                                            <?php echo isset($data3) ? $data3->format('d/m/Y H:i') : null ?>

                                            <div class="row mb-3">
                                                <div class="col">
                                                    <a href="editar.php?id=<?php echo $task['ID']; ?>">
                                                        <button type="button" class="btn btn-primary btn_tam">Editar</button>
                                                    </a>

                                                    <a href="excluir.php?id=<?php echo $task['ID']; ?>">
                                                        <button type="button" class="btn btn-danger btn_tam">Excluir</button>
                                                    </a>
                                                </div>
                                            </div>
                                        </td>
                                    </tr>
                    <?php       }
                            } 
                        }

                        if ($not_found3) { ?>
                            <tr class="tarefa">
                                <td>
                                    <p><?php echo "Tarefas não encontradas" ?></p>
                                </td>
                            </tr>
                    <?php   } ?>
                </table>
                </table>
            </div>