/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allcare;

import java.util.ArrayList;

/**
 *
 * @author danpg
 */
public class AllCare {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String concatenados="";
        
        ArrayList <String> arrst = new ArrayList<>();
        for(int i=0;i<=2;i++){
            arrst.add(String.valueOf(i));
        }
        for(String str : arrst){
            concatenados+= str + " or ";
        }
        int y = concatenados.lastIndexOf("or");
        
        concatenados = concatenados.substring(0, y-1);
        concatenados+= ";";
        
        System.out.println(concatenados);
    }
    
}
