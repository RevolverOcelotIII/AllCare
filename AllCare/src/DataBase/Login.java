/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import allcare.Medico;
import allcare.Usuario;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author danpg
 */
public class Login {
    
    public static void Cadastro(Usuario user, Statement declarador){
        if(user instanceof Medico){
            try {
                declarador.executeUpdate("Insert into Medico(id_user,senha) values ("+user.getId_user()+","+user.getSenha()+");");
            } catch (SQLException e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
        if(user instanceof Medico){
            try {
                declarador.executeUpdate("Insert into Paciente(id_user,senha) values ("+user.getId_user()+","+user.getSenha()+");");
            } catch (SQLException e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
    }
}
