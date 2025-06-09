import Controler.Tela;
import javax.swing.SwingUtilities;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Tela tela = new Tela();
            tela.setVisible(true);
            tela.createBufferStrategy(2);
            tela.go();
        });
    }
}
