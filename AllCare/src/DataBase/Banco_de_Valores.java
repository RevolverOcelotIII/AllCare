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
            rv = declarador.executeQuery("Select cod_doenca from Doenca where (nome = '"+doenca.getNome()+"')");
            if(rv != null && rv.next()){
                doenca.setCod_doenca(rv.getInt("cod_doenca"));
            }
            for(Sintoma sint : sintomas){
                rv = declarador.executeQuery("Select cod_sintoma from Sintoma where (nome ='"+sint.getNome()+"')");
                if(rv != null && rv.next()){
                    sint.setCod_sintoma(rv.getInt("cod_sintoma"));
                }
                declarador.executeUpdate("Insert into Doenca_x_Sintoma(cod_doenca,cod_sintoma) values ('"+doenca.getCod_doenca()+"','"+sint.getCod_sintoma()+"')");
            }
        } catch (Exception e) {
            System.out.println("Deu problem in the bank: "+e.getMessage());
        }
    }
    
    public static void empurra_Sintoma(Statement declarador, Sintoma sintoma){
        try {
            declarador.executeUpdate("Insert into Sintoma(nome) values('"+sintoma.getNome()+"');");
        } catch (Exception e) {
            System.out.println("Deu problem in the bank: "+e.getMessage());
        }
    }
    
    public static void adiciona_Sintoma_na_Doenca(Statement declarador,int cod_doenca,int cod_sintoma){
        try {
            declarador.executeUpdate("Insert into Doenca_x_Sintoma(cod_doenca,cod_sintoma) values ('"+cod_doenca+"','"+cod_sintoma+"')");
        } catch (Exception e) {
            System.out.println("Deu problem in the bank: "+e.getMessage());
        }
    }
    
    public static void modifica_Sintomas_e_nome_da_Doenca(Statement declarador, ResultSet rv, String nomeDoenca, ArrayList<Sintoma> sintomas, String novoNome, String descricao){
        try {
            //Conseguindo id da doenca
            rv = declarador.executeQuery("SELECT cod_doenca FROM doenca WHERE nome = '"+ nomeDoenca +"'");
            
            int idDoenca = -1;
            
            if(rv != null && rv.next()){
                idDoenca = rv.getInt("cod_doenca");
            }
            
            //Deletando relações antigas
            declarador.executeUpdate("DELETE FROM doenca_x_sintoma WHERE cod_doenca = '"+ idDoenca +"'");
            //Mudando nome
            declarador.executeUpdate("UPDATE doenca SET nome = '"+ novoNome +"' WHERE cod_doenca = '"+ idDoenca +"'");
            declarador.executeUpdate("UPDATE doenca SET descricao = '"+ descricao +"' WHERE cod_doenca = '"+ idDoenca +"'");
            
            //Estabelecendo novas relações
            for(Sintoma sint : sintomas){
                rv = declarador.executeQuery("Select cod_sintoma from Sintoma where (nome ='"+sint.getNome()+"')");
                if(rv != null && rv.next()){
                    sint.setCod_sintoma(rv.getInt("cod_sintoma"));
                }
                declarador.executeUpdate("Insert into Doenca_x_Sintoma(cod_doenca,cod_sintoma) values ('"+idDoenca+"','"+sint.getCod_sintoma()+"')");
            }
        } catch (Exception e) {
            System.out.println("Deu problem in the bank: "+e.getMessage());
        }
    }
    
    public static ArrayList<Sintoma> puxa_Sintomas(Statement declarador, ResultSet retorno){
        ArrayList<Sintoma> sintomas = new ArrayList<>();
        try {
            retorno = declarador.executeQuery("Select * from Sintoma");
            while(retorno != null && retorno.next()){
                Sintoma sint = new Sintoma();
                sint.setCod_sintoma(retorno.getInt("cod_sintoma"));
                sint.setNome(retorno.getString("nome"));
                sintomas.add(sint);
            }
        } catch (Exception e) {
            System.out.println("Deu problem in the bank: "+e.getMessage());
        }
        return sintomas;
    }
    
    public static ArrayList<Doenca> procura_Doencas(ArrayList<Sintoma> sintomas, Statement declarador, ResultSet rv){
        ArrayList<Integer> Cod_Doencas = new ArrayList<>();
        ArrayList<Doenca> Doencas = new ArrayList<>();
        String codSintomas = "", codInDoencas = "";
        int y;
        
        try{         
            for(Sintoma sint : sintomas){
            codSintomas += "cod_doenca in (select cod_doenca from doenca_x_sintoma where cod_sintoma="+sint.getCod_sintoma()+") and ";
            }

            int i = codSintomas.lastIndexOf("and ");
            codSintomas = codSintomas.substring(0,i-1);

            codSintomas+=";";

            try{
                rv = declarador.executeQuery("select cod_doenca from doenca_x_sintoma where "+codSintomas);
                while(rv.next()){
                Cod_Doencas.add(rv.getInt("cod_doenca"));
                }
            }catch(Exception e){
                System.out.println("Deu problem in the bank here: "+e.getMessage());
            }

            for(int cod_doenca : Cod_Doencas){
                codInDoencas += "'"+cod_doenca+"',";
            }
            y = codInDoencas.lastIndexOf(",");
            codInDoencas = codInDoencas.substring(0, y);
            try {
                rv = declarador.executeQuery("select Doenca.* from Doenca where cod_doenca in ("+codInDoencas+")");
            } catch (Exception e) {
                System.out.println("Deu problem in the bank: "+e.getMessage()); 
            }
            try {
                while(rv != null && rv.next()){
                    Doenca doenca = new Doenca();
                    doenca.setNome(rv.getString("Nome"));
                    doenca.setCod_doenca(rv.getInt("Cod_Doenca"));
                    doenca.setDescricao(rv.getString("Descricao"));
                    Doencas.add(doenca);
                }
            } catch (Exception e) {
                System.out.println("Deu problem in the bank: "+e.getMessage());
            }
            
            //Filtra pegando apenas aquelas que possuem todos os sintomas
            boolean verificador;
            try{
                for(Doenca doencaX : Doencas){
                    for(Sintoma sintomaX : sintomas){
                        verificador = false;
                        rv = declarador.executeQuery("SELECT cod_doenca FROM doenca_x_sintoma WHERE cod_sintoma = '"+sintomaX.getCod_sintoma()+"'");
                        while(rv.next()){
                            if(doencaX.getCod_doenca() == rv.getInt("cod_doenca")){
                                verificador = true;
                            }
                        }
                        if(verificador == false){
                        Doencas.remove(doencaX);
                        }
                    }
                }
             }catch(Exception e){
                 System.out.println("Deu problem in the bank, pesquisa sem resultados: "+e.getMessage());
             }
        }catch(StringIndexOutOfBoundsException e){
            System.out.println("Deu problem in the bank, pesquisa sem resultados: "+e.getMessage());
        }catch(ArrayIndexOutOfBoundsException e){
            System.out.println("Deu problem in the bank, pesquisa sem resultados: "+e.getMessage());
        }catch(IndexOutOfBoundsException e){
            System.out.println("Deu problem in the bank, pesquisa sem resultados: "+e.getMessage());
        }
        return Doencas;
    }
    
    public static ArrayList<Doenca> getAllDoencas(Statement declarador, ResultSet rv){
        ArrayList<Doenca> doencas = new ArrayList<>();
        try {
            rv = declarador.executeQuery("select Doenca.* from Doenca");
        } catch (Exception e) {
            System.out.println("Erro ao pegar doenças: "+e.getMessage()); 
        }
        
        try {
            while(rv != null && rv.next()){
                Doenca doenca = new Doenca();
                doenca.setNome(rv.getString("nome"));
                doenca.setDescricao(rv.getString("descricao"));
                doencas.add(doenca);
            }
        } catch (Exception e) {
            System.out.println("Erro ao pegar doenças: "+e.getMessage());
        }
        
        return doencas;
    }
    
    public static ArrayList<String> sintomas_de_doenca_especifica(Statement declarador, ResultSet rv, String doencaSelecionada){
        ArrayList<String> sintomas = new ArrayList<>();
        
        try {
            rv = declarador.executeQuery("SELECT sintoma.nome FROM sintoma, doenca, doenca_x_sintoma WHERE sintoma.cod_sintoma = doenca_x_sintoma.cod_sintoma AND "
                                        + "doenca_x_sintoma.cod_doenca = doenca.cod_doenca AND doenca.nome = '" + doencaSelecionada + "'");
        } catch (Exception e) {
            System.out.println("Erro ao pegar doenças: "+e.getMessage()); 
        }
        
        try {
            while(rv != null && rv.next()){
                sintomas.add(rv.getString("nome"));
            }
        } catch (Exception e) {
            System.out.println("Erro ao pegar doenças: "+e.getMessage());
        }
        
        return sintomas;
    }
    
    public static void deletarDoenca(Statement declarador, ResultSet rv, String doenca){
        try{
            rv = declarador.executeQuery("SELECT cod_doenca FROM doenca WHERE nome = '"+ doenca +"'");
            
            int cod_doenca = -1;
            if(rv != null && rv.next()){
            cod_doenca = rv.getInt("cod_doenca");
            }
            
            declarador.executeUpdate("DELETE FROM doenca_x_sintoma WHERE cod_doenca = '"+ cod_doenca +"'");
            declarador.executeUpdate("DELETE FROM doenca WHERE cod_doenca = '"+ cod_doenca +"'");
        } catch (Exception e) {
            System.out.println("Deu problem in the bank: "+e.getMessage());
        }
    }
    
}
