package com.bawker.jdbctest;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ControllerEstudante {

    public static void adicionarEstudante(Estudante estudante) throws SQLException {
        Connection con = Conexao.Conectar();

        PreparedStatement stmt = con.prepareStatement("INSERT INTO estudante (codigo, nome, teste1, teste2) VALUES (?, ?, ?, ?)");

        stmt.setInt(1, estudante.getCodigo());
        stmt.setString(2, estudante.getNome());
        stmt.setDouble(3, estudante.getTeste1());
        stmt.setDouble(4, estudante.getTeste2());
        stmt.executeUpdate();
        stmt.close();
    }



    public static ArrayList<Estudante> listarEstudantes() throws SQLException, ClassNotFoundException {
        ArrayList<Estudante> estudantes = new ArrayList<>();

        PreparedStatement stmt = null;
        Connection con = Conexao.Conectar();
        stmt = con.prepareStatement("SELECT * FROM estudante");

        ResultSet rs = stmt.executeQuery();
        while (rs.next()){
            int codigo = rs.getInt(1);
            String nome = rs.getString(2);
            double teste1 = rs.getDouble(3);
            double teste2 = rs.getDouble(4);
            Estudante estudante = new Estudante(codigo, nome, teste1, teste2);
            estudantes.add(estudante);
        }
        con.close();
        return estudantes;
    }

    public static void actualizarEstudante(Estudante estudante) throws SQLException {
        Connection con = Conexao.Conectar();

        PreparedStatement stmt = con.prepareStatement("UPDATE estudante SET nome = ?, teste1 = ?, teste2 = ? WHERE codigo = ?");

        stmt.setString(1, estudante.getNome());
        stmt.setDouble(2, estudante.getTeste1());
        stmt.setDouble(3, estudante.getTeste2());
        stmt.setInt(4, estudante.getCodigo());
        stmt.executeUpdate();
        stmt.close();
    }


    public static void eliminarEstudante(int codigo) throws SQLException {
        Connection con = Conexao.Conectar();

        PreparedStatement stmt = con.prepareStatement("DELETE FROM estudante WHERE codigo = ?");
        stmt.setInt(1,codigo);
        stmt.executeUpdate();
    }
}
