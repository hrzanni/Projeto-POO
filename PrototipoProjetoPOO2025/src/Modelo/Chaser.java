package Modelo;

import Controler.Tela;
import Auxiliar.Desenho;
import Controler.Fase;
import auxiliar.Posicao;
import java.io.Serializable;
import java.util.ArrayList;

public class Chaser extends Personagem implements Serializable {

    private boolean iDirectionV;
    private boolean iDirectionH;
    private int canMove = 0;  // Contador para controlar a velocidade
    private int moveDelay = 10; // O número de quadros para esperar antes de mover novamente (ajuste esse valor para controlar a velocidade)
    private Tela tela = Desenho.acessoATelaDoJogo();

    public Chaser(String sNomeImagePNG) {
        super(sNomeImagePNG); // Passa o nome da imagem para o construtor de Personagem
        iDirectionV = true;
        iDirectionH = true;
        this.bTransponivel = false;  // O Chaser não pode atravessar outros personagens
    }

    // Método para desenhar o Chaser no jogo
    public void autoDesenho() {
        Fase fase = tela.getFaseAtual();  // Obtem a fase atual

        // Verifica se o Chaser está na mesma posição que o herói
        if (this.getPosicao().igual(fase.getHero().getPosicao())) {
            // O Chaser matou o herói
            fase.getHero().perderVida(fase.getHero().getVida());
        }

        // Lógica para perseguir o herói
        Hero hero = fase.getHero();
        Posicao heroPos = hero.getPosicao();

        // Incrementa o contador de movimento
        canMove++;

        // Só move o Chaser a cada moveDelay quadros
        if (canMove >= moveDelay) {
            // Move o Chaser na direção do herói, verificando se o movimento é válido
            if (this.getPosicao().getLinha() < heroPos.getLinha()) {
                if (validarMovimento(this.getPosicao().getLinha() + 1, this.getPosicao().getColuna())) {
                    this.moveDown();  // Move para baixo se o herói estiver abaixo
                }
            } else if (this.getPosicao().getLinha() > heroPos.getLinha()) {
                if (validarMovimento(this.getPosicao().getLinha() - 1, this.getPosicao().getColuna())) {
                    this.moveUp();  // Move para cima se o herói estiver acima
                }
            }

            if (this.getPosicao().getColuna() < heroPos.getColuna()) {
                if (validarMovimento(this.getPosicao().getLinha(), this.getPosicao().getColuna() + 1)) {
                    this.moveRight();  // Move para a direita se o herói estiver à direita
                }
            } else if (this.getPosicao().getColuna() > heroPos.getColuna()) {
                if (validarMovimento(this.getPosicao().getLinha(), this.getPosicao().getColuna() - 1)) {
                    this.moveLeft();  // Move para a esquerda se o herói estiver à esquerda
                }
            }

            // Reinicia o contador
            canMove = 0;
        }

        // Atualiza a posição do Chaser
        super.autoDesenho();
    }

    // Método para validar se o Chaser pode se mover para a posição desejada
    private boolean validarMovimento(int linha, int coluna) {
        Fase fase = tela.getFaseAtual();
        ArrayList<Personagem> personagens = fase.getPersonagens();

        // Verifica se a posição está ocupada por um personagem não transponível
        for (Personagem p : personagens) {
            if (!p.isbTransponivel() && p.getPosicao().igual(new Posicao(linha, coluna))) {
                return false;  // A posição é inválida (bloqueada por um obstáculo)
            }
        }

        // A posição é válida para o movimento
        return true;
    }

    // Implementação do movimento (se for necessário personalizar o movimento)
    @Override
    public boolean moveUp() {
        return super.moveUp();
    }

    @Override
    public boolean moveDown() {
        return super.moveDown();
    }

    @Override
    public boolean moveRight() {
        return super.moveRight();
    }

    @Override
    public boolean moveLeft() {
        return super.moveLeft();
    }
}