package Controler;

import Modelo.CogumeloMal;
import Modelo.Bala;
import Modelo.CanhaoB;
import Modelo.CanhaoD;
import Modelo.CanhaoE;
import Modelo.Cascov;
import Modelo.Moeda;
import Modelo.Cano;
import Modelo.CascaDeBanana;
import Modelo.Cascoh;
import Modelo.Chaser;
import Modelo.Hero;
import Modelo.Personagem;
import Modelo.PortalCerto;
import Modelo.PortalQueMata;
import Modelo.PortalVoltaInicio;
import Modelo.cascaDeBananaEsquerda;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;import javax.swing.JOptionPane;
;


public class Fase implements Serializable{
    
    private static final long serialVersionUID = 1L;
    private final ArrayList<Bala> bombinhas;
    private final ArrayList<Personagem> personagens;
    private final ArrayList<Moeda> moedas;
    private final Hero hero;
    private final int pontoSaidaLinha;
    private final int pontoSaidaColuna;
    private boolean chaveColetada = false;


    public Fase(Hero hero, int pontoSaidaLinha, int pontoSaidaColuna) {
        this.personagens = new ArrayList<>();
        this.moedas = new ArrayList<>();
        this.bombinhas = new ArrayList<>();        
        this.hero = hero;
        this.pontoSaidaLinha = pontoSaidaLinha;
        this.pontoSaidaColuna = pontoSaidaColuna;
        this.personagens.add(hero);
    }
    
    public void addPersonagem(Personagem p) {
        personagens.add(p);
    }

    public void removePersonagem(Personagem p) {
        personagens.remove(p);
    }

    public ArrayList<Personagem> getPersonagens() {
        return personagens;
    }
    
    public boolean isEmpty() {
        return personagens.isEmpty();
    }
    
    public void clear() {
        personagens.clear();
    }
    
    public Hero getHero() {
        return hero;
    }
    
    public void addMoeda(Moeda moeda) {
        moedas.add(moeda);
        personagens.add(moeda);
    }
    
    public void removeMoeda(Moeda moeda) {
        moedas.remove(moeda);
        personagens.remove(moeda);
    }
    
    public boolean moedasColetadas() {
        return moedas.isEmpty() ||
            (moedas.size() == 1 && moedas.get(0).getImagem().equals("Robbo.png"));
    }
    
    public void checarColetaMoedas() {
    ArrayList<Moeda> moedasParaRemover = new ArrayList<>();
    int heroLinha = hero.getPosicao().getLinha();
    int heroColuna = hero.getPosicao().getColuna();

    for (Moeda moeda : moedas) {
        // Verifica se o herói está na mesma posição de uma moeda
        if (!moeda.getImagem().equals("Robbo.png") &&
            moeda.getPosicao().getLinha() == heroLinha &&
            moeda.getPosicao().getColuna() == heroColuna) {

            // Se for a chave, marca como coletada
            if (moeda.getImagem().equals("chave.png")) {
                chaveColetada = true;
                moedasParaRemover.add(moeda);
                continue;
            }

            // Se for a princesa, permite coletar apenas se a chave foi coletada
            if (moeda.getImagem().equals("princesa2.png") && chaveColetada) {
                moedasParaRemover.add(moeda);
            } else if (moeda.getImagem().equals("princesa2.png")) {
                // Se a princesa ainda não foi liberada, exibe uma mensagem
                JOptionPane.showMessageDialog(null, "Você precisa coletar a chave primeiro!");
            } else {
                moedasParaRemover.add(moeda);
            }
        }
    }
    for (Moeda moeda : moedasParaRemover) {
        removeMoeda(moeda);
    }
}
    

    
    public boolean checarTrocaDeFase() {
        int linhaAtual = hero.getPosicao().getLinha();
        int colunaAtual = hero.getPosicao().getColuna();
        return linhaAtual == pontoSaidaLinha && colunaAtual == pontoSaidaColuna;
    }
    
