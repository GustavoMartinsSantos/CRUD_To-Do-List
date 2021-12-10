<?php
	require_once('includes/header.php');
    require_once('includes/databaseFunctions.php');

    if(isset($task))
        $data = new Datetime($task['due_date']);
?>
    <div class="espaco mt-4">
        <form method="POST">
            <div class="form-floating">
                <input type="text" name="title" id="title" class="form-control" placeholder="No" maxlength="50" autofocus required
                value="<?php echo (TITLE == "Editar") ? $task['title'] : null ?>">
                <label class="form-label" for="title">Titulo da tarefa</label>
            </div>
            <br>
            <!--<label class="form-label mt-3">Descrição</label>
            <textarea name="Tarefa" rows="4" class="form-control" style="resize: none" maxlength="60000"></textarea>-->
            <br>
            <div class="row">
                <div class="col">
                    <div class="col-md">
                        <label class="form-label">Data de Vencimento</label>
                        <input type="date" name="date" class="form-control" required
                        value="<?php echo (TITLE == "Editar") ? $data->format("Y-m-d") : null ?>">
                    </div>
                </div>

                <div class="col">
                    <label class="form-label">Horário de vencimento:</label>
                    <input class="form-control" type="time" size="5%" name="time" required
                    value="<?php echo (TITLE == "Editar") ? $data->format('H:i') : null ?>">
                </div>

                <div class="col">
                    <label class="form-label">Status</label>
                    <select name="status" class="form-control">
                        <option value="0" <?php if(TITLE == "Editar") { echo $task['status'] == 0 ? "selected" : null; } ?>>A Fazer</option>
                        <option value="1" <?php if(TITLE == "Editar") { echo $task['status'] == 1 ? "selected" : null; } ?>>Em Desenvolvimento</option>
                        <option value="2" <?php if(TITLE == "Editar") { echo $task['status'] == 2 ? "selected" : null; } ?>>Feito</option>
                    </select>
                </div>
            </div>
                <div class="text-center">
                    <input type="submit" class="btn bg-warning mt-3 w-50 " value="<?php echo TITLE ?>">
                </div>    
                            
        </form>
    </div>
</body>
</html>