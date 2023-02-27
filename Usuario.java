/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package usuario;

// Bibliotecas Importadas para as leituras e tratamenos com SQL
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author lucas.monteiro
 */
public class Usuario {

    /* Funcão estática listarUsuario para listagem dos usuários sem paasagem de parametros*/
    static void listarUsuario() {
        /* Parametros de configuação com o banco MySQL*/
        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        /* Endere, Porta e Nome da base de Dados */
        String dbUsername = "root";/* Usuário */
        String dbPassword = "";
        /* Senha */

 /*  Instanciação da conexão com DriverManager.getConnection */
        try ( Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

            String sql = "SELECT * FROM usuario";
            /* Selecionar todos usuários da tabela usuário */

            Statement statement = conn.createStatement();/* Criação do statement conn*/
            ResultSet result = statement.executeQuery(sql);
            /* result prepara para a execução query */

            int cont = 0;/* Contador */

            while (result.next()) {/* Enquanto result tiver informações vá para o próximo */
                String user_id = result.getString(1);
                /* passagem do primeiro elemento através da posição 1 */
                String name = result.getString(2);/* passagem do segundo elemento  através da posição 2 */
                String pass = result.getString(3);/* passagem do terceiro elemento  através da posição 3*/
                String fullname = result.getString("fullname");/* passagem do quarto elemento pela ordem de preenchimento (4)*/
                String email = result.getString("email");/* passagem do quinto elemento pela ordem de preenchimento (5) */
           /* Recebimento dos elementos 1 - 2 - 3 - 4- 5 o sexto elemento do contador com execução */
                String output = "User #%s: %s - %s - %s - %s \nTotal de %d Usuarios";
                System.out.println(String.format(output, user_id, name, pass, fullname, email, ++cont));
            }

        } catch (SQLException ex) {/* Saída do erro da exceção */
            ex.printStackTrace();
        }

    }

    /* Função estática inserirUsuario com passagem dos parametros pela função que serão inseridos pelo Scanner  */
    static void inserirUsuario(String username, String password, String fullname, String email) {

        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String dbUsername = "root";
        String dbPassword = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            /* Inserção das informações na ordem username = ?, password = ?, fullname = ? e email = ? */
            String sql = "INSERT INTO usuario (username, password, fullname, email) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);/*  Preparando a coneção para execução da query de inserção */
            statement.setString(1, username);/* Recebimento do statment da variavel username */
            statement.setString(2, password);/* Recebimento do statment da variavel password */
            statement.setString(3, fullname);/* Recebimento do statment da variavel  fullname */
            statement.setString(4, email);/* Recebimento do statment da variavel email */

            int rowsInserted = statement.executeUpdate();
            /* execução do statment de inserção */
            if (rowsInserted > 0) {/* Se foi inserido a linha entra no if */
                System.out.println("Usuario adicionado com Sucesso!");
                /* Impressão da menssagem */
                conn.close();
                /* encerramento da conexão */
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    /* Função estática atualizarUsuario com passagem dos parametros pela função que serão inseridos pelo Scanner atrvés do useer_id informado  */
    static void atualizarUsuario(int user_id, String username, String password, String fullname, String email) {
        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String dbUsername = "root";
        String dbPassword = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {
            /* Atualização das informações na ordem username = ?, password = ?, fullname = ? e email = ?  AONDE o user_id for igual ao ID fornecido*/
            String sql = "UPDATE usuario SET username=?, password=?, fullname=?, email=? WHERE user_id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setString(4, email);
            statement.setInt(5, user_id);
            /*ID fornecido para atualização */

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Atualizado com Sucesso!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /* Deleção do usuárioatravés do ID fornecido*/
    static void deletarUsuario(int user_id) {
        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String username = "root";
        String password = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, username, password)) {
            /* Query de Deleção do usuário atrvés do ID*/
            String sql = "DELETE FROM usuario WHERE user_id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setInt(1, user_id);

            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Usuario Deletado!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        String username, password, fullname, email;
        int opcao, user_id;
        Scanner input = new Scanner(System.in);
        /* Exibição do menu de opções */
        System.out.print("\nDigite a opcao desejada:\n 1)Listar Usuarios. \n 2)Inserir Usuario\n 3)Deletar Usuario \n 4)Atualizar Usuario\n");
        opcao = input.nextInt();
        /* SE a opção seja 1 irá executar a função listarUsuario */
        if (opcao == 1) {
            listarUsuario();
        } else if (opcao == 2) {
            /* SE opção for 2 irá solicitar os dados para inserir as informações pelo Scanner */
            System.out.print("\nDigite seu login:\n");
            username = input.next();

            System.out.print("\nDigite sua senha:\n");
            password = input.next();

            System.out.print("\nDigite seu Nome Completo\n");
            fullname = input.next();

            System.out.print("\nDigite seu e-mail\n");
            email = input.next();

            inserirUsuario(username, password, fullname, email);
            listarUsuario();
            /* Após a inserção irá executar a listarUsuario */
        } else if (opcao == 3) {
            System.out.print("\nDigite o ID do usario que deseja deletar");
            user_id = input.nextInt();

            deletarUsuario(user_id);
            listarUsuario();/* Após a inserção irá executar a listarUsuario */
        /* SE a opção for 4 irá solicitar o ID do usuário que deseja alterar */
        } else if (opcao == 4) {

            System.out.print("\nDigite ID do usuario:\n");
            /* Preenchimento do ID do usuário */
            user_id = input.nextInt();

            System.out.print("\nDigite seu login:\n");
            username = input.next();

            System.out.print("\nDigite sua senha:\n");
            password = input.next();

            System.out.print("\nDigite seu Nome Completo\n");
            fullname = input.next();

            System.out.print("\nDigite seu e-mail\n");
            email = input.next();

            atualizarUsuario(user_id, username, password, fullname, email);
            listarUsuario();
        }

    }

}
