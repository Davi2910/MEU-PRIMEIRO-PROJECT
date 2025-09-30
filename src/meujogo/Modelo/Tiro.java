/**
 * Classe que representa os projéteis disparados pelo jogador.
 * 
 * <p>Esta classe implementa o sistema de tiros do jogador com física realística,
 * incluindo herança de velocidade da nave e comportamento diferenciado durante
 * turbo. Os projéteis podem ser tiros normais ou foguetes com velocidades distintas.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Sistema de física com herança de velocidade da nave</li>
 *   <li>Dois tipos de projéteis: normal e foguete</li>
 *   <li>Comportamento ajustado durante modo turbo da nave</li>
 *   <li>Sistema de detecção de colisões</li>
 *   <li>Remoção automática quando sai dos limites da tela</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxílio de IA (Claude)</li>
 *   <li>Principais modificações: Física dos tiros completamente refeita para realismo,
 *       sistema de herança de velocidade, ajuste para turbo</li>
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

public class Tiro {
    private Image imagem;
    private int x, y;
    private int largura, altura;
    private boolean isVisivel;
    private boolean isFoguete; 
    
    private static final int LARGURA = 938;     
    private static final int VELOCIDADE_NORMAL = 6;   
    private static final int VELOCIDADE_FOGUETE = 10; 

    private int velocidadeNave = 3; // Velocidade padrão da nave
    private boolean naveTurbo = false;
 /**
 * Construtor do projétil.
 * 
 * <p>Inicializa o tiro na posição especificada, define o tipo
 * e configura velocidade padrão da nave.</p>
 * 
 * @param x posição horizontal inicial do projétil
 * @param y posição vertical inicial do projétil
 * @param isFoguete true para foguete, false para tiro normal
 */
    public Tiro(int x, int y, boolean isFoguete) {
        this.x = x;                      
        this.y = y;
        this.isFoguete = isFoguete;
        isVisivel = true;
        // Velocidade da nave será passada depois
        this.velocidadeNave = 3;
        this.naveTurbo = false;
    }
    
    /**
 * Define a velocidade da nave que disparou o projétil.
 * 
 * <p>Permite que o projétil herde a velocidade da nave para
 * física realística, especialmente importante durante turbo.</p>
 * 
 * @param velocidadeNave velocidade atual da nave
 * @param naveTurbo true se a nave está em modo turbo
 */
    public void setVelocidadeNave(int velocidadeNave, boolean naveTurbo) {
        this.velocidadeNave = velocidadeNave;
        this.naveTurbo = naveTurbo;
    }
    
    public void load() {
        ImageIcon referencia = null;
        
        if (isFoguete) {
            referencia = new ImageIcon("res\\tiro_verde_no_fundo_optimized.png");
        } else {
      
            referencia = new ImageIcon("res\\tiro_verde_no_fundo_optimized.png");
        }
        
        if (referencia != null) {
            imagem = referencia.getImage();
            
            if (imagem != null) {
                this.largura = imagem.getWidth(null);
                this.altura = imagem.getHeight(null);
            } else {
                
                this.largura = 10;
                this.altura = 5;
            }
        } else {
          
            this.largura = 10;
            this.altura = 5;
        }
    }
    
    /**
 * Atualiza a posição do projétil a cada frame com física realística.
 * 
 * <p>Aplica velocidade base herdada da nave mais velocidade própria do projétil.
 * Durante turbo, reduz ligeiramente a velocidade para manter proporção física
 * realista entre nave e projéteis.</p>
 */
    public void update() {
       
        int velocidadeBase = velocidadeNave; // Herda a velocidade da nave
        
        if (isFoguete) {
            this.x += velocidadeBase + VELOCIDADE_FOGUETE; 
        } else {
            this.x += velocidadeBase + VELOCIDADE_NORMAL; 
        }
        
        if (naveTurbo) {
            this.x -= 3; 
        }
        
        if(this.x > LARGURA) {
            isVisivel = false;           
        }
    }
    
    //Guetts e setts
    
    public Rectangle getBounds() {
        return new Rectangle (x,y,largura,altura); 
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
    public boolean isIsVisivel() { 
        return isVisivel;
    }
    public void setIsVisivel(boolean isVisivel) { 
        this.isVisivel = isVisivel; 
    }
    public boolean isFoguete() { 
        return isFoguete;
    }
    public Image getImage() {
        return imagem; 
    }

    public boolean isVisivel() {
        return this.isVisivel;
    }
}