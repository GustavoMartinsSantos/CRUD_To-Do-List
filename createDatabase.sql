DROP DATABASE IF EXISTS db_tasks;

CREATE DATABASE db_tasks;

USE db_tasks;

CREATE TABLE tbl_tasks (
    ID INT PRIMARY KEY AUTO_INCREMENT,
    Title VARCHAR(50) NOT NULL,
    Description TEXT,
    Due_Date DATETIME NOT NULL,
    Status INT NOT NULL
);

CREATE USER IF NOT EXISTS 'User_ToDoList' IDENTIFIED BY 'Kjopk9qNHl';

/*
	ALL PRIVILEGES — como vimos anteriormente, isso garante ao usuário do MySQL acesso completo a um banco de dados (ou, se nenhum banco de dados for selecionado, acesso global a todo o sistema)
	CREATE — permite criar novas tabelas ou bancos de dados
	DROP — permite deletar tabelas ou bancos de dados
	DELETE — permite excluir linhas de tabelas
	INSERT — permite inserir linhas em tabelas
	SELECT - permite usar o comando SELECT para ler os bancos de dados
	UPDATE — permite atualizar linhas de tabelas
	GRANT OPTION — permite conceder ou remover privilégios de outros usuários
*/

GRANT INSERT, SELECT, UPDATE, DELETE
ON db_tasks.* TO 'User_ToDoList';

/*SELECT User, Host FROM mysql.user;
SHOW GRANTS FOR User_ToDoList;*/