
package Modelo;

public class PortalQueMata extends Personagem{    
    
    private Personagem hero; 
    
    public PortalQueMata(String sNomeImagePNG, Personagem hero){
        super(sNomeImagePNG);
        this.bTransponivel = true;
        this.bMortal = false;
        this.hero = hero;
    }
    
    
    @Override
    public void autoDesenho(){
        super.autoDesenho();   
        
        int linhaAtual = hero.getPosicao().getLinha();
        int heroColuna = hero.getPosicao().getColuna();

        
        if (linhaAtual == this.getPosicao().getLinha() && heroColuna == this.getPosicao().getColuna()){    
            hero.setPosicao(12, 8);
        }       
    }
         
    
}
