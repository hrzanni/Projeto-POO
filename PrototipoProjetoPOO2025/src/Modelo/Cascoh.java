package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;


public class Cascoh extends Personagem implements Serializable {

    private boolean bRight;
    int iContador;
    private final int dano;

    public Cascoh(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bRight = true;
        this.iContador = 0;
        this.dano = 1;
    }

    public void autoDesenho() {
        if (iContador == 3) {
            iContador = 0;
            if (bRight) {
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
            } else {
                this.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() - 1);
            }

            bRight = !bRight;
        }
        super.autoDesenho();
        
        Hero hero = Desenho.acessoATelaDoJogo().getFaseAtual().getHero();
        if (hero != null && hero.getPosicao().igual(this.getPosicao())) {
            hero.perderVida(dano);
            
        }
        iContador++;
    }
}
