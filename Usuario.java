/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package usuario;

// Insert
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Scanner;

// Select
import java.sql.ResultSet;
import java.sql.Statement;

/**
 *
 * @author lucas.monteiro
 */

public class Usuario {

    static void listarUsuario() {
        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String dbUsername = "root";
        String dbPassword = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

            String sql = "SELECT * FROM usuario";

            Statement statement = conn.createStatement();
            ResultSet result = statement.executeQuery(sql);

            int cont = 0;

            while (result.next()) {
                String user_id = result.getString(1);
                String name = result.getString(2);
                String pass = result.getString(3);
                String fullname = result.getString("fullname");
                String email = result.getString("email");

                String output = "User #%s: %s - %s - %s - %s \nTotal de %d Usuarios";
                System.out.println(String.format(output, user_id, name, pass, fullname, email, ++cont));
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    static void inserirUsuario(String username, String password, String fullname, String email) {

        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String dbUsername = "root";
        String dbPassword = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

            String sql = "INSERT INTO usuario (username, password, fullname, email) VALUES (?, ?, ?, ?)";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setString(4, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Usuario adicionado com Sucesso!");
                conn.close();
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }

    }

    static void atualizarUsuario(int user_id, String username, String password, String fullname, String email) {
        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String dbUsername = "root";
        String dbPassword = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, dbUsername, dbPassword)) {

            String sql = "UPDATE usuario SET username=?, password=?, fullname=?, email=? WHERE user_id=?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, fullname);
            statement.setString(4, email);
            statement.setInt(5, user_id);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                System.out.println("Atualizado com Sucesso!");
            }

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    static void deletarUsuario(int user_id) {
        String dbURL = "jdbc:mysql://localhost:3306/dbusuario";
        String username = "root";
        String password = "";

        try ( Connection conn = DriverManager.getConnection(dbURL, username, password)) {

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

        System.out.print("\nDigite a opcao desejada:\n 1)Listar Usuarios. \n 2)Inserir Usuario\n 3)Deletar Usuario \n 4)Atualizar Usuario\n");
        opcao = input.nextInt();

        if (opcao == 1) {
            listarUsuario();
        } else if (opcao == 2) {
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
        } else if (opcao == 3) {
            System.out.print("\nDigite o ID do usario que deseja deletar");
            user_id = input.nextInt();

            deletarUsuario(user_id);
            listarUsuario();

        } else if (opcao == 4) {

            System.out.print("\nDigite ID do usuario:\n");
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
