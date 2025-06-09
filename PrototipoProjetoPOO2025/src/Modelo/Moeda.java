package Modelo;

import java.io.Serializable;

public class Moeda extends Personagem implements Serializable {
    private int iContaIntervalos;
    private String nomeImagem;  

    public Moeda(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.nomeImagem = sNomeImagePNG;
        this.bTransponivel = true;
        this.bMortal = false;
        this.iContaIntervalos = 0;
    }

    public String getImagem() {
        return nomeImagem;
    }

    public void autoDesenho() {
        super.autoDesenho();
    }    
}

