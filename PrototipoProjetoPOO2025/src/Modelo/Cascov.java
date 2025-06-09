package Modelo;

public class Cascov extends Personagem {
    private boolean bUp;
    private int iContador;
    private final int dano;

    public Cascov(String sNomeImagePNG) {
        super(sNomeImagePNG);
        bUp = true;
        iContador = 0;
        this.bTransponivel = false;
        this.bMortal = false;
        this.dano = 1;
    }

    @Override
    public void autoDesenho() {
        if (iContador == 3) {    
            if (bUp) {
                this.setPosicao(pPosicao.getLinha() - 1, pPosicao.getColuna());
            } else {
                this.setPosicao(pPosicao.getLinha() + 1, pPosicao.getColuna());
            }
            bUp = !bUp;
            iContador = 0;         
        } else {
            iContador++;          
        }
        super.autoDesenho();
    }
}
