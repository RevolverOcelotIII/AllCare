/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import allcare.Medico;
import allcare.Paciente;
import allcare.Usuario;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author danpg
 */
public class Login {
    
    //Cadastro retornando string para na tela de login ver se não há um user com o mesmo id 
    public static String Cadastro(Usuario user, Statement declarador) {
        if(user instanceof Medico){
            try {
                declarador.executeUpdate("Insert into Medico(id_user,senha) values ('"+user.getId_user()+"','"+user.getSenha()+"');");
            } catch (SQLException e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
                return e.getMessage();
            }
        }
        if(user instanceof Paciente){
            try {
                declarador.executeUpdate("Insert into Paciente(id_user,senha) values ('"+user.getId_user()+"','"+user.getSenha()+"');");
            } catch (SQLException e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
                return e.getMessage();
            }
        }
        return "";
    }
    
    public static boolean Logar(Usuario user, Statement declarador,ResultSet rv){
        if(user instanceof Medico){
            try {
                rv=declarador.executeQuery("Select id_user,senha from Medico where id_user='"+user.getId_user()+"' and senha='"+user.getSenha()+"';");
                if(rv != null && rv.next()){
                    return user.getId_user().equals(rv.getString("id_user"))&& user.getSenha().equals(rv.getString("senha"));
                }
            } catch (Exception e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
        else{
            try {
                rv=declarador.executeQuery("Select id_user,senha from Paciente where id_user='"+user.getId_user()+"' and senha='"+user.getSenha()+"';");
                if(rv != null && rv.next()){
                    return user.getId_user().equals(rv.getString("id_user"))&& user.getSenha().equals(rv.getString("senha"));
                }
            } catch (Exception e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
        }
        return false;
    }
}
