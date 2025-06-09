package Modelo;

import Auxiliar.Desenho;
import java.io.Serializable;

public class Hero extends Personagem implements Serializable {

    private int vida;
    private boolean invencivel = false;
    private long tempoInvencivelFim = 0; 

    private static final long DURACAO_INVENCIBILIDADE = 1500; 

    public Hero(String sNomeImagePNG) {
        super(sNomeImagePNG);
        this.vida = 1; 
    }

    public void voltaAUltimaPosicao() {
        this.pPosicao.volta();
    }
    
   
    public boolean setPosicao(int linha, int coluna) {
        if (this.pPosicao.setPosicao(linha, coluna)) {
            if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
                this.voltaAUltimaPosicao();
            }
            return true;
        }
        return false;       
    }

    private boolean validaPosicao() {
        if (!Desenho.acessoATelaDoJogo().ehPosicaoValida(this.getPosicao())) {
            this.voltaAUltimaPosicao();
            return false;
        }
        return true;       
    }
    
    @Override
    public boolean moveUp() {
        if (super.moveUp()) {
            return validaPosicao();
        }
        return false;
    }

    @Override
    public boolean moveDown() {
        if (super.moveDown()) {
            return validaPosicao();
        }
        return false;
    }

    @Override
    public boolean moveRight() {
        if (super.moveRight()) {
            return validaPosicao();
        }
        return false;
    }

    @Override
    public boolean moveLeft() {
        if (super.moveLeft()) {
            return validaPosicao();
        }
        return false;
    }

    // ————— Vida e invencibilidade —————

    public int getVida() {
        return vida;
    }

    /**
     * Aplica dano e ativa invencibilidade temporária.
     */
    public void perderVida(int dano) {
        long agora = System.currentTimeMillis();
        if (invencivel) {
            return; // se ainda estiver invencível, não aplica dano
        }
        vida -= dano;
        if (vida < 0) {
            vida = 0;
        }
        System.out.println("Herói sofreu " + dano + " de dano! Vida atual: " + vida);
        invencivel = true;
        tempoInvencivelFim = agora + DURACAO_INVENCIBILIDADE;
    }

    /**
     * Deve ser chamado periodicamente (por exemplo, em ControleDeJogo) para desligar invencibilidade.
     */
    public void atualizarInvencibilidade() {
        if (invencivel && System.currentTimeMillis() > tempoInvencivelFim) {
            invencivel = false;
        }
    }

    public boolean estaInvencivel() {
        return invencivel;
    }

    public boolean estaVivo() {
        return vida > 0;
    }
}
