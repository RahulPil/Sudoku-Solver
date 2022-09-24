package com.example.sudoku;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;

public class SudokuSolverApplication extends Application {

    TextField[][] textFields = new TextField[9][9];

    @Override
    public void start(Stage stage) throws IOException {

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        for(int row = 0; row < 9; row++){
            for(int column = 0; column < 9; column++){
                TextField t = new TextField();
                t.setAlignment(Pos.CENTER);
                textFields[column][row] = t;
                grid.add(t, column, row);
            }
        }

        Button clearButton = new Button("Clear");
        HBox hBox = new HBox(10);
        hBox.getChildren().add(clearButton);
        hBox.setAlignment(Pos.BOTTOM_LEFT);
        grid.add(hBox, 0, 9, 3, 1);

        clearButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                for(int row = 0; row < 9; row++){
                    for(int column = 0; column < 9; column++){
                        textFields[column][row].setText("");
                        textFields[column][row].setStyle("-fx-background-color: rgb(255, 255, 255)");
                    }
                }
            }
        });

        Button solveButton = new Button("Solve");
        hBox = new HBox(10);
        hBox.getChildren().add(solveButton);
        hBox.setAlignment(Pos.BOTTOM_RIGHT);
        grid.add(hBox, 6, 9, 3, 1);

        solveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {

                Solver s = new Solver();
                Board partial = new Board();
                for(int row = 0; row < 9; row++){
                    for(int column = 0; column < 9; column++){
                        try{
                            Integer i = Integer.valueOf(textFields[column][row].getText().trim());
                            partial.set(column, row, i);
                        }catch(NumberFormatException e){}
                    }
                }
                Board solution = s.solve(partial);

                for(int row = 0; row < 9; row++){
                    for(int column = 0; column < 9; column++) {
                        if(textFields[column][row].getText().trim().equals("")){
                            if(solution == null){
                                textFields[column][row].setStyle("-fx-background-color: rgb(255, 204, 204)");
                            }else{
                                textFields[column][row].setStyle("-fx-background-color: rgb(204, 255, 204)");
                                textFields[column][row].setText(""+solution.get(column, row));
                            }
                        }else{
                            textFields[column][row].setStyle("-fx-background-color: rgb(255, 255, 255)");
                        }
                    }
                }
            }
        });

        for(int r = 0; r < 9; r++) {
            for (int c = 0; c < 9; c++) {
                int column = c;
                int row = r;
                textFields[c][r].textProperty().addListener(new ChangeListener<String>() {
                    @Override
                    public void changed(ObservableValue<? extends String> observable, String oldValue,
                                        String newValue) {
                        if (!newValue.matches("\\d*")) {
                            textFields[column][row].setText(newValue.replaceAll("[^\\d]", ""));
                        }
                    }
                });
            }
        }

        Scene scene = new Scene(grid, 400, 400);
        stage.setTitle("Sudoku Solver!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}