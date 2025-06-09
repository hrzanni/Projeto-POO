package Modelo;

import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;
import javax.swing.ImageIcon;


public abstract class Personagem implements Serializable {

    protected ImageIcon iImage;
    protected Posicao pPosicao;
    protected boolean bTransponivel; /*Pode passar por cima?*/
    protected boolean bMortal;       /*Se encostar, morre?*/
    protected boolean deslizando;  
    public Direcao direcao;
    
    public boolean isbMortal() {
        return bMortal;
    }


    protected Personagem(String sNomeImagePNG) {
        this.pPosicao = new Posicao(1, 1);
        this.bTransponivel = true;
        this.bMortal = false;
        this.deslizando = false;
        try {
            iImage = new ImageIcon(new java.io.File(".").getCanonicalPath() + Consts.PATH + sNomeImagePNG);
            Image img = iImage.getImage();
            BufferedImage bi = new BufferedImage(Consts.CELL_SIDE, Consts.CELL_SIDE, BufferedImage.TYPE_INT_ARGB);
            Graphics g = bi.createGraphics();
            g.drawImage(img, 0, 0, Consts.CELL_SIDE, Consts.CELL_SIDE, null);
            iImage = new ImageIcon(bi);
        } catch (IOException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    public void saveAsZip(String caminhoZip) throws IOException {

        try (ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(caminhoZip))) {

            ZipEntry entry = new ZipEntry("personagem.obj");
            zos.putNextEntry(entry);


            ObjectOutputStream oos = new ObjectOutputStream(zos);
            oos.writeObject(this);
            oos.flush();

            zos.closeEntry();
        }

    }
    
    public static Personagem loadFromZip(String caminhoZip) 
            throws IOException, ClassNotFoundException {
        try (ZipInputStream zis = new ZipInputStream(new FileInputStream(caminhoZip))) {
            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                if ("personagem.obj".equals(entry.getName())) {
                    ObjectInputStream ois = new ObjectInputStream(zis);
                    Personagem p = (Personagem) ois.readObject();
                    zis.closeEntry();
                    return p;
                }
                zis.closeEntry();
            }
        }
        throw new IllegalArgumentException("No personagem.obj inside " + caminhoZip);
    }
    
    public Posicao getPosicao() {
        /*TODO: Retirar este método para que objetos externos nao possam operar
         diretamente sobre a posição do Personagem*/
        return pPosicao;
    }

    public boolean isbTransponivel() {
        return bTransponivel;
    }

    public void setbTransponivel(boolean bTransponivel) {
        this.bTransponivel = bTransponivel;
    }

    public void autoDesenho(){
        Desenho.desenhar(this.iImage, this.pPosicao.getColuna(), this.pPosicao.getLinha());        
    }

    public boolean setPosicao(int linha, int coluna) {
        return pPosicao.setPosicao(linha, coluna);
    }

    public boolean moveUp() {
        return this.pPosicao.moveUp();
    }

    public boolean moveDown() {
        return this.pPosicao.moveDown();
    }

    public boolean moveRight() {
        return this.pPosicao.moveRight();
    }

    public boolean moveLeft() {
        return this.pPosicao.moveLeft();
    }
    
     public void setDeslizando(boolean deslizando) {
        this.deslizando = deslizando;
    }

    public boolean isDeslizando() {
        return this.deslizando;
    }
    
    public enum Direcao {
        CIMA, BAIXO, ESQUERDA, DIREITA
    }
     
    public Direcao getDirecao() {
        return direcao;
    }

    public void setDirecao(Direcao direcao) {
        this.direcao = direcao;
    } 
}
    