package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.io.Serializable;


public class CanhaoE extends Personagem implements Serializable {
    private int iContaIntervalos;
    
    public CanhaoE(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = false;
        this.bMortal = false;
        this.iContaIntervalos = 0;
    }

    @Override
    public void autoDesenho() {
        super.autoDesenho();

        this.iContaIntervalos++;
        if (this.iContaIntervalos == Consts.TIMER) {
            this.iContaIntervalos = 0;
            Bala f = new Bala("bombinha.png");
            f.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() - 1);
            Desenho.acessoATelaDoJogo().addPersonagem(f);
        }
    }
}
