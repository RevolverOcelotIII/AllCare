package FrontEnd;

import DataBase.ConectionFactory;
import allcare.Doenca;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class MostrarDescricaoDoenca {
    
    private static TextArea areaMessage;
    private static Button buttonOK;
    
    public static void display(ConectionFactory con, Doenca doenca){
        //Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        //Area message
        areaMessage = new TextArea(doenca.getDescricao());
        areaMessage.setPrefSize(200, 100);
        areaMessage.setFocusTraversable(false);
        areaMessage.setWrapText(true);
        areaMessage.setEditable(false);
        BorderPane.setMargin(areaMessage, new Insets(5, 5, 5, 5));
        
        //Button OK
        buttonOK = new Button("OK");
        BorderPane.setAlignment(buttonOK, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(buttonOK, new Insets(0, 3, 2, 0));
        buttonOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonOK(window);
            }
        });
        
        //Layout main
        BorderPane layoutMain = new BorderPane();
        layoutMain.setLeft(areaMessage);
        layoutMain.setRight(buttonOK);
        
        //Scene
        Scene cena = new Scene(layoutMain);
        
        //Stage
        window.getIcons().add(new Image(MostrarDescricaoDoenca.class.getResourceAsStream("AnimeIcon.png")));
        window.setTitle("Descrição");
        window.setScene(cena);
        window.setResizable(false);
        window.showAndWait();
    }

    public static void eventButtonOK(Stage window){
        window.close();
    }
    
}
