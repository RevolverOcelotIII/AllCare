package FrontEnd;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class NovoJOptionPane {
    
    private static ImageView image;
    private static Label text;
    private static Button buttonOK;
    
    public static void display(String message, String title, Image icon){
        //Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        //ImageView
        image = new ImageView(icon);
        
        //Text
        text = new Label(message);
        text.setStyle("-fx-text-fill: #000000");
        
        //ButtonOK
        buttonOK = new Button("OK");
        buttonOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonOK(window);
            }
        });
        
        //Layout
        HBox layout = new HBox();
        layout.setSpacing(10);
        layout.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(image, text, buttonOK);
        
        //Scene
        Scene cena = new Scene(layout);
        cena.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    window.close();
                }
            }
        });
        
        //Stage
        window.getIcons().add(new Image(NovoJOptionPane.class.getResourceAsStream("AnimeIcon.png")));
        window.setTitle(title);
        window.setResizable(false);
        window.setScene(cena);
        window.showAndWait();
    }
    
    public static void eventButtonOK(Stage window){
        window.close();
    }
}
