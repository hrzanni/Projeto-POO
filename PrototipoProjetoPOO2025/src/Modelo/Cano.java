package Modelo;

import java.io.Serializable;

public class Cano extends Personagem implements Serializable{
    
    public Cano(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
        bMortal = false;
    }

    public void autoDesenho() {
        super.autoDesenho();

    }    
}

