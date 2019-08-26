package userInterface;

import com.jfoenix.controls.JFXButton;
import customExceptions.MultiplicationNotSupportedException;
import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.AnchorPane ;
import javafx.event.ActionEvent;
import javafx.scene.layout.HBox;
import model.Board;
import com.jfoenix.controls.JFXTabPane;
import threads.TapChecker;

public class AllianceSpyController implements Initializable {

    private Board board;

    @FXML private JFXTabPane tapAplication;

    @FXML private AnchorPane  pUserMatrix;

    @FXML private JFXTextField tfRows_1;

    @FXML private JFXTextField tfColumn_1;

    @FXML private JFXTextField tfRows_2;

    @FXML private JFXTextField tfColumn_2;

    @FXML private AnchorPane  pFillMatrix;

    @FXML private AnchorPane  pGeneratedMatrix;

    @FXML private AnchorPane  pShowMatrix;

    @FXML private Tab tabFillingForm;

    @FXML private JFXButton btDiscovery;

    private int[][] matrixResult;


    /**
     *Metodo que inicializa atributos y/o relaciones de la clase.
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        TapChecker tapChecker = new TapChecker(tapAplication, tabFillingForm);
        tapChecker.setDaemon(true);
        tapChecker.start();

        board = new Board();
    }



    /**
     *Limpia la interfaz para crear una nueva pareja de matrices.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void clearClicked(ActionEvent event) {
        if (!board.getMatrixList().isEmpty()){
            board.getMatrixList().clear();
        }
        if (!pUserMatrix.getChildren().isEmpty()){
            pUserMatrix.getChildren().clear();
        }
        tfRows_1.setText("");
        tfColumn_1.setText("");
        tfRows_2.setText("");
        tfColumn_2.setText("");
    }

    /**
     *Comienz el proceso de creacion de las matrices, con el numero de filas y columnas ingresadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void createMatrixClicked(ActionEvent event) {
        if (!board.getMatrixList().isEmpty()){
            board.getMatrixList().clear();
        }
        if (!pUserMatrix.getChildren().isEmpty()){
            pUserMatrix.getChildren().clear();
        }

        try {
            board.createMatrix(Integer.parseInt(tfRows_1.getText()), Integer.parseInt(tfColumn_1.getText()));
            board.createMatrix(Integer.parseInt(tfRows_2.getText()), Integer.parseInt(tfColumn_2.getText()));

            paintMatrix(pUserMatrix);

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Causado por:" + "\n" + "Caracteres invalidos en los tamanos de las matrices", ButtonType.CLOSE);
            alert.setHeaderText("Error");
            alert.show();
        }
    }

    /**
     *Inicializa el proceso de hablitar la interfaz donde se crearan los coeficientes de dichas matrices.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void nextClicked(ActionEvent event) {
        if (board.getMatrixList().isEmpty()){
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Para ir a la interfaz de llenado de matrices, tienes que crearlas primero", ButtonType.OK);
            alert.setHeaderText("Informacion");
            alert.show();

        }else {
            tapAplication.getSelectionModel().select(1);
            paintMatrix(pFillMatrix);
        }
    }



    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de Strassen, con las matrices creadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void normalMultiplicationUserClicked(ActionEvent event) {
        try {
            printMatrixResult(board.multiplyMatrix(Board.CLASSICAL_MULTIPLICATION),pShowMatrix);
            tapAplication.getSelectionModel().select(3);
        } catch (MultiplicationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("Advertencia");
            alert.show();
        }

        eliminateMatrixFromList();

        if (!pFillMatrix.getChildren().isEmpty()){
            pFillMatrix.getChildren().clear();
        }
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de combinacion lineal, con las matrices creadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void linearCombinationUserClicked(ActionEvent event) {
        try {
            printMatrixResult(board.multiplyMatrix(Board.MULTIPLICATION_LINEAR_COMBINATION),pShowMatrix);
            tapAplication.getSelectionModel().select(3);
        } catch (MultiplicationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("Advertencia");
            alert.show();
        }

        eliminateMatrixFromList();

        if (!pFillMatrix.getChildren().isEmpty()){
            pFillMatrix.getChildren().clear();
        }
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de bloques, con las matrices creadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void blockMultiplicationUserClicked(ActionEvent event) {
        try {
            printMatrixResult(board.multiplyMatrix(Board.MULTIPLICATION_BY_BLOCKS),pShowMatrix);
            tapAplication.getSelectionModel().select(3);
        } catch (MultiplicationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("Advertencia");
            alert.show();
        }

        eliminateMatrixFromList();

        if (!pFillMatrix.getChildren().isEmpty()){
            pFillMatrix.getChildren().clear();
        }
    }

    /**
     *Permite inicializar el proceso de crear los coeficientes de las matrices, establecidas por el usuario, sin repeticion, llamando a los metodos indicados.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void differentClicked(ActionEvent event) {
        ArrayList<int[][]> matrixList = board.getMatrixList();

        for (int i = 0; i < matrixList.size(); i++) {
            int row = matrixList.get(i).length;
            int column = matrixList.get(i)[0].length;

            board.createRandomMatrix(false, row, column, matrixList.get(i), row*column);
        }
        if (!pFillMatrix.getChildren().isEmpty()){
            pFillMatrix.getChildren().clear();
        }
        paintMatrix(pFillMatrix);
    }

    /**
     *Permite inicializar el procesode crear los coeficientes de las matrices, establecidas por el usuario, con repeticion, llamando a los metodos correspondientes.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void repeatedClcicked(ActionEvent event) {
        ArrayList<int[][]> matrixList = board.getMatrixList();

        for (int i = 0; i < matrixList.size(); i++) {
            int row = matrixList.get(i).length;
            int column = matrixList.get(i)[0].length;

            board.createRandomMatrix(true, row, column, matrixList.get(i), row*column);
        }
        if (!pFillMatrix.getChildren().isEmpty()){
            pFillMatrix.getChildren().clear();
        }
        paintMatrix(pFillMatrix);
    }



    /**
     *Permite inicializar el proceso de crear un numero aleatorio de matrices, con coeficientes tambien aleatorios, para su correspondiente multiplicacion.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void generateClicked(ActionEvent event) {

        /**
        ArrayList<int[][]> matrixList = board.getMatrixList();

        for (int i = 0; i < matrixList.size(); i++) {
            int row = matrixList.get(i).length;
            int column = matrixList.get(i)[0].length;

            board.createRandomMatrix(false, row, column, matrixList.get(i), row*column);
        }
        if (!pFillMatrix.getChildren().isEmpty()){
            pFillMatrix.getChildren().clear();
        }

         **/

