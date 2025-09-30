/**
 * Classe que representa o jogador controlável no jogo Space Cosmic.
 * 
 * <p>Esta é a classe central do personagem jogável, gerenciando a movimentação,
 * disparos, sistema de vidas, power-ups e integração com controle. Implementa
 * física de movimento otimizada para "turbo" (que não está funcionando a fisica dele ainda),
 * e sistema de tiros com herença de velocidade.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Sistema  de movimentação com velocidade diferentes (normal/"turbo")</li>
 *   <li>Sistema de tiros com física realística e herença de velocidade da nave</li>
 *   <li>Gerenciamento de recursos: vidas, foquetes e turbos</li>
 *   <li>Integração com sistema de áudio para feedback sonoro</li>
 *   <li>Sistema de power-ups e coleta de itens</li>
 *   <li>Controle de limites de tela e reset de estado</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxilio de IA (Claude)</li>
 *   <li>Principais modificações: Fisica dos tiros corrigida, sistema de áudio,
 *       lógica de colisões otimizadas, controle de recursos balançeado</li>
 * </ul>
 * 
 * @author Davi Paulino Conceição
 * @version 1.0
 * @since 2025
 * 
 */
package meujogo.Modelo;
// Importes
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.Timer;


public class Player implements ActionListener {

    private int x, y;
    private int dx, dy;
    private Image image;
    private Image imageNormal; 
    private Image imageTurbo;
    private int altura, largura;
    private List <Tiro> tiros;
    private boolean isVisivel, isTurbo;
    private Timer time;
    
  
    private AudioManager audioManager;
    
   
    private int vidas = 3;           
    private int foguetes = 10;       
    private int turbos = 3;          
    private final int MAX_FOGUETES = 10;
    private final int MAX_TURBOS = 5;  
    
 
    private boolean movimentoLimitado = false;
    
 /**
 * Construtor do jogador.
 * 
 * <p>Inicializa o jogador na posição inicial com recursos padrão
 * e inicia o timer para controle do "turbo".</p>
 */
    public Player() {
        this.x = 100;
        this.y = 100;
        isVisivel = true;
        isTurbo = false;
        tiros = new ArrayList<Tiro>();
        time = new Timer(5000, this);
        time.start();
    }
    
   
    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
    
    public void load() {
        ImageIcon referenciaNormal = new ImageIcon("res\\Image_7_de_set._de_2025_14_25_12_df_optimized.png");
        imageNormal = referenciaNormal.getImage();
        
        ImageIcon referenciaTurbo = new ImageIcon("res\\Image 20 de set. de 2025, 10_29_46 (1).png"); 
        imageTurbo = referenciaTurbo.getImage();

        image = imageNormal;
        altura = image.getHeight(null);
        largura = image.getWidth(null);
    }
    
  /**
 * Atualiza o estado do jogador a cada frame.
 * 
 * <p>Processa movimentação, mantém jogador dentro dos limites da tela
 * e atualiza todos os projéteis disparados.</p>
 */
    public void update() {
        x += dx;
        y += dy;
        
        // Limita a nave dentro da tela
        if (x < 1) {
            x = 1;
        }
        if (x > 1024 - largura) { 
            x = 1024 - largura;
        }
        if (y < 1) {
            y = 1;
        }
        if (y > 768 - altura) {
            y = 768 - altura;
        }
        
        for (int i = tiros.size() - 1; i >= 0; i--) {
            Tiro tiro = tiros.get(i);
            if (tiro.isVisivel()) {
                tiro.update();
            } else {
                tiros.remove(i);
            }
        }
    }
    
    public void tiroSimples() {
        Tiro novoTiro = new Tiro(x + largura, y + altura/2 - 10, false);
        novoTiro.setVelocidadeNave(isTurbo ? 5 : 3, isTurbo);
        novoTiro.load();
        tiros.add(novoTiro);
        
      
        if (audioManager != null) {
            audioManager.tocarTiro();
        }
    }
    
