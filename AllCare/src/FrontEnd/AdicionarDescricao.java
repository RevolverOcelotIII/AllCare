package FrontEnd;

import DataBase.ConectionFactory;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AdicionarDescricao {
    
    private static TextField fieldPropagacao;
    private static TextField fieldTratamento;
    private static TextField fieldDuracao;
    private static ComboBox boxDiagnostico;
    private static TextField fieldMedicos;
    private static TextArea areaDescricaoGeral;
    private static Button buttonOK;
    private static String descricao;
    
    public static String display(ConectionFactory con, String descricao2){
        //Stage
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        
        //Field prograpagação
        fieldPropagacao = new TextField();
        fieldPropagacao.setPromptText("Propagação");
        if(!descricao2.isEmpty()){
            int i;
            int z;
            i = descricao2.indexOf("Propagação") + 12;
            z = descricao2.indexOf("Tratamento") - 1;
            fieldPropagacao.setText(descricao2.substring(i, z));
        }
        
        //Field tratamento
        fieldTratamento = new TextField();
        fieldTratamento.setPromptText("Tratamento");
        if(!descricao2.isEmpty()){
            int i;
            int z;
            i = descricao2.indexOf("Tratamento") + 12;
            z = descricao2.indexOf("Duração") - 1;
            fieldTratamento.setText(descricao2.substring(i, z));
        }
        
        //Field duração
        fieldDuracao = new TextField();
        fieldDuracao.setPromptText("Duração");
        if(!descricao2.isEmpty()){
            int i;
            int z;
            i = descricao2.indexOf("Duração") + 9;
            z = descricao2.indexOf("Requer diagnóstico") - 1;
            fieldDuracao.setText(descricao2.substring(i, z));
        }
        
        //Box diagnostico
        boxDiagnostico = new ComboBox();
        boxDiagnostico.setPrefWidth(250);
        boxDiagnostico.getItems().addAll("Sim", "Não");
        boxDiagnostico.setPromptText("Requer diagnóstico?");
        if(!descricao2.isEmpty()){
            int i;
            int z;
            i = descricao2.indexOf("Requer diagnóstico") + 20;
            z = descricao2.indexOf("Médicos") - 1;
            if(descricao2.substring(i, z).equals("Sim")){
                boxDiagnostico.getSelectionModel().select(0);
            }else{
                boxDiagnostico.getSelectionModel().select(1);
            }
        }
        
        //Field medicos
        fieldMedicos = new TextField();
        fieldMedicos.setPromptText("Médicos");
        if(!descricao2.isEmpty()){
            int i;
            int z;
            i = descricao2.indexOf("Médicos(s)") + 12;
            z = descricao2.indexOf("Descrição geral") - 1;
            fieldMedicos.setText(descricao2.substring(i, z));
        }
        
        //Area descrição
        areaDescricaoGeral = new TextArea();
        areaDescricaoGeral.setPrefSize(230, 75);
        areaDescricaoGeral.setFocusTraversable(false);
        areaDescricaoGeral.setWrapText(true);
        areaDescricaoGeral.setPromptText("Descrição geral");
        if(!descricao2.isEmpty()){
            int i;
            int z;
            i = descricao2.indexOf("Descrição geral") + 17;
            areaDescricaoGeral.setText(descricao2.substring(i));
        }
        
        //Button OK
        buttonOK = new Button("Concluir");
        BorderPane.setAlignment(buttonOK, Pos.BOTTOM_RIGHT);
        buttonOK.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonOK(window);
            }
        });
        
        //LayoutDados
        VBox layoutDados = new VBox();
        layoutDados.setAlignment(Pos.CENTER);
        layoutDados.setSpacing(5);
        layoutDados.setPrefWidth(230);
        BorderPane.setMargin(layoutDados, new Insets(2, 2, 2, 2));
        layoutDados.getChildren().addAll(fieldPropagacao, fieldTratamento, fieldDuracao, boxDiagnostico, fieldMedicos, areaDescricaoGeral, buttonOK);
        
        //Layout main
        BorderPane layoutMain = new BorderPane();
        layoutMain.setCenter(layoutDados);
        
        //Scene
        Scene cena = new Scene(layoutMain);
        
        //Stage
        window.getIcons().add(new Image(MostrarDescricaoDoenca.class.getResourceAsStream("AnimeIcon.png")));
        window.setTitle("Descrição");
        window.setScene(cena);
        window.setResizable(false);
        window.showAndWait();
        
        return descricao;
    }

    public static void eventButtonOK(Stage window){
        if(!fieldPropagacao.getText().isEmpty() && !fieldTratamento.getText().isEmpty() 
            && !fieldDuracao.getText().isEmpty() && boxDiagnostico.getSelectionModel().getSelectedIndex() > -1
            && !fieldMedicos.getText().isEmpty() && !areaDescricaoGeral.getText().isEmpty()){
            descricao = "Propagação: " + fieldPropagacao.getText() + "\n" +
                    "Tratamento: " + fieldTratamento.getText() + "\n" +
                    "Duração: " + fieldDuracao.getText() + "\n" +
                    "Requer diagnóstico: " + boxDiagnostico.getSelectionModel().getSelectedItem().toString() + "\n" +
                    "Médicos(s): " + fieldMedicos.getText() + "\n" +
                    "Descrição geral: " + areaDescricaoGeral.getText();
            window.close();
        }else{
            NovoJOptionPane.display("Preencha todos os campos", "Erro", new Image(AdicionarDescricao.class.getResourceAsStream("ErrorIcon.png")));
        }
    }
    
}
