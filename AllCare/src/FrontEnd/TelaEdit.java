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

public class TelaEdit {
    
    private static TextField fieldNomeDoenca;
    private static Button buttonSintomas;
    private static Button buttonDescricao;
    private static Button buttonFinalizar;
    private static ArrayList<Sintoma> sintomas_escolhidos;
    private static String descricao;
    
    public static void display(ConectionFactory con, String doencaSelecionada){
        sintomas_escolhidos = new ArrayList<>();
        ArrayList<String> sintomasPreEstabelecidos = Banco_de_Valores.sintomas_de_doenca_especifica(con.getDeclaracao_de_comandos(), con.getResult_consultas(), doencaSelecionada);
        
        for(String nome : sintomasPreEstabelecidos){
            Sintoma sintoma = new Sintoma();
            sintoma.setNome(nome);
            
            sintomas_escolhidos.add(sintoma);
        }
        
        //Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        //FieldNomeDoença
        fieldNomeDoenca = new TextField();
        fieldNomeDoenca.setPromptText("Nome");
        fieldNomeDoenca.setText(doencaSelecionada);
        
        //ButtonMudarSintomas
        buttonSintomas = new Button("Sintomas");
        buttonSintomas.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonSintomas(con, doencaSelecionada);
            }
        });
        
        //ButtonDescricao
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
                eventButtonFinalizar(con, window, doencaSelecionada);
            }
        });
        
        //Layout components
        HBox layoutMain = new HBox();
        layoutMain.setPrefHeight(30);
        layoutMain.getChildren().addAll(fieldNomeDoenca, buttonDescricao, buttonSintomas, buttonFinalizar);
        layoutMain.setSpacing(5);
        layoutMain.setAlignment(Pos.CENTER);
                
        //Scene
        Scene cena = new Scene(layoutMain);
        
        //Stage
        window.getIcons().add(new Image(TelaEdit.class.getResourceAsStream("AnimeIcon.png")));
        window.setTitle("Editar Doença");
        window.setScene(cena);
        window.setResizable(false);
        window.showAndWait();
    }
    
    public static void eventButtonSintomas(ConectionFactory con, String doencaSelecionada){
        sintomas_escolhidos = MudarSintomasToDoencas.display(con, doencaSelecionada);
    }
    
    public static void eventButtonDescricao(ConectionFactory con){
        descricao = AdicionarDescricao.display(con, descricao);
    }
    
    public static void eventButtonFinalizar(ConectionFactory con, Stage window, String doencaSelecionada){
        //Ver se a doenca já não existe no banco
        boolean existe = false;
        for(Doenca disease : Banco_de_Valores.getAllDoencas(con.getDeclaracao_de_comandos(), con.getResult_consultas())){
            if(fieldNomeDoenca.getText().equalsIgnoreCase(disease.getNome())){
                existe = true;
            }
        }
        if(fieldNomeDoenca.getText().equalsIgnoreCase(doencaSelecionada)){
            existe = false;
        }
        
        if(!existe){
            if(!fieldNomeDoenca.getText().isEmpty() && !sintomas_escolhidos.isEmpty() && !descricao.isEmpty()){
                Banco_de_Valores.modifica_Sintomas_e_nome_da_Doenca(con.getDeclaracao_de_comandos(), con.getResult_consultas(), doencaSelecionada, sintomas_escolhidos, fieldNomeDoenca.getText(), descricao);
                window.close();
            }else{
                NovoJOptionPane.display("Nome ou sintomas não inseridos", "Erro", new Image(TelaEdit.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Nome ou sintomas não inseridos", "Erro", 0, new ImageIcon(TelaEdit.class.getResource("ErrorIcon.png")));
            }
        }else{
            NovoJOptionPane.display("Nome já utilizado", "Erro", new Image(TelaEdit.class.getResourceAsStream("ErrorIcon.png")));
            //JOptionPane.showMessageDialog(null, "Nome já utilizado", "Erro", 0, new ImageIcon(TelaEdit.class.getResource("ErrorIcon.png")));
        }    
    }
    
}
