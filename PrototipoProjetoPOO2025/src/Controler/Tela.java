package Controler;

import Modelo.Personagem;
import Modelo.Hero;
import Auxiliar.Consts;
import Auxiliar.Desenho;
import auxiliar.Posicao;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.dnd.DropTarget;
import java.awt.dnd.DropTargetDropEvent;
import java.awt.dnd.DropTargetListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import javax.swing.JOptionPane;
 

public class Tela extends javax.swing.JFrame implements KeyListener, MouseListener {
    
    
    private static final String FILE_NAME = "fase_salva.dat";
    private Hero hero;
    private static Tela instancia;
    private Fase faseAtual;
    private ControleDeJogo cj = new ControleDeJogo();
    private Graphics g2;
    private int cameraLinha = 0;
    private int cameraColuna = 0;
    private int numeroFase = 1;
    
    private boolean jogoEncerrado = false;
    private String textoEncerramento = ""; 

    public Tela() {
        // Configuração básica da janela
        super("POO2023-1 - Skooter");
        Desenho.setCenario(this);
        
        // Define o tamanho da janela baseado nas constantes de resolução
        setSize(Consts.RES * Consts.CELL_SIDE + getInsets().left + getInsets().right,
                Consts.RES * Consts.CELL_SIDE + getInsets().top + getInsets().bottom);
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        
        addMouseListener(this);
        addKeyListener(this);
        
        new DropTarget(this.getContentPane(), new FileDropTargetListener());
        // Inicia a primeira fase
        this.numeroFase = 1;
        this.faseAtual = Fase.criarFase1();
        atualizaCamera();   
        Tela.instancia = this;
    }
    
    public static Tela getTela() {
        return instancia;
    }
    
    public Fase getFaseAtual() {
        return faseAtual;
    }
    
    private Hero getHeroDaFase() {
        if (faseAtual == null) return null;
        return faseAtual.getHero();
    }

    public int getCameraLinha() {
        return cameraLinha;
    }

    public int getCameraColuna() {
        return cameraColuna;
    }
    
    public void removePersonagem(Personagem p) {
        faseAtual.removePersonagem(p);
    }

    public void addPersonagem(Personagem p) {
        faseAtual.addPersonagem(p);
    }

    public Graphics getGraphicsBuffer() {
        return g2;
    }

    public boolean ehPosicaoValida(Posicao p) {
        return cj.ehPosicaoValida(this.faseAtual.getPersonagens(), p);
    }

    
    
    
    @Override
    public void paint(Graphics gOld) {
        
        if (jogoEncerrado) {
            Graphics g = this.getBufferStrategy().getDrawGraphics();
            g2 = g.create(getInsets().left, getInsets().top,
                          getWidth() - getInsets().right, getHeight() - getInsets().top);

            // Fundo escuro 
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, Consts.RES * Consts.CELL_SIDE, Consts.RES * Consts.CELL_SIDE);

            // Configura a fonte e a cor do texto
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Arial", Font.BOLD, 36));

            // Calcula o ponto (x,y) para centralizar o texto
            FontMetrics fm = g2.getFontMetrics();
            int larguraTexto = fm.stringWidth(textoEncerramento);
            int alturaTexto  = fm.getAscent();
            int x = (Consts.RES * Consts.CELL_SIDE - larguraTexto) / 2;
            int y = (Consts.RES * Consts.CELL_SIDE - alturaTexto) / 2 + alturaTexto;

            g2.drawString(textoEncerramento, x, y);

            g.dispose();
            g2.dispose();
            if (!getBufferStrategy().contentsLost()) {
                getBufferStrategy().show();
            }
            return;
        }
        
        // Obtém o bufferStrategy e desenha no buffer
        Graphics g = this.getBufferStrategy().getDrawGraphics();
        g2 = g.create(getInsets().left, getInsets().top,
                      getWidth() - getInsets().right, getHeight() - getInsets().top);
        
        
        String nomeFundo = "blackTile.png";
        switch (this.numeroFase) {
            case 1: nomeFundo = "grass_1.png"; break;
            case 2: nomeFundo = "grayFundo.png"; break;
            case 3: nomeFundo = "areiaFundo.png"; break;
            case 4: nomeFundo = "asfaltoFundo.png"; break;
            case 5: nomeFundo = "tijoloFundo.png"; break;
            case 6: nomeFundo = "tijoloFundo.png"; break;
            
            default: break;
        }
        
