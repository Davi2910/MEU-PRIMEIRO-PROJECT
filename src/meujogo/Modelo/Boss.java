/**
 * Classe que representa o chefe do jogo Space Cosmic
 * 
 * <p>O Boss é um inimigo especial que aparece após 90 segundos, ou 1 minuto e meio de jogo. Possuindo alta resisitencia,
 * movimento padronizado e a capacidade de atacar o player.
 * Implementa um sistema de vida robusto e padroes de movimento verticais.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Sistema de vida com 1000 pontos de resistencia</li>
 *   <li>Movimento horizontal límitado e verticial oscilatórios</li>
 *   <li>Sistema de ataques automáticos em intervalos regulares</li>
 *   <li>Integração com sistema de áudio para efeitos sonoros</li>
 *   <li>Detecção de colisoes e recebimento de dano</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong</p>
 * <ul>
 *  <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *  <li>Melhorias e correções: Implementadas com auxilio de IA (Claude)</li>
 *  <li>Principais modificações: Sistema de áudio, otimização de ataques</li>
 * </ul
 * 
 * @author Davi Paulino Conceição
 * @Version1 1.0
 * @since 2025
 *   
 */
package meujogo.Modelo;

// Importes
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;


public class Boss {
    private Image imagem;
    private int x, y;
    int largura;
    private int altura;
    private boolean isVisivel;
    private int vida;
    
  
    private AudioManager audioManager;
    
    private List<BossTiro> tiros;
    private int contadorAtaque = 0;
    private final int INTERVALO_ATAQUE = 120; 
    
    private static final int VELOCIDADE_X = 1;
    private int direcaoY = 1; 
    private static final int VELOCIDADE_Y = 1;
    private static final int POSICAO_LIMITE_X = 650;
    
 /**
 * Construtor do Boss.
 * 
 * <p>Inicializa o chefe na posição especificada com vida maxima
 *  e lista de tiros vazia.</p>
 * 
 * @param x posição horizontal inicial
 * @param y posição vertical inicial
 *
 */
    public Boss(int x, int y) {
        this.x = x;
        this.y = y;
        isVisivel = true;
        vida = 1000; // Pontos de vida do chefe
        tiros = new ArrayList<BossTiro>();
    }
    
        
    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
    
   
    public void load() {
        try {
            ImageIcon referencia = new ImageIcon("res\\Image 20 de set. de 2025, 13_36_05 (1) (1).png");
            imagem = referencia.getImage();
            
            if (imagem != null) {
                this.largura = imagem.getWidth(null);
                this.altura = imagem.getHeight(null);
            } else {
                this.largura = 150;
                this.altura = 150;
            }
        } catch (Exception e) {
            this.largura = 150;
            this.altura = 150;
            System.out.println("Erro ao carregar imagem do chefe: " + e.getMessage());
        }
    }
    
     /**
     * Atualizar o estado do Boss a cada frame.
     * 
     * <p>gerencia movimento horizontal (até posição limite), movimento vertical
     * oscilatório (sobe e desce) e sistema de ataques em intervalos regulares.</p>
     */
    public void update() {
      
        if (this.x > POSICAO_LIMITE_X) {
            this.x -= VELOCIDADE_X; 
        }
        this.y += VELOCIDADE_Y * direcaoY;
        if (this.y < 50 || this.y > 600) {
            direcaoY *= -1; 
        }
        
        contadorAtaque++;
        if (contadorAtaque >= INTERVALO_ATAQUE) {
            atacar();
            contadorAtaque = 0;
        }
    }
    
    /**
     * Executa um ataque do Boss.
     * 
     * <p>Cria um novo projetil que se move em direção ao jogador,
     * dispardo o centro do chefe.</p>
     */
    public void atacar() {
        BossTiro tiro = new BossTiro(x, y + altura / 2); // Atira do meio do chefe
        tiro.load();
        this.tiros.add(tiro);
        
    }
    
    /**
     * Aplica dano ao Boss e verifica se foi derrotado.
     * 
     * <p>Reduz a vida do chefe e torna invisivel quando a vida
     * chega a zero, reproduzindo som d explosão.</p>
     * 
     * @param dano quantidade de dano a ser aplicada.
     */
    
    public void receberDano(int dano) {
        this.vida -= dano;
        if (this.vida <= 0) {
            this.isVisivel = false;
            
            if (audioManager != null) {
                audioManager.tocarExplosao();
            }
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }


    public int getX() { 
      return x; 
    }
    public int getY() {
        return y; 
    }
    public Image getImagem() { 
        return imagem; 
    }
    
    public boolean isVisivel() { 
        return isVisivel; 
    }
    
    public boolean isIsVisivel() { 
        return isVisivel; 
    }
    public void setIsVisivel(boolean isVisivel) {
        this.isVisivel = isVisivel; 
    }
    public int getVida() { 
        return vida; 
    }
    public List<BossTiro> getTiros() { 
        return tiros; 
    }
}