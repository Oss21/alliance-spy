package userInterface;

import javafx.fxml.Initializable;
import java.net.URL;
import java.util.ResourceBundle;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.Pane;
import javafx.event.ActionEvent;
import model.Board;
import javafx.scene.control.Tab;
import com.jfoenix.controls.JFXTabPane;
import threads.TapChecker;

public class AllianceSpyController implements Initializable {

    private Board board;

    @FXML private JFXTabPane tapAplication;

    @FXML private Pane pUserMatrix;

    @FXML private JFXTextField tfRows_1;

    @FXML private JFXTextField tfColumn_1;

    @FXML private JFXTextField tfRows_2;

    @FXML private JFXTextField tfColumn_2;

    @FXML private Pane pFillMatrix;

    @FXML private Pane pGeneratedMatrix;

    @FXML private Pane pShowMatrix;

    @FXML private Tab tabFillingForm;



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
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de Strassen, con las matrices creadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void normalMultiplicationUserClicked(ActionEvent event) {
        board.multiplyMatrix(Board.CLASSICAL_MULTIPLICATION);
        tapAplication.getSelectionModel().select(3);
        eliminateMatrixFromList();
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de combinacion lineal, con las matrices creadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void linearCombinationUserClicked(ActionEvent event) {
        board.multiplyMatrix(Board.MULTIPLICATION_LINEAR_COMBINATION);
        tapAplication.getSelectionModel().select(3);
        eliminateMatrixFromList();
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de bloques, con las matrices creadas por el usuario.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void blockMultiplicationUserClicked(ActionEvent event) {
        board.multiplyMatrix(Board.MULTIPLICATION_BY_BLOCKS);
        tapAplication.getSelectionModel().select(3);
        eliminateMatrixFromList();
    }

    /**
     *Inicializa el proceso de creacion de matrices, con las filas y columnas elegidas por el usuario, para hablitar la interfaz donde se crearan los coeficientes de dichas matrices.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void nextClicked(ActionEvent event) {
        if (!board.getMatrixList().isEmpty()){
            board.getMatrixList().clear();
        }

        try {
            board.createMatrix(Integer.parseInt(tfRows_1.getText()), Integer.parseInt(tfColumn_1.getText()));
            board.createMatrix(Integer.parseInt(tfRows_2.getText()), Integer.parseInt(tfColumn_2.getText()));

            tfRows_1.setText("");
            tfColumn_1.setText("");
            tfRows_2.setText("");
            tfColumn_2.setText("");

            tapAplication.getSelectionModel().select(1);

        }catch (NumberFormatException e){
            Alert alert = new Alert(Alert.AlertType.ERROR, "Causado por:" + "\n" + "Letras en los tamanos de las matrices", ButtonType.OK);
            alert.setHeaderText("Error");
            alert.show();
        }
    }

    /**
     *Permite inicializar el proceso de crear los coeficientes de las matrices, establecidas por el usuario, sin repeticion, llamando a los metodos indicados.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void differentClicked(ActionEvent event) {

    }

    /**
     *Permite inicializar el procesode crear los coeficientes de las matrices, establecidas por el usuario, con repeticion, llamando a los metodos correspondientes.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void repeatedClcicked(ActionEvent event) {

    }



    /**
     *Permite inicializar el proceso de crear un numero aleatorio de matrices, con coeficientes tambien aleatorios, para su correspondiente multiplicacion.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void generateClicked(ActionEvent event) {

    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de Strassen, con las matrices generadas aleatoriamente.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void normalMultiplicationClicked(ActionEvent event) {
        board.multiplyMatrix(Board.CLASSICAL_MULTIPLICATION);
        tapAplication.getSelectionModel().select(3);
        eliminateMatrixFromList();
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de combinacion lineal, con las matrices generadas aleatoriamente.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void linearCombinationClicked(ActionEvent event) {
        board.multiplyMatrix(Board.MULTIPLICATION_LINEAR_COMBINATION);
        tapAplication.getSelectionModel().select(3);
        eliminateMatrixFromList();
    }

    /**
     *Se encarga de comenzar el proceso de multiplicacion por el metodo de bloques, con las matrices generadas aleatoriamente.
     * @param event - evento que se genera cuando se pulsa el boton y/o se llama al metodo.
     */
    @FXML
    void blockMultiplicationClicked(ActionEvent event) {
        board.multiplyMatrix(Board.MULTIPLICATION_BY_BLOCKS);
        tapAplication.getSelectionModel().select(3);
        eliminateMatrixFromList();
    }



    /**
     *Permite eliminar las matrices de la lista de matrices, una ves se hallan multiplicado.
     */
    private void eliminateMatrixFromList(){
        board.getMatrixList().clear();
    }
}
