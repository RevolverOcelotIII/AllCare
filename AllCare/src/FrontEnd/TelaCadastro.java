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
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

public class TelaCadastro extends Application {
    
    //Stage
    Stage windowLogin;
    
    //VariavelConection
    ConectionFactory con;
    
    //Variaveis javafx
    Label labelTitle, labelPowered;
    TextField fieldUser;
    PasswordField fieldPass;
    RadioButton radioPatient, radioDoctor;
    ToggleGroup group;
    Button buttonEntrar, buttonCadastrar;
 
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        
        windowLogin = primaryStage;
        
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
        GridPane.setConstraints(fieldUser, 0, 0);
        
        //Field password
        fieldPass = new PasswordField();
        fieldPass.setPromptText("Senha");
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
        scene.setOnKeyPressed(new EventHandler<KeyEvent>(){
            @Override
            public void handle(KeyEvent event) {
                if(event.getCode().equals(KeyCode.ENTER)){
                    eventButtonEntrar();
                }
            }
        });

        //Stage
        windowLogin.getIcons().add(new Image(TelaCadastro.class.getResourceAsStream("AnimeIcon.png")));
        windowLogin.setTitle("All Care");
        windowLogin.setScene(scene);
        windowLogin.setResizable(false);
        windowLogin.show();
    }
    
    public void eventButtonEntrar(){
        if(radioPatient.isSelected() && !fieldUser.getText().isEmpty() && !fieldPass.getText().isEmpty()){
            Paciente patient = new Paciente();
            patient.setId_user(fieldUser.getText());
            patient.setSenha(fieldPass.getText());
            if(Login.Logar(patient, con.getDeclaracao_de_comandos(),con.getResult_consultas())){
                radioPatient.setSelected(true);
                fieldUser.setText("");
                fieldPass.setText("");
                TelaPaciente telaPaciente = new TelaPaciente();
                telaPaciente.display(windowLogin, con);
            }else{
                NovoJOptionPane.display("Usuário ou senha não reconhecidos", "Erro", new Image(TelaCadastro.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Usuário ou senha não reconhecidos", "Erro", 0, new ImageIcon(TelaCadastro.class.getResource("ErrorIcon.png")));
            }
        } else if(radioDoctor.isSelected() && !fieldUser.getText().isEmpty() && !fieldPass.getText().isEmpty()){
            Medico medician = new Medico();
            medician.setId_user(fieldUser.getText());
            medician.setSenha(fieldPass.getText());
            if(Login.Logar(medician, con.getDeclaracao_de_comandos(),con.getResult_consultas())){
                radioPatient.setSelected(true);
                fieldUser.setText("");
                fieldPass.setText("");
                TelaDoutor telaDoutor = new TelaDoutor();
                telaDoutor.display(windowLogin, con);
            }else{
                NovoJOptionPane.display("Usuário ou senha não reconhecidos", "Erro", new Image(TelaCadastro.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Usuário ou senha não reconhecidos", "Erro", 0, new ImageIcon(TelaCadastro.class.getResource("ErrorIcon.png")));
            }
        } else if(fieldUser.getText().isEmpty() || fieldPass.getText().isEmpty()){
            NovoJOptionPane.display("Preencha todos os campos", "Erro", new Image(TelaCadastro.class.getResourceAsStream("ErrorIcon.png")));
            //JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", 0, new ImageIcon(TelaCadastro.class.getResource("ErrorIcon.png")));
        }
    }
    
    public void eventButtonCadastrar(){
        String errorDuplicate = "";
                
        if(radioPatient.isSelected() && !fieldUser.getText().isEmpty() && !fieldPass.getText().isEmpty()){
            Paciente patient = new Paciente();
            patient.setId_user(fieldUser.getText());
            patient.setSenha(fieldPass.getText());
            errorDuplicate = Login.Cadastro(patient, con.getDeclaracao_de_comandos());
            if(errorDuplicate.isEmpty()){               
                NovoJOptionPane.display("Cadastro efetuado", "Sucesso", new Image(TelaCadastro.class.getResourceAsStream("AnimeIconForJOptionPane.png")));
                //JOptionPane.showMessageDialog(null, "Cadastro efetuado", "Sucesso", 0, new ImageIcon(TelaCadastro.class.getResource("AnimeIconForJOptionPane.png")));
            }else{
                NovoJOptionPane.display("Nome de usuário já utilizado", "Erro", new Image(TelaCadastro.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Nome de usuário já utilizado", "Erro", 0, new ImageIcon(TelaCadastro.class.getResource("ErrorIcon.png")));
            }
        }
        if(radioDoctor.isSelected() && !fieldUser.getText().isEmpty() && !fieldPass.getText().isEmpty()){
            Medico medician = new Medico();
            medician.setId_user(fieldUser.getText());
            medician.setSenha(fieldPass.getText());
            errorDuplicate = Login.Cadastro(medician, con.getDeclaracao_de_comandos());
            if(errorDuplicate.isEmpty()){
                NovoJOptionPane.display("Cadastro efetuado", "Sucesso", new Image(TelaCadastro.class.getResourceAsStream("AnimeIconForJOptionPane.png")));
                //JOptionPane.showMessageDialog(null, "Cadastro efetuado", "Sucesso", 0, new ImageIcon(TelaCadastro.class.getResource("AnimeIconForJOptionPane.png")));
            }else{
                NovoJOptionPane.display("Nome de usuário já utilizado", "Erro", new Image(TelaCadastro.class.getResourceAsStream("ErrorIcon.png")));
                //JOptionPane.showMessageDialog(null, "Nome de usuário já utilizado", "Erro", 0, new ImageIcon(TelaCadastro.class.getResource("ErrorIcon.png")));
            }
        }
        
        if(fieldUser.getText().isEmpty() || fieldPass.getText().isEmpty()){
            NovoJOptionPane.display("Preencha todos os campos", "Erro", new Image(TelaCadastro.class.getResourceAsStream("ErrorIcon.png")));
            //JOptionPane.showMessageDialog(null, "Preencha todos os campos", "Erro", 0, new ImageIcon(TelaCadastro.class.getResource("ErrorIcon.png")));
        }
        
        fieldUser.setText("");
        fieldPass.setText("");
    }
}
