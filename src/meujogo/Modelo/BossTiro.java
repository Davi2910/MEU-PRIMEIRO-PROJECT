/**
 * Classe que representa os projéties disparados pelo Boss.
 * 
 * 
 * <p>Esta classe implementa os tiros do chefe, que se movem horizontalmente
 * da direta para a esquerda da tela em direção ao jogador. Os projeteis pusseum
 * velocidade constante e são removidos quando saem dos llimites da tela.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Movimento horizontal constante em direção ao jogador</li>
 *   <li>Carregamneto de sprite visual do projetil</li>
 *   <li>Sistema de detecção de colisões</li>
 *   <li>Remoção automática quando sai dos llimites da tela</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxilio de IA (Claude)</li>
 *   <li>Principais modificações: otimização de performance, correção de bugs</li>
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

public class BossTiro {
    private Image imagem;
    private int x, y;
    private int largura, altura;
    private boolean isVisivel;
    
    private static final int VELOCIDADE = 3;
    
 /**
 * 
 * Contrutor do projetil do Boss.
 * 
 * <p>Inicializa o tiro na posição especiifca e o torna visível.</p>
 * 
 * @param x posição horizontal inicial do projetil
 * @param y posição vertical inicial do projetil
 */
    public BossTiro(int x, int y) {
        this.x = x;
        this.y = y;
        isVisivel = true;
    }
    
    /**
     * Carrega a imagem visual do projetil do Boss.
     * 
     * <p>Carrega o sprite do projetil e define suas dimensões
     * com base no arquivo de imagem.</p>
     */
    public void load() {
        ImageIcon referencia = new ImageIcon("res\\spaceMeteors_002 (1).png");
        imagem = referencia.getImage();
        
        this.largura = imagem.getWidth(null);
        this.altura = imagem.getHeight(null);
    }
    
    /**
     * Atualiza a posição do projetil a cada frame.
     * 
     * <p>Move o tiro horizontal em direção ao jogador
     * e o torna invisivel quando sai dos limites da tela.</p>
     */
    public void update() {
        this.x -= VELOCIDADE;
            if(this.x < -largura) {
                isVisivel = false;
           }
    }
    
    /**
     * Retorna o retangulo de colisão do projetil.
     * 
     * @return Rectangle representa a área de colisão do tiro 
     */
    public Rectangle getBounds() {
        return new Rectangle(x,y,largura,altura); 
    }  
    
    // Métodos gettes e setts

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
    public Image getImage() { 
        return imagem;
    }
}