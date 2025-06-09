package Modelo;

import java.io.Serializable;

/**
 * Bala que se move para esquerda, direita ou para baixo, é mortal e transponível.
 */
public class Bala extends Personagem implements Serializable {

    public enum Direcao { ESQUERDA, DIREITA, BAIXO }

    private Direcao direcao;

    public Bala(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.bTransponivel = true;
        this.bMortal = true;
        this.direcao = Direcao.ESQUERDA; // padrão: esquerda
    }

    public void setDirecaoDireita() {
        this.direcao = Direcao.DIREITA;
    }

    public void setDirecaoEsquerda() {
        this.direcao = Direcao.ESQUERDA;
    }

    public void setDirecaoBaixo() {
        this.direcao = Direcao.BAIXO;
    }

    @Override
    public void autoDesenho() {
        super.autoDesenho();

        int linha = pPosicao.getLinha();
        int coluna = pPosicao.getColuna();

        switch (direcao) {
            case DIREITA:
                this.setPosicao(linha, coluna + 1);
                break;
            case ESQUERDA:
                this.setPosicao(linha, coluna - 1);
                break;
            case BAIXO:
                this.setPosicao(linha + 1, coluna);
                break;
        }
    }
}
