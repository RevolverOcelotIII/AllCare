/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package allcare;

/**
 *
 * @author danpg
 */
public class Doenca {
    private int cod_doenca;
    private String nome;
    private String Descricao;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String Descricao) {
        this.Descricao = Descricao;
    }

    public int getCod_doenca() {
        return cod_doenca;
    }

    public void setCod_doenca(int cod_doenca) {
        this.cod_doenca = cod_doenca;
    }
    
}
