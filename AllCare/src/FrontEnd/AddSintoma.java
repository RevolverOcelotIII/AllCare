package FrontEnd;

import DataBase.Banco_de_Valores;
import DataBase.ConectionFactory;
import allcare.Sintoma;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class AddSintoma {
    
    private static TextField fieldSintoma;
    private static Button buttonAdicionar;
    
    public static void display(ConectionFactory con){
        //Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        //fieldSintoma
        fieldSintoma = new TextField();
        fieldSintoma.setPromptText("Sintoma");
        
        //buttonAdicionar
        buttonAdicionar = new Button("Adicionar");
        buttonAdicionar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonAdicionar(con, window);
            }
        });
        
        //Layout components
        HBox layoutMain = new HBox();
        layoutMain.getChildren().addAll(fieldSintoma, buttonAdicionar);
        layoutMain.setSpacing(10);
        layoutMain.setAlignment(Pos.CENTER);
                
        //Scene
        Scene cena = new Scene(layoutMain, 230, 30);
        
        //Stage
        window.getIcons().add(new Image(AddSintoma.class.getResourceAsStream("AnimeIcon.png")));
        window.setTitle("Adicionar Sintoma");
        window.setScene(cena);
        window.setResizable(false);
        window.showAndWait();
    }
    
    public static void eventButtonAdicionar(ConectionFactory con, Stage window){
        //Ver se o sintoma já não existe no banco
        boolean existe = false;
        
        for(Sintoma sintomaNoBanco : Banco_de_Valores.puxa_Sintomas(con.getDeclaracao_de_comandos(), con.getResult_consultas())){
            if(fieldSintoma.getText().equalsIgnoreCase(sintomaNoBanco.getNome())){
                existe = true;
            }
        }
        
        if(!existe){
            if(!fieldSintoma.getText().isEmpty()){
                Sintoma sintoma = new Sintoma();
                sintoma.setNome(fieldSintoma.getText());
                Banco_de_Valores.empurra_Sintoma(con.getDeclaracao_de_comandos(), sintoma);
                window.close();
            }else{
                NovoJOptionPane.display("Preencha o campo", "Erro", new Image(AddSintoma.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Preencha o campo", "Erro", 0, new ImageIcon(AddSintoma.class.getResource("ErrorIcon.png")));
            }
        }else{
            NovoJOptionPane.display("Sintoma já existente na base de dados", "Erro", new Image(AddSintoma.class.getResourceAsStream("ErrorIcon.png")));
            //JOptionPane.showMessageDialog(null, "Sintoma já existente na base de dados", "Erro", 0, new ImageIcon(AddSintoma.class.getResource("ErrorIcon.png")));
        }
    }
    
}
