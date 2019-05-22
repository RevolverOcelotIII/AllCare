package FrontEnd;

import DataBase.ConectionFactory;
import DataBase.Login;
import allcare.Medico;
import allcare.Paciente;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TelaCadastro extends Application {
    
    //VariavelConection
    ConectionFactory con;
    
    //Variaveis javafx
    Label labelTitle, labelPowered;
    TextField fieldUser;
    PasswordField fieldPass;
    RadioButton radioPatient, radioDoctor;
    ToggleGroup group;
    Button buttonEntrar, buttonCadastrar;
    //ImageAnimeIcon.png");
    Image AnimeIcon;
    ImageIcon AnimeIcone = new ImageIcon("AnimeIcon.png");
    
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        //DatabaseConection
        con = new ConectionFactory();
        if(con.conectar()){
            System.out.println("A conexão foi estabelecida com success");
        }else{
            System.out.println("A conexão não foi estabelecida :/");
        }
        //
        
        GridPane layoutCenter = new GridPane();
        layoutCenter.setAlignment(Pos.CENTER);
        layoutCenter.setVgap(10);
        layoutCenter.setHgap(-73);
        
        BorderPane layoutMain = new BorderPane();
        layoutMain.setCenter(layoutCenter);
        layoutMain.setId("pane");
        
        //Label title
        labelTitle = new Label("");
        layoutMain.setTop(labelTitle);
        labelTitle.setFont(Font.font("Lucida Console", 50));
        
        //Label powered
        labelPowered = new Label("Powered by Webnamoradores™");
        layoutMain.setBottom(labelPowered);
        labelPowered.setStyle("-fx-text-fill: #000000");
        BorderPane.setAlignment(labelPowered, Pos.BOTTOM_RIGHT);
        BorderPane.setMargin(labelPowered, new Insets(0, 3, 2, 0));
        
        //Field username
        fieldUser = new TextField();
        fieldUser.setPromptText("Usuário");
        fieldUser.setFocusTraversable(false);
        GridPane.setConstraints(fieldUser, 0, 0);
        
        //Field password
        fieldPass = new PasswordField();
        fieldPass.setPromptText("Senha");
        fieldPass.setFocusTraversable(false);
        GridPane.setConstraints(fieldPass, 0, 1);
        
        //Toggle group
        group = new ToggleGroup();
        
        //Radio patient
        radioPatient = new RadioButton("Paciente");
        radioPatient.setSelected(true);
        radioPatient.setStyle("-fx-text-fill: #000000");
        GridPane.setConstraints(radioPatient, 0, 2);
        radioPatient.setToggleGroup(group);
        
        //Radio doctor
        radioDoctor = new RadioButton("Doutor(a)");
        radioDoctor.setSelected(false);
        radioDoctor.setStyle("-fx-text-fill: #000000");
        GridPane.setConstraints(radioDoctor, 1, 2);
        radioDoctor.setToggleGroup(group);
        
        //Layout for buttonEntrar and ButtonCadastrar
        HBox layoutLogSign = new HBox();
        layoutLogSign.setSpacing(40);
        GridPane.setConstraints(layoutLogSign, 0, 3);

        //ButtonEntrar
        buttonEntrar = new Button("Entrar");
        buttonEntrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonEntrar();
            }
        });
        
        //ButtonCadastrar
        buttonCadastrar = new Button("Cadastrar");
        buttonCadastrar.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                eventButtonCadastrar();
            }
        });
        //Adicionando componentes
        layoutLogSign.getChildren().addAll(buttonEntrar, buttonCadastrar);
        
        //Adicionando componentes
        layoutCenter.getChildren().addAll(fieldUser, fieldPass, radioPatient, radioDoctor, layoutLogSign);
        
        //Scene
        Scene scene = new Scene(layoutMain, 280, 380);
        scene.getStylesheets().addAll(this.getClass().getResource("StyleLogin.css").toExternalForm());

        //Stage
        primaryStage.getIcons().add(AnimeIcon);
        primaryStage.setTitle("All Care");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }
    
    public void eventButtonEntrar(){
        if(radioPatient.isSelected()){
            Paciente patient = new Paciente();
            patient.setId_user(fieldUser.getText());
            patient.setSenha(fieldPass.getText());
            if(Login.Logar(patient, con.getDeclaracao_de_comandos(),con.getResult_consultas())){
                JOptionPane.showMessageDialog(null, "Login efetuado com sucesso", "Sucesso", 0,AnimeIcone);
            }else{
                JOptionPane.showMessageDialog(null, "Usuário ou senha não reconhecidos", "Fracasso", JOptionPane.ERROR_MESSAGE);
            }
        }else{
            Medico medician = new Medico();
            medician.setId_user(fieldUser.getText());
            medician.setSenha(fieldPass.getText());
            if(Login.Logar(medician, con.getDeclaracao_de_comandos(),con.getResult_consultas())){
                JOptionPane.showMessageDialog(null, "Login Efetuado");
            }else{
                JOptionPane.showMessageDialog(null, "Usuário ou senha não reconhecidos");
            }
        }
    }
    
    public void eventButtonCadastrar(){
        if(radioPatient.isSelected()){
            Paciente patient = new Paciente();
            patient.setId_user(fieldUser.getText());
            patient.setSenha(fieldPass.getText());
            Login.Cadastro(patient, con.getDeclaracao_de_comandos());
            JOptionPane.showMessageDialog(null, "Cadastro efetuado");
        }else{
            Medico medician = new Medico();
            medician.setId_user(fieldUser.getText());
            medician.setSenha(fieldPass.getText());
            Login.Cadastro(medician, con.getDeclaracao_de_comandos());
            JOptionPane.showMessageDialog(null, "Cadastro efetuado");
        }
    }
    
}