        paintMatrix(pFillMatrix);


    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de Strassen, con las matrices generadas aleatoriamente.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void normalMultiplicationClicked(ActionEvent event) {
        try {
            printMatrixResult(board.multiplyMatrix(Board.CLASSICAL_MULTIPLICATION),pShowMatrix);
            tapAplication.getSelectionModel().select(3);
        } catch (MultiplicationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("Advertencia");
            alert.show();
        }

        eliminateMatrixFromList();
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de combinacion lineal, con las matrices generadas aleatoriamente.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void linearCombinationClicked(ActionEvent event) {
        try {
            printMatrixResult(board.multiplyMatrix(Board.MULTIPLICATION_LINEAR_COMBINATION),pShowMatrix);
            tapAplication.getSelectionModel().select(3);
        } catch (MultiplicationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("Advertencia");
            alert.show();
        }

        eliminateMatrixFromList();
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de bloques, con las matrices generadas aleatoriamente.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void blockMultiplicationClicked(ActionEvent event) {
        try {
            printMatrixResult(board.multiplyMatrix(Board.MULTIPLICATION_BY_BLOCKS),pShowMatrix);
            tapAplication.getSelectionModel().select(3);
        } catch (MultiplicationNotSupportedException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING, e.getMessage(), ButtonType.OK);
            alert.setHeaderText("Advertencia");
            alert.show();
        }

        eliminateMatrixFromList();
    }



    /**
     *Permite eliminar las matrices de la lista de matrices, una ves se hallan multiplicado.
     */
    private void eliminateMatrixFromList(){
        board.getMatrixList().clear();
    }


    /**
     * Pinta las matrices que se encuentren en la lista de matrices, en la clase Board.
     * @param anchorPane - Tablero donde se pintaran las matrices de la lista de matrices.
     */
    private void paintMatrix(AnchorPane  anchorPane){
        ArrayList<int[][]> matrixList = board.getMatrixList();
        HBox hBox = new HBox();
        hBox.setSpacing(10);

        for (int i = 0; i < matrixList.size(); i++) {
            GridPane grid = new GridPane();
            grid.setAlignment(Pos.CENTER);

            for (int j = 0; j < matrixList.get(i).length; j++) {
                for (int k = 0; k < matrixList.get(i)[j].length; k++) {
                    Button button = new Button("" + matrixList.get(i)[j][k]);
                    button.setPrefWidth(40);
                    button.setPrefHeight(20);

                    grid.add(button, k, j);
                }
            }
            hBox.getChildren().add(grid);
            hBox.setLayoutY(50);
            hBox.setLayoutX(20);
        }
        anchorPane.getChildren().add(hBox);
    }

    /**
     * Pinta la matriz que resulta de la multiplicación de las matrices A y B.
     * @param mResult - La matriz resultante.
     * @param anchorPane - El escenario donde se pinta la matriz.
     */
    private void printMatrixResult( int [][] mResult, AnchorPane anchorPane){

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);


            for (int j = 0; j < mResult.length; j++) {
                for (int k = 0; k < mResult.length; k++) {
                    Button button = new Button("" + mResult[j][k]);
                    button.setPrefWidth(60);
                    button.setPrefHeight(20);

                    grid.add(button, k, j);
                }
            }
            hBox.getChildren().add(grid);
            hBox.setLayoutY(50);
            hBox.setLayoutX(20);

        anchorPane.getChildren().add(hBox);

        matrixResult = new int[mResult.length][mResult[0].length];
        matrixResult = mResult;
    }


    /**
     * Descubre las posiciones de las naves enemigas.
     * @param event - La acción de presionar el botón.
     */
    @FXML
    public void DiscoveryClicked(ActionEvent event){

        HBox hBox = new HBox();
        hBox.setSpacing(10);

        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);

        for (int i = 0; i < matrixResult.length; i++) {
            for (int j = 0; j < matrixResult[0].length; j++) {
                if (board.verifyIsPrimeNumber(matrixResult[i][j])){

                    Button button = new Button();
                    button.setText("-==-");
                    button.setPrefWidth(60);
                    button.setPrefHeight(20);

                    grid.add(button, j, i);
                }else{
                    Button button = new Button();
                    button.setPrefWidth(60);
                    button.setPrefHeight(20);

                    grid.add(button, j, i);

                }
            }
        }

        hBox.getChildren().add(grid);
        hBox.setLayoutY(50);
        hBox.setLayoutX(20);

        pShowMatrix.getChildren().add(hBox);

    }






}
