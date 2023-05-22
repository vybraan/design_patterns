package com.bawker.jdbctest;

//import javafx.fxml.FXML;
//import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import javafx.fxml.FXML;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.ResourceBundle;
public class HelloController implements Initializable {

    @FXML
    private TextField codigoText;

    @FXML
    private TextField nomeText;

    @FXML
    private TableView<Estudante> tableView;

    @FXML
    private TextField test1Text;

    @FXML
    private TextField teste2Text;

    public int editingIndex = 0;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        tableView.setRowFactory(tv -> {
            TableRow<Estudante> row = new TableRow<>();

            row.setOnMouseClicked(event -> {
                if (!row.isEmpty() && event.getButton() == MouseButton.PRIMARY
                        && event.getClickCount() == 2) {

                    editingIndex = row.getIndex();
                    Estudante clickedRow = row.getItem();

                    codigoText.setText(clickedRow.getCodigo() + "");

                    test1Text.setText(clickedRow.getTeste1() + "");

                    teste2Text.setText(clickedRow.getTeste2() + "");

                    nomeText.setText(clickedRow.getNome());

                }
            });
            return row;
        });

    }


    @FXML
    void actualizar(MouseEvent event) throws SQLException {
        Estudante estudante = new Estudante();

        estudante.setCodigo(Integer.parseInt(codigoText.getText()));
        estudante.setNome(nomeText.getText());
        estudante.setTeste1(Double.parseDouble(test1Text.getText()));
        estudante.setTeste2(Double.parseDouble(teste2Text.getText()));

        tableView.getItems().set(editingIndex, estudante);
        ControllerEstudante.actualizarEstudante(estudante);
        codigoText.setText("");
        nomeText.setText("");
        test1Text.setText("");
        teste2Text.setText("");
        editingIndex = 0;


    }

    @FXML
    void adicionar(MouseEvent event) throws SQLException, ClassNotFoundException {
        Estudante estudante = new Estudante();
        estudante.setCodigo(Integer.parseInt(codigoText.getText()));
        estudante.setNome(nomeText.getText());
        estudante.setTeste1(Double.parseDouble(test1Text.getText()));
        estudante.setTeste2(Double.parseDouble(teste2Text.getText()));
//        contas.add(estudante);
        ControllerEstudante.adicionarEstudante(estudante);
        tableView.getItems().clear();
        tableView.getItems().addAll(ControllerEstudante.listarEstudantes());
        tableView.getItems().add(estudante);
        codigoText.setText("");
        nomeText.setText("");
        test1Text.setText("");
        teste2Text.setText("");

    }

    @FXML
    void cancelar(MouseEvent event) {
        codigoText.setText("");
        nomeText.setText("");
        test1Text.setText("");
        teste2Text.setText("");
        editingIndex = 0;

    }

    @FXML
    void listar(MouseEvent event) throws SQLException, ClassNotFoundException {
        tableView.getItems().clear();
        tableView.getColumns().clear();
        tableView.getItems().clear();

        TableColumn<Estudante, String> column1 = new TableColumn("CODIGO");
        TableColumn<Estudante, String> column2 = new TableColumn("NOME");
        TableColumn<Estudante, String> column3 = new TableColumn("TESTE1");
        TableColumn<Estudante, String> column4 = new TableColumn("TESTE2");
        TableColumn<Estudante, String> column5 = new TableColumn("MEDIA");

        column1.setCellValueFactory(new PropertyValueFactory<>("codigo"));
        column2.setCellValueFactory(new PropertyValueFactory<>("nome"));
        column3.setCellValueFactory(new PropertyValueFactory<>("teste1"));
        column4.setCellValueFactory(new PropertyValueFactory<>("teste2"));
        column5.setCellValueFactory(new PropertyValueFactory<>("media"));

        tableView.getColumns().addAll(column1, column2, column3, column4, column5);

        ArrayList<Estudante> arr = ControllerEstudante.listarEstudantes();

        for (Estudante es: arr) {
            tableView.getItems().add(es);
        }
//        tableView.getItems().addAll();
        tableView.refresh();
        codigoText.setText("");
        nomeText.setText("");
        test1Text.setText("");
        teste2Text.setText("");
        editingIndex = 0;

    }

    @FXML
    void novo(MouseEvent event) {

    }

    @FXML
    void remover(MouseEvent event) throws SQLException {
        tableView.getItems().remove(editingIndex);
        ControllerEstudante.eliminarEstudante(editingIndex);
        tableView.refresh();
        codigoText.setText("");
        nomeText.setText("");
        test1Text.setText("");
        teste2Text.setText("");
        editingIndex = 0;

    }

}