/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataBase;

import allcare.Doenca;
import allcare.Sintoma;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

/**
 *
 * @author danpg
 */
public class Banco_de_Valores {
    
    public static void empurra_Doenca(Statement declarador,Doenca doenca, ArrayList <Sintoma> sintomas,ResultSet rv){
        try {
            declarador.executeUpdate("Insert into Doenca(nome,descricao) values('"+doenca.getNome()+"','"+doenca.getDescricao()+"');");
            rv = declarador.executeQuery("Select cod_doenca from Doenca where (nome =='"+doenca.getNome()+"')");
            doenca.setCod_doenca(rv.getInt("cod_doenca"));
            for(Sintoma sint : sintomas){
                rv = declarador.executeQuery("Select cod_sintoma from Sintoma where (nome =='"+sint.getNome()+"')");
                sint.setCod_sintoma(rv.getInt("cod_sintoma"));
                declarador.executeUpdate("Insert into Doenca_x_Sintoma(cod_doenca,cod_sintoma) values ('"+doenca.getCod_doenca()+"','"+sint.getCod_sintoma()+"')");
            }
        } catch (Exception e) {
            System.out.println("Deu problema no banco");
        }
    }
    
    public static void empurra_Sintoma(Statement declarador, Sintoma sintoma){
        try {
            declarador.executeUpdate("Insert into Sintoma(nome) values('"+sintoma.getNome()+"');");
        } catch (Exception e) {
            System.out.println("Deu problema no banco");
        }
    }
    
    public static void adiciona_Sintoma_na_Doenca(Statement declarador,int cod_doenca,int cod_sintoma){
        try {
            declarador.executeUpdate("Insert into Doenca_x_Sintoma(cod_doenca,cod_sintoma) values ('"+cod_doenca+"','"+cod_sintoma+"')");
        } catch (Exception e) {
            System.out.println("Deu problema no banco");
        }
    }
    
    public static ArrayList<Doenca> procura_Doencas(ArrayList<Sintoma> sintomas,Statement declarador,ResultSet rv){
        ArrayList<Integer> DoencasExcluidas = new ArrayList();
        try {
            for(Sintoma sint : sintomas){
                rv = declarador.executeQuery("Select cod_doenca from Doenca_x_Sintoma where cod_sintoma != '"+sint.getCod_sintoma()+"'");
            }
            DoencasExcluidas.add(rv.getInt("cod_doenca"));
            
        } catch (Exception e) {
        }
    }
}
