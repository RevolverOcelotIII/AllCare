/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import com.mysql.jdbc.Connection;
import com.mysql.jdbc.Statement;
import java.sql.DriverManager;
import java.sql.ResultSet;

/**
 *
 * @author danpg
 */
public class Coneccao {
    private Connection coneccao;
    private Statement declaracao_de_comandos;
    private ResultSet result_consultas;
    
    public boolean conectar(){
        String driver = "com.mysql.jdbc.Driver";
        String user = "root";
        String senha = "123456";
        String servidor = "jdbc:mysql://localhost:3306/allcare";
        
        try {
            Class.forName(driver);
            this.coneccao = (Connection) DriverManager.getConnection(servidor,user,senha);
            this.declaracao_de_comandos = (Statement) this.coneccao.createStatement();
        } catch (Exception e) {
            System.out.println("Não funfou a logagem");
        }
        
        return this.coneccao!=null;
    }
}
