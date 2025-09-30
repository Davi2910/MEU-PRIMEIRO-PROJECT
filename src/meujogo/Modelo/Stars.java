/**
 * Classe que representa as estrelas de fundo do jogo Space Cosmic.
 * 
 * <p>Esta classe implementa elementos visuais decorativos que criam efeito de
 * profundidade e movimento espacial. As estrelas se movem horizontalmente e
 * reaparecem do lado direito quando saem da tela, criando um loop infinito.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Movimento horizontal contínuo criando efeito parallax</li>
 *   <li>Sistema de reposicionamento automático para loop infinito</li>
 *   <li>Carregamento de sprite visual</li>
 *   <li>Velocidade configurável estaticamente para ajuste de parallax</li>
 *   <li>Geração de atmosfera espacial visual</li>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxílio de IA (Claude)</li>
 *   <li>Principais modificações: Sistema de loop otimizado, tratamento de erros</li>
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
import java.util.Random;
import javax.swing.ImageIcon;

public class Stars {
    private Image imagem;
    private int x, y;
    private int largura, altura;
    private boolean isVisivel;
    
    private static int VELOCIDADE = 4;
    private static Random random = new Random();
    
 /**
 * Construtor da estrela de fundo.
 * 
 * <p>Inicializa a estrela na posição especificada e a torna visível.</p>
 * 
 * @param x posição horizontal inicial da estrela
 * @param y posição vertical inicial da estrela
 */
    public Stars(int x, int y) {
        this.x = x;
        this.y = y;
        isVisivel = true;
    }
    
 /**
 * Carrega a imagem visual da estrela.
 * 
 * <p>Carrega o sprite da estrela e define suas dimensões.
 * Em caso de erro, utiliza dimensões padrão.</p>
 */
    public void load() {
        try {
            ImageIcon referencia = new ImageIcon("res\\Image_18_de_set._de_2025__09_22_43__1_-removebg-preview.png");
            imagem = referencia.getImage();
            
            if (imagem != null) {
                this.largura = imagem.getWidth(null);
                this.altura = imagem.getHeight(null);
            } else {
                this.largura = 10;
                this.altura = 10;
            }
        } catch (Exception e) {
            this.largura = 10;
            this.altura = 10;
            System.out.println("Erro ao carregar imagem da estrela: " + e.getMessage());
        }
    }
 /**
 * Atualiza a posição da estrela a cada frame.
 * 
 * <p>Move a estrela horizontalmente e a reposiciona no lado direito
 * quando sai dos limites da tela, criando efeito de loop infinito.</p>
 */
    public void update() {
        this.x -= VELOCIDADE;
        
        if(this.x < -largura) {
            this.x = (int)(Math.random() * 200 + 1024);
            this.y = (int)(Math.random() * 768);
        }
    }
    // Guetts e setts
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
    
    public void setVisivel(boolean isVisivel) { 
        this.isVisivel = isVisivel; 
    }

    public static void setVELOCIDADE(int VELOCIDADE) { 
        Stars.VELOCIDADE = VELOCIDADE;
    }
    public static int getVELOCIDADE() { 
        return VELOCIDADE; }
    
    public Image getImage() {
        return imagem; }
}