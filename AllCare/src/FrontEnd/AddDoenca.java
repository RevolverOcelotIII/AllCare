package FrontEnd;

import DataBase.Banco_de_Valores;
import DataBase.ConectionFactory;
import allcare.Doenca;
import allcare.Sintoma;
import java.util.ArrayList;
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

public class AddDoenca {
    
    private static TextField fieldDoenca;
    private static Button buttonAddSintomas;
    private static Button buttonDescricao;
    private static Button buttonFinalizar;
    private static ArrayList<Sintoma> sintomas_escolhidos;
    private static String descricao;
    
    public static void display(ConectionFactory con){
        descricao = "";
        
        sintomas_escolhidos = new ArrayList<>();
        
        //Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        //FieldDoença
        fieldDoenca = new TextField();
        fieldDoenca.setPromptText("Nome");
        
        //ButtonAddSintomas
        buttonAddSintomas = new Button("Sintomas");
        buttonAddSintomas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonSintomas(con);
            }
        });
        
        //ButtonAddDescrição
        buttonDescricao = new Button("Descrição");
        buttonDescricao.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonDescricao(con);
            }
        });
        
        //ButtonFinalizar
        buttonFinalizar = new Button("Finalizar");
        buttonFinalizar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonFinalizar(con, window);
            }
        });
        
        //Layout components
        HBox layoutMain = new HBox();
        layoutMain.setPrefHeight(30);
        layoutMain.getChildren().addAll(fieldDoenca, buttonDescricao, buttonAddSintomas, buttonFinalizar);
        layoutMain.setSpacing(5);
        layoutMain.setAlignment(Pos.CENTER);
                
        //Scene
        Scene cena = new Scene(layoutMain);
        
        //Stage
        window.getIcons().add(new Image(AddDoenca.class.getResourceAsStream("AnimeIcon.png")));
        window.setTitle("Adicionar Doença");
        window.setScene(cena);
        window.setResizable(false);
        window.showAndWait();
    }
    
    public static void eventButtonSintomas(ConectionFactory con){
        sintomas_escolhidos = AddSintomasToDoencas.display(con, sintomas_escolhidos);
    }
    
    public static void eventButtonDescricao(ConectionFactory con){
        descricao = AdicionarDescricao.display(con, descricao);
    }
    
    public static void eventButtonFinalizar(ConectionFactory con, Stage window){
        //Ver se a doenca já não existe no banco
        boolean existe = false;
        for(Doenca disease : Banco_de_Valores.getAllDoencas(con.getDeclaracao_de_comandos(), con.getResult_consultas())){
            if(fieldDoenca.getText().equalsIgnoreCase(disease.getNome())){
                existe = true;
            }
        }
        
        if(!existe){
            if(!fieldDoenca.getText().isEmpty() && !sintomas_escolhidos.isEmpty() && !descricao.isEmpty()){
                Doenca doenca = new Doenca();
                doenca.setNome(fieldDoenca.getText());
                doenca.setDescricao(descricao);
                Banco_de_Valores.empurra_Doenca(con.getDeclaracao_de_comandos(), doenca, sintomas_escolhidos, con.getResult_consultas());
                window.close();
            }else{
                NovoJOptionPane.display("Nome ou sintomas não inseridos", "Erro", new Image(AddDoenca.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Nome ou sintomas não inseridos", "Erro", 0, new ImageIcon(AddDoenca.class.getResource("ErrorIcon.png")));
            }
        }else{
            NovoJOptionPane.display("Doença já existente na base de dados", "Erro", new Image(AddDoenca.class.getResourceAsStream("ErrorIcon.png")));
            //JOptionPane.showMessageDialog(null, "Doença já existente na base de dados", "Erro", 0, new ImageIcon(AddDoenca.class.getResource("ErrorIcon.png")));
        }    
    }
    
}
