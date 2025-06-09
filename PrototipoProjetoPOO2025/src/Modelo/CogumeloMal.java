package Modelo;

import java.io.Serializable;

public class CogumeloMal extends Moeda implements Serializable {

    public CogumeloMal(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bMortal = true;
    }

    public void efeitoColeta(Hero hero) {
        if (hero != null && hero.estaVivo()) {
            hero.perderVida(hero.getVida());
        }
    }
}
