package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import java.io.Serializable;

public class CanhaoD extends Personagem implements Serializable {
    private int iContaIntervalos;

    public CanhaoD(String sNomeImagePNG) {
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
            bala.setPosicao(pPosicao.getLinha(), pPosicao.getColuna() + 1);
            bala.setDirecaoDireita();

            Desenho.acessoATelaDoJogo().addPersonagem(bala);
        }
    }
}