 /**
 * Dispara um foguete consumindo munição.
 * 
 * <p>Cria projétil com física otimizada, herança de velocidade da nave
 * e reproduz efeito sonoro. Só funciona se houver munição disponível.</p>
 */
    public void tiroFoguete() {
        if (foguetes > 0) {
            Tiro novoTiro = new Tiro(x + largura, y + altura/2 - 10, true);
            novoTiro.setVelocidadeNave(isTurbo ? 5 : 3, isTurbo);
            novoTiro.load();
            tiros.add(novoTiro);
            foguetes--;
            
            if (audioManager != null) {
                audioManager.tocarTiro();
            }
        }
    }
    
 /**
 * Ativa o modo "turbo" do jogador.
 * 
 * <p>Consome uma unidade de turbo, altera sprite visual, aumenta velocidade
 * de movimento e reproduz efeito sonoro. Dura 5 segundos. AJUSTAR FUTURAMENTE</p>
 */
    public void turbo() {
        if (turbos > 0 && isTurbo == false) {
            isTurbo = true;
            turbos--;
            image = imageTurbo; 
            time.restart();
            
         
            if (audioManager != null) {
                audioManager.tocarTurbo();
            }
        }
    }
    
    public void KeyPressed(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();
        
        if (codigo == KeyEvent.VK_SPACE) {
            turbo();              
        }
        
        
        if (codigo == KeyEvent.VK_A) {
            tiroFoguete();
        }
        
       
        if (codigo == KeyEvent.VK_UP) {
            dy = isTurbo ? -5 : -3;                       
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = isTurbo ? 5 : 3;                       
        }
        if (codigo == KeyEvent.VK_LEFT) {
            dx = isTurbo ? -5 : -3;                      
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            if (!movimentoLimitado) {
                dx = isTurbo ? 5 : 3;
            } else {
                dx = 0;
            }
        }
    }
    
    public void KeyRelease(KeyEvent tecla) {
        int codigo = tecla.getKeyCode();
        
        if (codigo == KeyEvent.VK_UP) {
            dy = 0;                       
        }
        if (codigo == KeyEvent.VK_DOWN) {
            dy = 0;                       
        }
        if (codigo == KeyEvent.VK_LEFT) {
            dx = 0;                      
        }
        if (codigo == KeyEvent.VK_RIGHT) {
            dx = 0;                       
        }
    }

    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
    
    public void perdeVida() {
        vidas--;
        if (vidas <= 0) {
            isVisivel = false;
        }
    }
    
 /**
 * Verifica se o jogador ainda está vivo e processa dano recebido.
 * 
 * <p>Remove uma vida quando chamado e retorna se ainda há vidas restantes.
 * Torna jogador invisível quando vidas chegam a zero.</p>
 * 
 * @return true se ainda há vidas, false se morreu
 */
    public boolean isVivo() {
        if (vidas <= 0) {
            isVisivel = false;
            return false;
        }
        perdeVida();
        return vidas > 0;
    }

    public void coletarFoguetes() {
        if (foguetes < MAX_FOGUETES) {
            foguetes++;
        }
    }
    
    public void coletarTurbos() {
        if (turbos < MAX_TURBOS) {
            turbos++;
        }
    }

    public void resetar() {
        this.x = 100;
        this.y = 100;
        this.vidas = 3;
        this.foguetes = 10;
        this.turbos = 3;
        this.isVisivel = true;
        this.isTurbo = false;
        this.movimentoLimitado = false;
        this.tiros.clear();
        this.dx = 0;
        this.dy = 0;
        this.image = imageNormal;
        time.stop();
        time = new Timer(5000, this);
        time.start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
      
        isTurbo = false;
        image = imageNormal; 
    }
    
    public void limitarMovimento() {
        this.movimentoLimitado = true;
    }

    // Getters e settes
    public int getX() { 
        return x; 
    }
    public int getY() { 
        return y; 
    }
    public Image getImage() { 
        return image; 
    }
    public List<Tiro> getTiros() { 
        return tiros; 
    }
    public boolean isVisivel() { 
        return isVisivel;
    }
    public int getVidas() { 
        return vidas;
    }
    public int getFoguetes() {
        return foguetes; 
    }
    public int getTurbos() { 
        return turbos; 
    }
    public boolean isTurbo() {
        return isTurbo;
    }
    
    public void setIsVisivel(boolean isVisivel) {
        this.isVisivel = isVisivel;
    }
}