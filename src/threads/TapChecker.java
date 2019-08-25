package threads;

import com.jfoenix.controls.JFXTabPane;
import javafx.scene.control.Tab;

public class TapChecker extends Thread {

    private JFXTabPane tapAplication;
    private Tab tabFillingForm;

    public TapChecker(JFXTabPane tapAplication, Tab tabFillingForm) {
        this.tapAplication = tapAplication;
        this.tabFillingForm = tabFillingForm;
    }

    @Override
    public void run() {

        while (true){
            if (tapAplication.getSelectionModel().isSelected(0)){
                tabFillingForm.setDisable(true);

            }else if (tapAplication.getSelectionModel().isSelected(2)){
                tabFillingForm.setDisable(true);

            }else if (tapAplication.getSelectionModel().isSelected(3)){
                tabFillingForm.setDisable(true);

            }else {
                tabFillingForm.setDisable(false);
            }

            try {
                sleep(250);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
