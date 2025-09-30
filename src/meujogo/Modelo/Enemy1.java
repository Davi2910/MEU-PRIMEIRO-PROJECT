/**
 * Classe que representa os inimigos básicos do jogo Space Cosmic.
 * 
 * <p>Esta classe implementa os inimigos comuns que aparecem continuamente durante
 * o jogo, movendo-se da direta para a esquerda da tela. São eliminados com um tiro
 * e reproduzem efeito sonoto quando destruídos.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Movimento horizontal constante da direta para esquerda</li>
 *   <li>Sistema de detecção da colisões com jogador e projéties</li>
 *   <li>Integração com sistema de áudio para efeitos de destruição</li>
 *   <li>Controle de velocidade configurável estaticamente</li>
 *   <li>Remoção automática quando sai dos limites da tela</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxílio de IA (Claude)</li>
 *   <li>Principais modificações: Sistema de áudio, método destruir(), otimizações</li>
 * </ul>
 * 
 * @author Davi Paulino Conceição
 * @version 1.0
 * @since 2025
 */
package meujogo.Modelo;

// Importes
import java.awt.Image;
import java.awt.Rectangle;
import javax.swing.ImageIcon;

public class Enemy1 {
    private Image imagem;
    private int x, y;
    private int largura, altura;
    private boolean isVisivel;
    
    private AudioManager audioManager;
    
    private static int VELOCIDADE = 2;
    
 /**
 * 
 * Construtor do inimigo básico.
 * 
 * <p>Inicializa o inimigo na posição especificada e o torna visível.</p>
 * 
 * @param x posição horizontal inicial do inimigo
 * @param y posição vertical inicial do inimigo
 */
    public Enemy1(int x, int y) {
        this.x = x;                      
        this.y = y;
        isVisivel = true;
    }
    
   
    public void setAudioManager(AudioManager audioManager) {
        this.audioManager = audioManager;
    }
    
    /**
     * Carrega a imagem visual do inimigo.
     * 
     * <p>Carrega o sprite do inimigo e defina suas dimensões.
     * Em caso de erro, utiliza dimensões padrão.</p>
     */
    public void load() {
        try {
            ImageIcon referencia = new ImageIcon("res\\Image 16 de set. de 2025, 09_49_.png");
            imagem = referencia.getImage();
            
            if (imagem != null) {
                this.largura = imagem.getWidth(null);
                this.altura = imagem.getHeight(null);
            } else {
                this.largura = 50;
                this.altura = 50;
            }
        } catch (Exception e) {
            this.largura = 50;
            this.altura = 50;
            System.out.println("Erro ao carregar imagem do inimigo: " + e.getMessage());
        }
    }
    /**
     * Atualiza a posição do inimigo a cada frame.
     * 
     * <p>Move o inimigo horizontalmente e o torna invisível
     * quando sai dos limites da tela.</p>
     */
    public void update() {
        this.x -= VELOCIDADE;
        if(this.x < -largura) {
            isVisivel = false;
        }
    }

    /**
     * Destroi o inimigo e reproduz efeito sonoro.
     * 
     * <p>Torna o inimigo invisível e aciona o som de explosão
     * através do sistema de áudio.</p>
     */
    public void destruir() {
        isVisivel = false;
        
        // SOM: Inimigo destruído
        if (audioManager != null) {
            audioManager.tocarExplosao();
        }
    }
    
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura); 
    }
       // Métodos getts e setts
    
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
        return isVisivel; }

    public void setIsVisivel(boolean isVisivel) { 
        this.isVisivel = isVisivel; 
    }
    
    public void setVisivel(boolean isVisivel) { 
        this.isVisivel = isVisivel; 
    }

    public static void setVELOCIDADE(int VELOCIDADE) { 
        Enemy1.VELOCIDADE = VELOCIDADE; 
    }
    public static int getVELOCIDADE() { 
        return VELOCIDADE; }

    public Image getImage() { 
        return imagem; 
    }
}