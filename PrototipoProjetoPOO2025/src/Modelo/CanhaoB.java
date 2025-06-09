package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.io.Serializable;

public class CanhaoB extends Personagem implements Serializable {
    private int iContaIntervalos;

    public CanhaoB(String sNomeImagePNG) {
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

            Bala bala = new Bala("bombinha.png");
            bala.setDirecaoBaixo();
            bala.setPosicao(pPosicao.getLinha() + 1, pPosicao.getColuna());
            Desenho.acessoATelaDoJogo().addPersonagem(bala);
        }
    }
}