        // Desenha o “chão” 
        for (int i = 0; i < Consts.RES; i++) {
            for (int j = 0; j < Consts.RES; j++) {
                int mapaLinha = cameraLinha + i;
                int mapaColuna = cameraColuna + j;

                if (mapaLinha < Consts.MUNDO_ALTURA && mapaColuna < Consts.MUNDO_LARGURA) {
                    try {
                        Image newImage = Toolkit.getDefaultToolkit().getImage(
                            new java.io.File(".").getCanonicalPath() + Consts.PATH + nomeFundo);
                        g2.drawImage(newImage,
                                    j * Consts.CELL_SIDE, i * Consts.CELL_SIDE,
                                    Consts.CELL_SIDE, Consts.CELL_SIDE, null);
                    } catch (IOException ex) {
                        Logger.getLogger(Tela.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        }

        // Desenha e processa todos os personagens da fase
        if (!this.faseAtual.isEmpty()) {
            this.cj.desenhaTudo(faseAtual.getPersonagens());
            this.cj.processaTudo(faseAtual.getPersonagens());
        }

        // Exibe vida do herói no canto superior esquerdo
        g2.setColor(Color.RED);
        g2.drawString("Vida: " + faseAtual.getHero().getVida(), 10, 20);

        // Finaliza pintura e apresenta buffer
        g.dispose();
        g2.dispose();
        if (!getBufferStrategy().contentsLost()) {
            getBufferStrategy().show();
        }
    }

    private void atualizaCamera() {
        ArrayList<Personagem> personagens = faseAtual.getPersonagens();
        if (personagens.isEmpty()) return;
        Hero hero = (Hero) personagens.get(0);
        Posicao pos = hero.getPosicao();
        int linha = pos.getLinha();
        int coluna = pos.getColuna();

        cameraLinha = Math.max(0, Math.min(linha - Consts.RES / 2, Consts.MUNDO_ALTURA - Consts.RES));
        cameraColuna = Math.max(0, Math.min(coluna - Consts.RES / 2, Consts.MUNDO_LARGURA - Consts.RES));
    }

    /**
     * Inicia o loop de repaint() usando um TimerTask. A cada Consts.PERIOD ms,
     * a tela é redesenhada.
     */
    
    
    public void mousePressed(MouseEvent e) {
        // Obtém as coordenadas do clique do mouse
        int x = e.getX();
        int y = e.getY();

        // Calcula a linha e a coluna da célula onde o personagem deve ir
        int linha = y / Consts.CELL_SIDE;
        int coluna = x / Consts.CELL_SIDE;

        // Atualiza a posição do herói para a célula clicada
        if (faseAtual != null && faseAtual.getHero() != null) {
            faseAtual.getHero().getPosicao().setPosicao(linha, coluna);
        }

        atualizaCamera();
        repaint();
    }
    
    private class FileDropTargetListener implements DropTargetListener {
        @Override
        public void dragEnter(java.awt.dnd.DropTargetDragEvent dtde) { }

        @Override
        public void dragOver(java.awt.dnd.DropTargetDragEvent dtde) { }

        @Override
        public void dropActionChanged(java.awt.dnd.DropTargetDragEvent dtde) { }

        @Override
        public void dragExit(java.awt.dnd.DropTargetEvent dte) { }

        @Override
        public void drop(DropTargetDropEvent dtde) {
            try {
                dtde.acceptDrop(dtde.getDropAction());
                Transferable tr = dtde.getTransferable();

                if (tr.isDataFlavorSupported(DataFlavor.javaFileListFlavor)) {
                    @SuppressWarnings("unchecked")
                    ArrayList<File> arquivos = (ArrayList<File>) tr.getTransferData(DataFlavor.javaFileListFlavor);

                    // Recupera ponto de drop em pixels (relativo ao getContentPane())
                    int xTela = dtde.getLocation().x;
                    int yTela = dtde.getLocation().y;

                    // Converte pixel → célula de grid, levando em conta a câmera
                    int colRel = xTela / Consts.CELL_SIDE;
                    int linRel = yTela / Consts.CELL_SIDE;
                    int coluna = cameraColuna + colRel;
                    int linha  = cameraLinha  + linRel;

                    for (File arquivo : arquivos) {
                        if (!arquivo.getName().toLowerCase().endsWith(".zip")) {
                            continue;
                        }
                        Personagem novoP = carregarPersonagemDeZip(arquivo);
                        if (novoP != null) {
                            novoP.setPosicao(linha, coluna);
                            faseAtual.addPersonagem(novoP);
                        }
                    }
                    repaint();
                    dtde.dropComplete(true);
                    return;
                }
                dtde.dropComplete(false);
            } catch (Exception ex) {
                ex.printStackTrace();
                dtde.dropComplete(false);
            }
    }

    private Personagem carregarPersonagemDeZip(File arquivoZip) {
        try (FileInputStream fis = new FileInputStream(arquivoZip);
             ZipInputStream zis = new ZipInputStream(fis)) {

            ZipEntry entry;
            while ((entry = zis.getNextEntry()) != null) {
                try (ObjectInputStream ois = new ObjectInputStream(zis)) {
                    Object obj = ois.readObject();
                    if (obj instanceof Personagem) {
                        return (Personagem) obj;
                    }
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                zis.closeEntry();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }
    }
    
    public void go() {
        createBufferStrategy(2);
        TimerTask task = new TimerTask() {
            public void run() {
                repaint();
            }
        };
        Timer timer = new Timer();
        timer.schedule(task, 0, Consts.PERIOD);
    }
    

    @Override
    public void keyPressed(KeyEvent e) {
        if (faseAtual == null) return;

        Hero hero = getHeroDaFase();
        if (hero == null) return;

        if (e.getKeyCode() == KeyEvent.VK_C) {
            this.faseAtual.clear();
        } else if (e.getKeyCode() == KeyEvent.VK_UP) {
            hero.moveUp();
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            hero.moveDown();
        } else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            hero.moveLeft();
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            hero.moveRight();
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            salvarFase();
        } else if (e.getKeyCode() == KeyEvent.VK_L) {
            carregarFase();
        }

        // Verifica se o herói coletou alguma moeda
        faseAtual.checarColetaMoedas();

        // Atualiza a câmera e redesenha
        this.atualizaCamera();
        this.repaint();

        // Atualiza título com coordenadas do herói
        this.setTitle("-> Cell: " + (hero.getPosicao().getLinha()) + ", "
                           + (hero.getPosicao().getColuna()));

        // Se o herói morreu, exibe diálogo de Game Over
        if (!hero.estaVivo()) {
            int resposta = JOptionPane.showConfirmDialog(
                this,
                "GAME OVER! Deseja jogar novamente?",
                "Game Over",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
            );

            if (resposta == JOptionPane.YES_OPTION) {
                // Reinicia a fase atual
                switch (numeroFase) {
                    case 1: faseAtual = Fase.criarFase1(); break;
                    case 2: faseAtual = Fase.criarFase2(); break;
                    case 3: faseAtual = Fase.criarFase3(); break;
                    case 4: faseAtual = Fase.criarFase4(); break;
                    case 5: faseAtual = Fase.criarFase5(); break;
                    case 6: faseAtual = Fase.criarFase6(); break;
                    
                    default: break;

                }
                atualizaCamera();
                repaint();
            } else {
                System.exit(0);
            }
            return;
        }

        // Troca de fase (se todas as moedas coletadas e herói na saída)
        if (faseAtual.moedasColetadas() && faseAtual.checarTrocaDeFase()) {
            numeroFase++;
            System.out.println(">>> TROCANDO PARA FASE " + numeroFase);
            
            switch (numeroFase) {
                case 2:
                    faseAtual = Fase.criarFase2();
                    break;
                case 3: 
                    faseAtual = Fase.criarFase3(); 
                    break;
                case 4:
                    faseAtual = Fase.criarFase4();
                    break;
                case 5:
                    faseAtual = Fase.criarFase5(); 
                    break;
                case 6:
                    faseAtual = Fase.criarFase6(); 
                    break;

                default: 
                    jogoEncerrado = true;
                    textoEncerramento = "DESAFIO COMPLETO, PARABÉNS!!!";
                    break;
            }
            if (!jogoEncerrado) {
                atualizaCamera();
                repaint();
            } else {
                repaint(); // redesenha só a mensagem
            }
        }
    }
    
    // Método para salvar a fase em um arquivo
    private void salvarFase() {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(FILE_NAME))) {
            out.writeObject(faseAtual);  // Serializa a fase atual
            JOptionPane.showMessageDialog(this, "Fase salva com sucesso!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Erro ao salvar a fase: " + e.getMessage());
        }
    }

    // Método para carregar a fase de um arquivo
    private void carregarFase() {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(FILE_NAME))) {
            faseAtual = (Fase) in.readObject();  
            numeroFase = determinarNumeroFase(faseAtual);  
            atualizaCamera();
            repaint();
            JOptionPane.showMessageDialog(this, "Fase carregada com sucesso!");
        } catch (IOException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, "Erro ao carregar a fase: " + e.getMessage());
        }
    }
    private int determinarNumeroFase(Fase fase) {
        if (fase == Fase.criarFase1()) return 1;
        if (fase == Fase.criarFase2()) return 2;
        if (fase == Fase.criarFase3()) return 3;
        if (fase == Fase.criarFase4()) return 4;
        if (fase == Fase.criarFase5()) return 5;
        if (fase == Fase.criarFase6()) return 6;
        return 1;  
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // não usado
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // não usado
    }
    
     public void mouseMoved(MouseEvent e) {
    }

    public void mouseClicked(MouseEvent e) {
    }

    public void mouseReleased(MouseEvent e) {
    }

    public void mouseEntered(MouseEvent e) {
    }

    public void mouseExited(MouseEvent e) {
    }

    public void mouseDragged(MouseEvent e) {
    }
    

        
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("POO2023-1 - Skooter");
        setAlwaysOnTop(true);
        setAutoRequestFocus(false);
        setResizable(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 561, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables


}