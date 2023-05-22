package com.bawker.jdbctest;

import java.sql.Connection;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexao {
    public static Connection Conectar(){

        Connection con = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = java.sql.DriverManager.getConnection("jdbc:mysql://localhost:3306/teste", "root", "");
            JOptionPane.showMessageDialog(null, "Conectado com sucesso");

            return con;
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro ao conectar");
            e.printStackTrace();
        }
        return con;
    }

}

