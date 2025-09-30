/**
 * Classe que representa o power-up de energia de turbo.
 * 
 * <p>Este power-up permite ao jogador recuperar cargas de turbo quando coletado.
 * Move-se horizontalmente da direita para a esquerda da tela e é removido automaticamente
 * quando sai dos limites visíveis ou quando coletado pelo jogador.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Movimento horizontal constante</li>
 *   <li>Sistema de detecção de colisão com o jogador</li>
 *   <li>Carregamento de sprite visual</li>
 *   <li>Remoção automática quando sai da tela</li>
 *   <li>Incremento de cargas de turbo ao ser coletado</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxílio de IA (Claude)</li>
 *   <li>Principais modificações: Sistema de spawn otimizado, tratamento de erros</li>
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

public class PowerUpTurbo {
    private Image imagem;
    private int x, y;
    private int largura, altura;
    private boolean isVisivel;
    
    private static int VELOCIDADE = 3;
    
 /**
 * Construtor do power-up de turbo.
 * 
 * <p>Inicializa o power-up na posição especificada e o torna visível.</p>
 * 
 * @param x posição horizontal inicial do power-up
 * @param y posição vertical inicial do power-up
 */
    public PowerUpTurbo(int x, int y) {
        this.x = x;
        this.y = y;
        isVisivel = true;
    }
    
 /**
 * Carrega a imagem visual do power-up.
 * 
 * <p>Carrega o sprite do power-up e define suas dimensões.
 * Em caso de erro, utiliza dimensões padrão.</p>
 */ 
    public void load() {
        try {
            ImageIcon referencia = new ImageIcon("res\\spaceBuilding_003.png");
            imagem = referencia.getImage();
            
            if (imagem != null) {
                this.largura = imagem.getWidth(null);
                this.altura = imagem.getHeight(null);
            } else {
                this.largura = 25;
                this.altura = 25;
            }
        } catch (Exception e) {
            this.largura = 25;
            this.altura = 25;
            System.out.println("Usando power-up turbo padrão");
        }
    }
 /**
 * Atualiza a posição do power-up a cada frame.
 * 
 * <p>Move o power-up horizontalmente e o torna invisível
 * quando sai dos limites da tela.</p>
 */
    public void update() {
        this.x -= VELOCIDADE;
        
        if(this.x < -largura) {
            isVisivel = false;
        }
    }
    
    // Gettes e setts
    public Rectangle getBounds() {
        return new Rectangle(x, y, largura, altura);
    }
    public int getX() { 
        return x;
    }
    public int getY() { 
        return y;
    }
    public Image getImage() { 
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
}