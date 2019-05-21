/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import allcare.Medico;
import allcare.Paciente;
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
        if(user instanceof Paciente){
            try {
                declarador.executeUpdate("Insert into Paciente(id_user,senha) values ("+user.getId_user()+","+user.getSenha()+");");
            } catch (SQLException e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
    }
    
    public static boolean Logar(Usuario user, Statement declarador){
        boolean verificador_de_login = false;
        if(user instanceof Medico){
            try {
                verificador_de_login=declarador.execute("Select id_user from Medico where id_user="+user.getId_user()+" and senha="+user.getSenha()+";"); 
            } catch (Exception e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
        if(user instanceof Paciente){
            try {
                verificador_de_login=declarador.execute("Select id_user from Paciente where id_user="+user.getId_user()+" and senha="+user.getSenha()+";"); 
            } catch (Exception e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
        return verificador_de_login;
    }
}
