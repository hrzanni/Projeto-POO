package Controler;

import Modelo.Bala;
import Modelo.Cascov;
import Modelo.CogumeloMal;
import Modelo.Personagem;
import Modelo.Hero;
import auxiliar.Posicao;
import java.util.ArrayList;

/**
 * Processa desenho automático de todos os personagens e colisões principais.
 */
public class ControleDeJogo {
    
    public void desenhaTudo(ArrayList<Personagem> lista) {
        for (Personagem p : lista) {
            p.autoDesenho();
        }
    }
    
    public void processaTudo(ArrayList<Personagem> umaFase) {
        if (umaFase.isEmpty()) return;
        
        Hero hero = (Hero) umaFase.get(0);
        ArrayList<Personagem> personagensCopia = new ArrayList<>(umaFase);
        
        for (int i = 1; i < personagensCopia.size(); i++) {
            Personagem p = personagensCopia.get(i);
            
            // Se o herói está na mesma posição de p
            if (hero.getPosicao().igual(p.getPosicao())) {
                // Se p for Casco, aplica dano
                if (p instanceof Cascov) {
                    hero.perderVida(hero.getVida());
                }
                // Se p for Bala, a bala mata o herói imediatamente
                else if (p instanceof Bala) {
                    hero.perderVida(hero.getVida()); // zera vida
                    umaFase.remove(p);
                }
                
                // Se p for Cogumelo do mal, mata o herói imediatamente
                 else if (p instanceof CogumeloMal) {
                    hero.perderVida(hero.getVida()); // zera vida
                }
                
                // Se p for outro personagem transponível e mortal (ex.: moeda), removemos
                else if (p.isbTransponivel()) {
                    if (p.isbMortal()) {
                        umaFase.remove(p);
                    }
                }
            }
        }
    }

    /**
     * Retorna true se a posição p é válida (sem colisão com personagem não-transponível).
     */
    public boolean ehPosicaoValida(ArrayList<Personagem> umaFase, Posicao p) {
        for (int i = 1; i < umaFase.size(); i++) {
            Personagem pIesimo = umaFase.get(i);
            if (!pIesimo.isbTransponivel()) {
                if (pIesimo.getPosicao().igual(p)) {
                    return false;
                }
            }
        }
        return true;
    }
}
