/**
 * Class principal responsavel por inicializar a janela do jogo Space Cosmic
 * 
 * <p> Esta Classe cria a janela do jogo, configurando as propriedades
 * básicas como o título, tamanho, corpotamneto e fechamento e inicialização do painel do jogo (fase).</p>
 *
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li> Criaçãoe cconfiguração da janela principal (Jframe)</li>
 *   <li> Definição das propriedades da janela (tamanho, posição, redimensionamento)</li>
 *   <li> Inicialização do painel de jogo principal (classe Fase)</li>
 *   <li> Configuração do método main para a execução thread-safe</li>
 * 
 * <p><strong> Desenvolvimento:</strong></p>
 *  <ul>
 *   <li> Estrutura base: Tutorial de desenvolvimentos de jogos em Java no youtube</li>
 *   <li> Melhorias e refinamentos: Tive o auxilio de IA (Claude) para tratamentos de bugs, algoritmos complexos</li>
 *   <li> Principais modificações: Titulo do jogo, Performace e organização do código</li>
 * </ul>
 * 
 * @author Davi Paulino Conceição
 * @version 1.0
 * @since 2025
 */

///// Importes///
package meujogo;
import javax.swing.JFrame;
import meujogo.Modelo.Fase;

/**
 * Construtor da janela principal do jogo.
 * 
 * <p> Iniciliza todos os componentes da janela, inclusive titulo, tamanho,
 * comportamneto de fechamento e adiciona o painel principal do jogo.</p>
 * 
 * <p> Configurações aplicadas:</p>
* Configurações: título "Space Cosmic", dimensões 1024x768 pixels,
 * posição centralizada, redimensionamento desabilitado.
 *
 */
public class GameWindow extends JFrame {

    public GameWindow() {
        add(new Fase());
        setTitle("Space Cosmic"); // Titulo do jogo, quando executado aparece o que está entre ""
        setSize(1024, 768);  // Tamanho da tela, largura e altura 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //Fechar o jogo apertando o x
        setLocationRelativeTo(null); // Ponto da tela ao aparecer quando iniciar, no caso irá aparecer no centro. 
        this.setResizable(false); // Controle do usuário a personalizar a tela maximizando ou minimizando. No caso False para impedir de colocar em fullscreen, para mudar só colocar em true
        setVisible(true); // Série de comandos visível.
    }
    
    /**
     * Método principal que inicializa a aplicação do jogo.
     * 
     * <p>Utiliza SwingUtilities.invokerLater para garantir a interface gráfica
     * seja criada na thread de despacho de eventos (EDT), seguindo as boas práticas
     * para aplicações Swing.</p>
     * 
     * @param args argumentos da linha de comando (não utilizados)
     *
     */
    
    
    public static void main(String[] args) {
       
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new GameWindow();
            }
        });
    }
}