    public static Fase criarFase1() {
        Hero hero = new Hero("mario.png");
        hero.getPosicao().setPosicao(1, 1);
        Fase fase1 = new Fase(hero, 27, 14);
        
        Moeda moeda1 = new Moeda("yoshi.png");
        moeda1.getPosicao().setPosicao(27, 14);
        fase1.addMoeda(moeda1);
        
    
        int[][] posCanhaoE = {
           {4, 5},
        };
        for (int[] pos : posCanhaoE) {
            CanhaoE canhao = new CanhaoE("canhaoe.png");
            canhao.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addPersonagem(canhao);
        }


        int[][] posCanhaoD = {
            {8, 1},
            {2, 5},
            {27, 1},
        };
        for (int[] pos : posCanhaoD) {
            CanhaoD canhao = new CanhaoD("canhaod.png");
            canhao.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addPersonagem(canhao);
        }


        int[][] posCanhaoB = {
            {1, 9}
        };
        for (int[] pos : posCanhaoB) {
            CanhaoB canhao = new CanhaoB("canhaoB.png");
            canhao.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addPersonagem(canhao);
        }

        Cascoh cascoz = new Cascoh("casco.png");
        cascoz.getPosicao().setPosicao(5, 1);
        fase1.addPersonagem(cascoz);
        
        try {
            cascoz.saveAsZip("casco.zip");
            System.out.println("Salvo casco.zip com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int[][] posCascoh = {
            {15, 2},{12, 1},{13, 11},{15, 12},{18, 2},{21, 1},
        };
        for (int[] pos : posCascoh) {
            Cascoh casco = new Cascoh("casco.png");
            casco.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addPersonagem(casco);
        }

        int[][] posCascov = {
            {27, 6},{15, 11},{17, 4},{14, 13}, {26, 8},
        };
        for (int[] pos : posCascov) {
            Cascov casco = new Cascov("casco.png");
            casco.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addPersonagem(casco);
        }

        int[][] posMoedas = {
            {7, 2},{14, 12},{20, 9},{25, 11},{1, 14},                
        };
        for (int[] pos : posMoedas) {
            Moeda moeda = new Moeda("moeda.png");
            moeda.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addMoeda(moeda);
        }

       
        // Paredes externas (bordas)
        for (int i = 0; i < 16; i++) {
            Cano topo = new Cano("Cano.png");
            topo.getPosicao().setPosicao(0, i);
            fase1.addPersonagem(topo);

            Cano inferior = new Cano("Cano.png");
            inferior.getPosicao().setPosicao(28, i);
            fase1.addPersonagem(inferior);
        }
        for (int i = 0; i < 29; i++) {
            Cano esquerda = new Cano("Cano.png");
            esquerda.getPosicao().setPosicao(i, 0);
            fase1.addPersonagem(esquerda);

            Cano direita = new Cano("Cano.png");
            direita.getPosicao().setPosicao(i, 15);
            fase1.addPersonagem(direita);
        }

        // Paredes internas (corredores)
        int[][] paredes = {
            {1,3},{1, 13},{2, 3},{2, 4},{3, 5},{3, 11},{3, 12},{3, 13},{3, 14},
            {4, 6},{4, 10},{5, 3},{5, 4},{5, 5},{6, 3},{6, 8},{7 , 1},{7, 3},{7, 7}, 
            {8, 6},{9, 1},{9, 3},{9, 4},{10, 4},{9, 5},{11, 4},{11, 10},{11, 11},
            {11, 12},{11, 13},{11, 14},{12, 4},{12, 3},{12, 9},{13, 4},{13, 9},
            {14, 4},{15, 1}, {15, 4}, {15, 9},{16, 4}, {16, 9},{17, 4},{17, 10},
            {17, 11},{17, 12},{17, 13},{17, 14},{18, 1},{18, 4},{19, 4},{20, 4},
            {21, 3}, {21, 4},{22, 4},{23, 4},{24, 4},{24, 5},{24, 6},{24, 7},{24, 8},
            {24, 9},{24, 10},{24, 11},{24, 12},{25, 6},{25, 10},{25, 12},{26, 1},
            {26, 3},{26, 12},{27, 8},{27, 12},
                
        };
        for (int[] pos : paredes) {
            Cano c = new Cano("Cano.png");
            c.getPosicao().setPosicao(pos[0], pos[1]);
            fase1.addPersonagem(c);
        }

        return fase1;
    }
    
    public static Fase criarFase2() {
        Hero hero = new Hero("mario&yoshi.png");
        hero.getPosicao().setPosicao(27, 1);
        Fase fase2 = new Fase(hero, 1, 14);
        
        Moeda moeda1 = new Moeda("Toad.png");
        moeda1.getPosicao().setPosicao(1, 14);
        fase2.addMoeda(moeda1);
        
        // Moedas comuns espalhadas
        int[][] posCogumelos = {
            {20, 10},
            {18, 13},
            {10,7},
            {10,13},
            {2,2}
        };
        for (int[] pos : posCogumelos) {
            Moeda cogu = new Moeda("cogumelo.png");
            cogu.getPosicao().setPosicao(pos[0], pos[1]);
            fase2.addMoeda(cogu);        
        }
        
        // Cogumelo que mata
        int[][] posCogumelosMal = {
           {23, 14},
            {17, 3},
            {14,11},
            {8,1},
            {11,13},
            {9,13},
            {2,5} 
        };
        for (int[] pos : posCogumelosMal) {
            CogumeloMal coguMal = new CogumeloMal("cogumeloRoxo.png");
            coguMal.getPosicao().setPosicao(pos[0], pos[1]);
            fase2.addPersonagem(coguMal);        
        }
        //Cascos posições
        int[][] posCascaTugaHorizontal = {
            {25, 13},
            {23, 12},
            {21, 13},
            {24, 2},
            {16,9},
            {12,1},
            {6,1},
            {4,1},
            {2,7}
        };
        for (int[] pos : posCascaTugaHorizontal) {
            Cascoh casco = new Cascoh("casco.png");
            casco.getPosicao().setPosicao(pos[0], pos[1]);
            fase2.addPersonagem(casco);        
        }
       
        // 7) Paredes externas
        for (int i = 0; i < 16; i++) {
            Cano topo = new Cano("cogumeloparede.png");
            topo.getPosicao().setPosicao(0, i);
            fase2.addPersonagem(topo);

            Cano inferior = new Cano("cogumeloparede.png");
            inferior.getPosicao().setPosicao(28, i);
            fase2.addPersonagem(inferior);
        }
        for (int i = 0; i < 29; i++) {
            Cano esquerda = new Cano("cogumeloparede.png");
            esquerda.getPosicao().setPosicao(i, 0);
            fase2.addPersonagem(esquerda);

            Cano direita = new Cano("cogumeloparede.png");
            direita.getPosicao().setPosicao(i, 15);
            fase2.addPersonagem(direita);
        }
        
        //8) Paredes internas (corredores)
       int[][] paredes = {
            {26,2},{26,3},{26,4},{25,4},{24,4},{24,5},
            {24,6},{24,7},{26,6},{26,7},{27,8},{24,9},
            {25,9},{23,7},{22,7},{23,7},{22,9},
            {23,10},{21,11},{22,11},{24,11},{25,11},
            {20,11},{19,11},{26,10},{26,12},{19,11},
            {23,11},{21,7},{20,8},{27,14},{26,14},
            {19, 10},{20, 12},{20, 14},{24,1},{24,1},
            {23,3},{18,11},{17,11},{16,11},{16,12},
            {16,13},{16,14},{22,4},{21,5},{23,5},{16,14},
            {21,2},{20,2},{19,3},{18,4},{15,1},{16,2},
            {18,7},{18,9},{17,6},{16,5},{15,4},{14,3},
            {13,2},{18,9},{12,3},{11,2},{11,3},{11,4},
            {11,5},{11,6},{10,8},{9,7},{11,7},
            {9,2},{9,3},{9,4},{9,5},{9,6},{13,5},{15,5},
            {12,10},{11,9},{13,14},{12,13},{15,12},
            {11,12},{9,3},{16,8},{16,7},{13,8},{14,7},{9,3},
            {2,14},{3,14},{9,12},{8,13},{7,14},{9,3},
            {7,10},{5,11},{6,11},{5,13},{4,13},{3,12},{2,11},
            {3,10},{4,10},{7,6},{7,5},{6,4},{6,3},{8,2},
            {4,3},{4,4},{4,5},{4,6},{5,7},{4,8},{3,9},
            {1,5},{2,6},
        
     
        };
        
        for (int[] pos : paredes) {
            Cano c = new Cano("cogumeloparede.png");
            c.getPosicao().setPosicao(pos[0], pos[1]);
            fase2.addPersonagem(c);
        }
        
    return fase2;

    }    
    public static Fase criarFase3() {
        Hero hero = new Hero("mario&toad.png");
        hero.getPosicao().setPosicao(1, 7);
        Fase fase3 = new Fase(hero, 27, 1);
        
        //Moeda especial que funciona como portal
        Moeda moeda1 = new Moeda("Donkeykong.png");
        moeda1.getPosicao().setPosicao(27, 1);
        fase3.addMoeda(moeda1);
        
        CascaDeBanana cascaBanana = new CascaDeBanana("cascaDeBanana.png", hero);
        cascaBanana.getPosicao().setPosicao(2, 7);
        fase3.addPersonagem(cascaBanana);
        try {
            cascaBanana.saveAsZip("cascaDeBanana.zip");
            System.out.println("Salvo cascaBanana.zip com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        int[][] posCascasBananas = {
            
            {2, 2},
            {18, 1},
            {5, 14}
            
        };
        for (int[] pos : posCascasBananas) {
            CascaDeBanana cascaBanana2 = new CascaDeBanana("cascaDeBanana.png", hero);
            cascaBanana2.getPosicao().setPosicao(pos[0], pos[1]);
            fase3.addPersonagem(cascaBanana2);        
        }
                
        
        // Moedas comuns espalhadas
        int[][] posBananas = {
            {1, 1},
            {1, 14},
            {10,14},
            {13,5},
            {12,7},
            {24,6},
            
        };
        for (int[] pos : posBananas) {
            Moeda banana = new Moeda("Banana.png");
            banana.getPosicao().setPosicao(pos[0], pos[1]);
            fase3.addMoeda(banana);        
        }
        
        
        //Cogumelo do mal
        int[][] posCogumelosMal = {
            {4, 3},
            {13,4},
            {11,13},
            {20,14},
            {20,2},
            {22,1},
            {24,2},
            {26,1},
            {6,7}  
        };
        for (int[] pos : posCogumelosMal) {
            CogumeloMal coguMal = new CogumeloMal("cogumeloRoxo.png");
            coguMal.getPosicao().setPosicao(pos[0], pos[1]);
            fase3.addPersonagem(coguMal);        
        }
        
        int[][] posCanhoes = {
            {3, 14},
            {6,5},
        };
        for (int[] pos : posCanhoes) {
            CanhaoE canhao = new CanhaoE("canhaoe.png");
            canhao.getPosicao().setPosicao(pos[0], pos[1]);
            fase3.addPersonagem(canhao);        
        }

        //Cascos posições
        int[][] posCascaTugaHorizontal = {
            {2, 11},
            {2, 2},
            {9,13},
            {12,4},
            {12,11},
            {14,11},
            {15,12},
            {14,8},
            {26,13},
            {26,10},
        };
        for (int[] pos : posCascaTugaHorizontal) {
            Cascoh casco = new Cascoh("casco.png");
            casco.getPosicao().setPosicao(pos[0], pos[1]);
            fase3.addPersonagem(casco);        
        }
        
        // 7) Paredes externas
        for (int i = 0; i < 16; i++) {
            Cano topo = new Cano("Palmeira.png");
            topo.getPosicao().setPosicao(0, i);
            fase3.addPersonagem(topo);

            Cano inferior = new Cano("Palmeira.png");
            inferior.getPosicao().setPosicao(28, i);
            fase3.addPersonagem(inferior);
        }
        for (int i = 0; i < 29; i++) {
            Cano esquerda = new Cano("Palmeira.png");
            esquerda.getPosicao().setPosicao(i, 0);
            fase3.addPersonagem(esquerda);

            Cano direita = new Cano("Palmeira.png");
            direita.getPosicao().setPosicao(i, 15);
            fase3.addPersonagem(direita);
        }
        
        //8) Paredes internas (corredores)
       int[][] paredes = {
           {1,5},{2,5},{1,9},{2,9},{4,5},{5,5},{4,8},
           {2,10},{2,13},{2,14},{2,5},{2,1},{2,4},
           {5,10},{6,10},{7,11},{8,10},{9,11},{10,12},
           {10,13},{11,14},{4,6},{2,13},{8,12},{7,13},
           {4,13},{4,14},{7,9},{2,13},{4,1},{6,12},
           {7,1},{7,2},{8,3},{8,5},{6,6},{7,6},{8,6},
           {8,8},{10,7},{11,7},{12,8},{12,6},{9,5},
           {4,1},{10,4},{11,3},{12,3},{13,3},{14,4},
           {14,5},{13,6},{9,11},{9,1},{14,1},{15,1},
           {16,1},{16,2},{16,4},{15,5},{14,7},{12,9},
           {12,10},{13,11},{14,10},{4,1},{15,11},{16,10},
           {17,11},{18,11},{18,12},{18,13},{19,12},{20,12},
           {21,13},{14,13},{16,14},{17,5},{17,6},{17,7},
           {15,5},{19,4},{20,5},{16,7},{18,9},{19,9},{20,9},
           {21,11},{23,12},{4,1},{24,12},{25,13},{4,1},{26,12},
           {25,6},{25,7},{24,7},{23,7},{23,6},{23,5},{21,4},{21,5},
           {21,6},{21,7},{21,3},{21,8},{21,9},{22,3},{23,3},{24,3},
           {25,3},{26,3},{27,3},{27,4},{27,5},{27,6},{27,7},{27,8},
           {27,9},{25,5},{22,9},{23,9},{24,9},{25,9},{25,10},{25,11},
        
     
        };
        
        for (int[] pos : paredes) {
            Cano c = new Cano("Palmeira.png");
            c.getPosicao().setPosicao(pos[0], pos[1]);
            fase3.addPersonagem(c);
        }
        
        
        return fase3;
    }
    
    public static Fase criarFase4() {
        Hero hero = new Hero("mario&donkey.png");
        hero.getPosicao().setPosicao(27, 14);
        Fase fase4 = new Fase(hero, 1, 1);
        
        Moeda moeda1 = new Moeda("luiggi.png");
        moeda1.getPosicao().setPosicao(1, 1);
        fase4.addMoeda(moeda1);
          
        Chaser chaser = new Chaser("boo.png");
        chaser.getPosicao().setPosicao(27, 1);
        fase4.addPersonagem(chaser);
        
        Chaser ch = new Chaser("boo.png");
        ch.getPosicao().setPosicao(2, 7);
        fase4.addPersonagem(ch);
        try {
            ch.saveAsZip("boo.zip");
            System.out.println("Salvo boo.zip com sucesso!");
        } catch (IOException e) {
            e.printStackTrace();
        }
        
        Chaser chaser2 = new Chaser("boo.png");
        chaser.getPosicao().setPosicao(17, 3);
        fase4.addPersonagem(chaser);

        Chaser chaser3 = new Chaser("boo.png");
        chaser.getPosicao().setPosicao(9, 14);
        fase4.addPersonagem(chaser);
        
        cascaDeBananaEsquerda cascaBanana = new cascaDeBananaEsquerda("cascaDeBanana.png", hero);
        cascaBanana.getPosicao().setPosicao(27, 12);
        fase4.addPersonagem(cascaBanana);
            
        int[][] posMoedas = {
            {25, 5}, {21, 14},{6, 11},{14, 4},
        };
        for (int[] pos : posMoedas) {
            Moeda moeda = new Moeda("moeda.png");
            moeda.getPosicao().setPosicao(pos[0], pos[1]);
            fase4.addMoeda(moeda);
        }
        
        // Paredes externas
        for (int i = 0; i < 16; i++) {
            Cano topo = new Cano("pneu.png");
            topo.getPosicao().setPosicao(0, i);
            fase4.addPersonagem(topo);

            Cano inferior = new Cano("pneu.png");
            inferior.getPosicao().setPosicao(28, i);
            fase4.addPersonagem(inferior);
        }
        for (int i = 0; i < 29; i++) {
            Cano esquerda = new Cano("pneu.png");
            esquerda.getPosicao().setPosicao(i, 0);
            fase4.addPersonagem(esquerda);

            Cano direita = new Cano("pneu.png");
            direita.getPosicao().setPosicao(i, 15);
            fase4.addPersonagem(direita);
        }
        
        int[][] paredes = {
            {26, 9},{26, 1},{26, 2},{26, 3},{26, 4},{26, 5},{26, 6},{26, 9},
            {26, 10},{26, 11},{26, 12},{26, 13}, {26, 14},{25, 9},{24, 9},{23, 9},
            {22, 9},{21, 9},{20, 9},{25, 6},{24, 6},{23, 6},{22, 6},{21, 6},{19, 10},{18, 11},
            {18, 11},{18, 12},{19, 13},{20, 13},{21, 13},{22, 14},{21, 5},{21, 4},{22, 3},
            {23, 4},{23, 5},{25, 4},{25, 3},{25, 2},{25, 1},{24, 2},{24, 1},{23, 1},
            {22, 1},{21, 1},{20, 2},{19, 3},{19, 4},{19, 5},{19, 6},{18, 6},{19, 6},
            {15, 8},{14, 8},{13, 8},{12, 8},{16, 11},{16, 12},{16, 13},{15, 13},
            {14, 12},{13, 12},{12, 12},{11, 11},{8, 14},{15, 2},{15, 3},{15, 4},{8, 1},
            {9, 1},{10, 1},{11, 1},{12, 1},{13, 1},{14, 1},{7, 2},{7, 3},{14, 5},{13, 4},
            {7, 5},{6, 6},{5, 7},{4, 8},{6, 4},{5, 3},{4, 2},{3, 2},{12, 3},{11, 3},
            {2, 1},{4, 5},{3, 4},{2, 4},{1, 4},{3, 6},{2, 6},{1, 6},{2, 10},{2, 11},{2, 12},
            {3, 13},{4, 13},{5, 13},{6, 12},{6, 10},{7, 10},{8, 11},{8, 12},{8, 13},
            {5, 11},{10, 3},{9, 3},{9, 4},
        };
        
        for (int[] pos : paredes) {
            
            Cano c = new Cano("pneu.png");
            c.getPosicao().setPosicao(pos[0], pos[1]);
            fase4.addPersonagem(c);      
        }
        
        return fase4;
     }
     
     
     public static Fase criarFase5() {
        Hero hero = new Hero("mario&luiggi.png");
        hero.getPosicao().setPosicao(1, 1);
        Fase fase5 = new Fase(hero, 27, 1);
        
        
        //Porta de saida da fase
        Moeda moeda1 = new Moeda("bandeirafinal.png");
        moeda1.getPosicao().setPosicao(27, 1);
        fase5.addMoeda(moeda1);
        
        //Princesa
        Moeda princesa = new Moeda("princesa2.png");
        princesa.getPosicao().setPosicao(16, 8);
        fase5.addMoeda(princesa);
        
        //Chave
        Moeda chave = new Moeda("chave.png");
        chave.getPosicao().setPosicao(1, 7);
        fase5.addMoeda(chave);
        
        //Bowser
        CogumeloMal bowser = new CogumeloMal("bowser.png");
        bowser.getPosicao().setPosicao(12, 8);
        fase5.addPersonagem(bowser);
        
        //LuckyBox -> Portal que mata
        CogumeloMal luckyBoxmata = new CogumeloMal("luckybox.png");
        luckyBoxmata.getPosicao().setPosicao(21, 1);
        fase5.addPersonagem(luckyBoxmata);
        
        //LuckyBox -> PortalCerto 
        PortalCerto portalcerto = new PortalCerto("luckybox.png", hero);
        portalcerto.getPosicao().setPosicao(9, 10);
        fase5.addPersonagem(portalcerto);
        
        //LuckyBox -> Portal que vai até o bownser 
        PortalQueMata portalMata = new PortalQueMata("luckybox.png", hero);
        portalMata.getPosicao().setPosicao(19, 5);
        fase5.addPersonagem(portalMata);
        
        //LuckyBox -> Portal que vai até o bownser 
        PortalQueMata portalMata2 = new PortalQueMata("luckybox.png", hero);
        portalMata2.getPosicao().setPosicao(6, 3);
        fase5.addPersonagem(portalMata2);
        
        
        //LuckyBox -> Portal que vai levar o personagem de volta ao inicio
        PortalVoltaInicio portalInicio = new PortalVoltaInicio("luckybox.png", hero);
        portalInicio.getPosicao().setPosicao(17, 12);
        fase5.addPersonagem(portalInicio);        
        
        //LuckyBox -> Portal que funciona como cascaDeBanana
        int[][] posPortalCascas = {
            {11, 2},
            {12, 1},  
        };
        for (int[] pos : posPortalCascas) {
            CascaDeBanana portalCascaBanana = new CascaDeBanana("luckybox.png", hero);
            portalCascaBanana.getPosicao().setPosicao(pos[0], pos[1]);
            fase5.addPersonagem(portalCascaBanana);        
        }
        
        //goomba
        int[][] prisao = {
           {16,7},{17,8},{15,8}      
         };        
         for (int[] pos : prisao) {
            CogumeloMal goomba = new CogumeloMal("goomba.png");
            goomba.getPosicao().setPosicao(pos[0], pos[1]);
            fase5.addPersonagem(goomba);
        }
        
        //Cascos posições
        int[][] posCascaTugaHorizontal = {
            {2, 5},
            {4,2},
            {4,0},
            {9,2},
            {21, 4},
            {24,9},
            {24,12},
            {18,13},
            {1,9},
            {3,13},
            {3,9},
            {11,10},
            {11,4},
            {13,4},
         
        };
        for (int[] pos : posCascaTugaHorizontal) {
            Cascoh casco = new Cascoh("casco.png");
            casco.getPosicao().setPosicao(pos[0], pos[1]);
            fase5.addPersonagem(casco);        
        } 
         
        // Chamas que matam (igual ao cogumelo do mal)
        int[][] chamas = {
           {1,6},{2,7},{8,1},{15,2},{15,1},{13,1},{14,1},
           {12,2},{13,2},{14,2},{20,5},{17,6},{26,12},{26,14},
           {22,12},{22,14},{17,10},{2,8},{11,7},{11,8},{11,9},
           {12,7},{12,9},{13,7},{13,8},{13,9},{14,4},{27,5}
     
        };
        
        for (int[] pos : chamas) {
           CogumeloMal c = new CogumeloMal("chama.png");
           c.getPosicao().setPosicao(pos[0], pos[1]);
           fase5.addPersonagem(c);
        }
        
        
         
        int[][] paredesTocha = {
            {2,1},{2,2},{2,3},{3,4},{3,6},{2,4},{4,6},{5,6},{5,5},
            {5,4},{5,3},{5,2},{4,2},{6,2},{7,3},{8,4},{9,4},{10,4},{10,3},
            {11,3},{12,3},{13,3},{14,3},{15,3},{17,3},{15,4},{15,5},{16,6},
            {17,5},{17,4},{18,3},{19,3},{20,3},{21,3},{23,3},{25,3},{22,3},
            {26,1},{26,2},{26,3},{26,4},{26,5},{26,6},{27,6},{23,4},{23,5},
            {24,7},{25,8},{24,9},{23,8},{23,6},{26,10},{25,11},{24,11},{23,11},
            {22,10},{21,9},{21,6},{20,7},{18,8},{19,9},{20,9},{19,7},
            {24,12},{24,12},{21,11},{18,10},{21,10},{18,11},{18,12},
            {17,13},{16,13},{15,13},{24,14},{16,11},{15,11},{14,11},
            {13,11},{12,11},{14,13},{12,13},{12,12},{11,13},{10,13},{9,13},
            {2,10},{2,11},{2,12},{2,13},{5,13},{6,13},{7,13},{8,13},{4,11},
            {4,10},{6,9},{6,10},{6,11},{5,8},{6,8},{7,7},{8,7},{8,6},{8,5},
            {10,11},{9,11},{8,11},{8,10},{8,9},{9,9},{10,9},{10,8},{10,7},
            {10,6},{11,6},{12,6},{13,6},
        };
        
        for (int[] pos : paredesTocha) {
           Cano c = new Cano("Tocha.png");
           c.getPosicao().setPosicao(pos[0], pos[1]);
           fase5.addPersonagem(c);
        }
         
         
        // Paredes externas
        for (int i = 0; i < 16; i++) {
            Cano topo = new Cano("Tocha.png");
            topo.getPosicao().setPosicao(0, i);
            fase5.addPersonagem(topo);

            Cano inferior = new Cano("Tocha.png");
            inferior.getPosicao().setPosicao(28, i);
            fase5.addPersonagem(inferior);
        }
        for (int i = 0; i < 29; i++) {
            Cano esquerda = new Cano("Tocha.png");
            esquerda.getPosicao().setPosicao(i, 0);
            fase5.addPersonagem(esquerda);

            Cano direita = new Cano("Tocha.png");
            direita.getPosicao().setPosicao(i, 15);
            fase5.addPersonagem(direita);
        }
        
        return fase5;
     }
     
     
      public static Fase criarFase6() {
        Hero hero = new Hero("mario&princesa.png");
        hero.getPosicao().setPosicao(27, 7);
        Fase fase6 = new Fase(hero, 11, 8);
                
        //Porta de saida da fase
        Moeda moeda1 = new Moeda("trono.png");
        moeda1.getPosicao().setPosicao(11, 8);
        fase6.addMoeda(moeda1);
        
        // trofeus
        int[][] trofeus = {
            {13,9},{13,6},{12,5},{11,5},{12,10},{11,10},
            {10,6},{10,7},{10,8},{10,9}
        };
        for (int[] pos : trofeus) {
           Cano trofeu = new Cano("trofeu.png");
           trofeu.getPosicao().setPosicao(pos[0], pos[1]);
           fase6.addPersonagem(trofeu);
        }
        
        
        
        //Fogos
        int[][] fogos = {
            {25,2},{21,2},{17,2},{25,13},{21,13},{17,13},{10,11},{10,4},{9,7},{9,8},
        };
        
        for (int[] pos : fogos) {
           Cano fogo = new Cano("fogosArt.png");
           fogo.getPosicao().setPosicao(pos[0], pos[1]);
           fase6.addPersonagem(fogo);
        }
        
        
        int[][] Tochas = {
            {27,5},{26,5},{25,5},{24,5},{23,5},{21,5},{19,5},{17,5},{15,5},
            {27,10},{26,10},{25,10},{24,10},{23,10},{21,10},{19,10},{17,10},{15,10}
        };
        
        for (int[] pos : Tochas) {
           Cano tocha = new Cano("Tocha.png");
           tocha.getPosicao().setPosicao(pos[0], pos[1]);
           fase6.addPersonagem(tocha);
        }
        
        
        // Adicionando personagens que aparecem nas fases
        
        //Cogumelo Mal
        Cano coguMal = new Cano("cogumeloroxo.png");
        coguMal.getPosicao().setPosicao(22 , 5);
        fase6.addPersonagem(coguMal);
        
        //Cogumelo bem
        Cano cogu = new Cano("cogumelo.png");
        cogu.getPosicao().setPosicao(22 , 10);
        fase6.addPersonagem(cogu);
        
        //bala de canhao
        Cano bala = new Cano("canhaoe.png");
        bala.getPosicao().setPosicao(20 , 10);
        fase6.addPersonagem(bala);
        
        //boo
        Cano boo = new Cano("boo.png");
        boo.getPosicao().setPosicao(20 , 5);
        fase6.addPersonagem(boo);
        
        //goomba
        Cano goomba = new Cano("goomba.png");
        goomba.getPosicao().setPosicao(18 , 5);
        fase6.addPersonagem(goomba);
        
        //Bowser
        Cano bowser = new Cano("bowser.png");
        bowser.getPosicao().setPosicao(18 , 10);
        fase6.addPersonagem(bowser);
        
        //Yoshi
        Cano yoshi = new Cano("yoshi.png");
        yoshi.getPosicao().setPosicao(16 , 10);
        fase6.addPersonagem(yoshi);
        
        //toad
        Cano toad = new Cano("Toad.png");
        toad.getPosicao().setPosicao(16 , 5);
        fase6.addPersonagem(toad);
        
        //Luiggi
        Cano luiggi = new Cano("luiggi.png");
        luiggi.getPosicao().setPosicao(14 , 5);
        fase6.addPersonagem(luiggi);
        
        //DonkeyKong
        Cano dk = new Cano("DonkeyKong.png");
        dk.getPosicao().setPosicao(14 , 10);
        fase6.addPersonagem(dk);
         
        // Paredes externas
        for (int i = 3; i < 13; i++) {
            Cano topo = new Cano("MuroFinal.png");
            topo.getPosicao().setPosicao(8, i);
            fase6.addPersonagem(topo);

            Cano inferior = new Cano("MuroFinal.png");
            inferior.getPosicao().setPosicao(28, i);
            fase6.addPersonagem(inferior);
        }
        for (int i = 9; i < 29; i++) {
            Cano esquerda = new Cano("MuroFinal.png");
            esquerda.getPosicao().setPosicao(i, 3);
            fase6.addPersonagem(esquerda);

            Cano direita = new Cano("MuroFinal.png");
            direita.getPosicao().setPosicao(i, 12);
            fase6.addPersonagem(direita);
        }
        
       return fase6; 
      }
}
