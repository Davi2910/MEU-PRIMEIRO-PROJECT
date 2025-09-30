/**
 * Classe principal que gerencia toda a lógica da gameplay do Space Cosmic.
 * 
 * <p>Esta é a classe central do jogo, responsável por coordenar todos os elemnetos 
 * de gameplay incluindo o jogor, inimigos, projétis, power-ups, chefe, sistema de colisões,
 * renderização e controles. Estende JPanel para forneceer a interface gráfica prinicipal
 * do jogo.</p>
 * 
 *<p><strong>Funcionalidades principais:</strong></p>
 * <ul>
 *   <li>Gerencianeto do loop principal do jogo e sistema de timing</li>
 *   <li>Sistema completo de spaw automático de inimigos, estrelas e power-ups</li>
 *   <li>Detecção e processamento de todas as colisões do jogo</li>
 *   <li>Renderização de todos os elementos visuais e HUD</li>
 *   <li>Integração com sistema de áudio para música e efeito sonoros</li>
 *   <li>Controles de teclado e mouse para a interação do jogador</li>
 *   <li>Sistema de reinicialização do jogo e tela do gamer over</li>
 *   <li> FUTURAMENTE QUEM SABE, "POSSIVEL INTEGRAÇÃO COM BANCO DE DADOS E SISTEMA DE SAVE" DATA DE DIGITAÇÃO = 26/09/2025</lin>
 * </ul>
 * 
 * <p><strong>Desenvolvimento:</strong></p>
 *  <ul>
 *   <li>Estrutura base: Tutorial do youtube de desenvolvimento de jogos em Java</li>
 *   <li>Melhorias e correções: Implementadas com auxilio de IA (claude)</li>
 *   <li>Principais modificações: Sistema de áudio completo, fisica de colisões,
 *       sistema de spaw otimizado, HUD aprimorado, controle     de áudio</li>
 * </ul>
 * 
 * @author Davi Paulino Conceição
 * @veersion 1.0
 * @since 2025
 *
 */

package meujogo.Modelo;

// Muitos Importes kk //
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.Timer;

public class Fase extends JPanel implements ActionListener {
    
    private Image fundo;
    private Player player;  
    private Timer time;
    private List<Enemy1> enemy1;
    private List<Stars> stars; 
    private List<PowerUpFoguete> powerUpsFoguete;  
    private List<PowerUpTurbo> powerUpsTurbo;      
    private boolean emJogo;
    
  
    private AudioManager audioManager;
    private int contadorSpawnInimigo = 0;
    private final int INTERVALO_SPAWN_INIMIGO = 100;
    
    private int contadorSpawnEstrela = 0;
    private final int INTERVALO_SPAWN_ESTRELA = 50;
    
    private int contadorSpawnPowerUpFoguete = 0;
    private final int INTERVALO_SPAWN_POWERUP_FOGUETE = 400;
    
    private int contadorSpawnPowerUpTurbo = 0;
    private final int INTERVALO_SPAWN_POWERUP_TURBO = 600;
    
  
    private final int MAX_INIMIGOS = 30;
    private final int MAX_ESTRELAS = 100;
    private final int MAX_POWERUPS_FOGUETE = 5;
    private final int MAX_POWERUPS_TURBO = 3;
    
 
    private Rectangle botaoContinuar;
    private boolean mostrarBotaoContinuar = false;
    
   
    private Image imagemVida;
    private Image imagemFoguete;
    private Image imagemTurbo;
    

    private Boss boss;
    private boolean bossAtivo = false;
    private long tempoInicialJogo;
    
 /**
 * 
 * Contrutor de classe Fase.
 * 
 * <p>Inicializa todos os componentes do jogo incluindo sistema de áudio,
 * interface gráfica, Listeners de eventos e elementos visuais.</p>
 *
 */
    public Fase() {
        setFocusable(true);
        setDoubleBuffered(true);
        
       
        audioManager = AudioManager.getInstance();
        
        try {
            ImageIcon referencia = new ImageIcon("res\\black-background.png");
            fundo = referencia.getImage();
        } catch (Exception e) {
            System.out.println("Erro ao carregar fundo: " + e.getMessage());
        }
        
        carregarImagensHUD();
        
        botaoContinuar = new Rectangle(750, 650, 200, 60);
        
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (mostrarBotaoContinuar && botaoContinuar.contains(e.getPoint())) {
                    reiniciarJogo();
                }
            }
        });
        
        iniciarJogo();
        tempoInicialJogo = System.currentTimeMillis();
        
      
        audioManager.iniciarMusicaFundo();
    }
    
    private void carregarImagensHUD() {
        try {
            ImageIcon vida = new ImageIcon("res\\Image 20 de set. de 2025, 14_35_13.png");
            imagemVida = vida.getImage();
        } catch (Exception e) {
            imagemVida = null;
        }
        
        try {
            ImageIcon foguete = new ImageIcon("res\\spaceMissiles_002.png");
            imagemFoguete = foguete.getImage();
        } catch (Exception e) {
            imagemFoguete = null;
        }
        
        try {
            ImageIcon turbo = new ImageIcon("res\\spaceBuilding_003.png");
            imagemTurbo = turbo.getImage();
        } catch (Exception e) {
            imagemTurbo = null;
        }
    }
    
    private void desenharHUD(Graphics2D graficos) {
        Font fonteHUD = new Font("Arial", Font.BOLD, 20);
        graficos.setFont(fonteHUD);
        graficos.setColor(Color.WHITE);
        
        int hudY = 30;
        int espacamento = 150;
        
        int vidasX = 30;
        for (int i = 0; i < player.getVidas(); i++) {
            if (imagemVida != null) {
                graficos.drawImage(imagemVida, vidasX + (i * 35), hudY - 5, 30, 30, null);
            } else {
                graficos.setColor(Color.RED);
                graficos.fillOval(vidasX + (i * 35), hudY - 5, 25, 25);
                graficos.setColor(Color.WHITE);
                graficos.drawString("♥", vidasX + (i * 35) + 5, hudY + 15);
            }
        }
        
        int foguetesX = vidasX + espacamento;
        if (imagemFoguete != null) {
            graficos.drawImage(imagemFoguete, foguetesX, hudY - 5, 30, 30, null);
        } else {
            graficos.setColor(Color.ORANGE);
            graficos.fillRect(foguetesX, hudY - 5, 25, 30);
            graficos.setColor(Color.RED);
            graficos.fillRect(foguetesX + 5, hudY + 20, 15, 10);
        }
        graficos.setColor(Color.WHITE);
        graficos.drawString(String.valueOf(player.getFoguetes()), foguetesX + 40, hudY + 15);
        
        int turbosX = foguetesX + espacamento;
        if (imagemTurbo != null) {
            graficos.drawImage(imagemTurbo, turbosX, hudY - 5, 30, 30, null);
        } else {
            graficos.setColor(Color.CYAN);
            graficos.fillOval(turbosX, hudY - 5, 30, 30);
            graficos.setColor(Color.WHITE);
            graficos.drawString("T", turbosX + 10, hudY + 15);
        }
        graficos.setColor(Color.WHITE);
        graficos.drawString(String.valueOf(player.getTurbos()), turbosX + 40, hudY + 15);
        
        Font fonteInstrucoes = new Font("Arial", Font.PLAIN, 14);
        graficos.setFont(fonteInstrucoes);
        graficos.setColor(Color.LIGHT_GRAY);
        
        graficos.drawString("A: Atirar | SPACE: Turbo | Setas: Mover | M: Música ON/OFF", 30, getHeight() - 40);
        graficos.drawString("Volume: + / - | Efeitos: E ON/OFF", 30, getHeight() - 20);
        
        graficos.setColor(Color.WHITE);
        String nomeDesenvolvedor = "© Davi Paulino Conceição";
        int larguraNome = graficos.getFontMetrics().stringWidth(nomeDesenvolvedor);
        graficos.drawString(nomeDesenvolvedor, getWidth() - larguraNome - 30, getHeight() - 20);
        
        // Indicador de áudio
        if (audioManager.isMusicaAtiva()) {
            graficos.setColor(Color.GREEN);
            graficos.drawString("♪ ON", getWidth() - 100, hudY);
        } else {
            graficos.setColor(Color.RED);
            graficos.drawString("♪ OFF", getWidth() - 100, hudY);
        }
    }
    
    /**
     * Inicializa uma nova partida do jogo.
     * 
     * <p>Cria o jogador, configura o timer principal, inicializa  todos os 
     * elementos do jogo e define o estado como "Em jogo".</p>
     */
    private void iniciarJogo() {
        player = new Player();
        player.load(); 
        player.setAudioManager(audioManager); // Passar referência do áudio
                   
        addKeyListener(new TecladoAdapter());       
         
        time = new Timer(5, this);
        time.start();
     
        inicializaInimigos();
        inicializaStars();
        inicializaPowerUps();
        emJogo = true;
        mostrarBotaoContinuar = false;
        
        tempoInicialJogo = System.currentTimeMillis();
    }
    
    private void reiniciarJogo() {
        if (time != null) {
            time.stop();
        }
        
        if (enemy1 != null) enemy1.clear();
        if (stars != null) stars.clear();
        if (powerUpsFoguete != null) powerUpsFoguete.clear();
        if (powerUpsTurbo != null) powerUpsTurbo.clear();
        
        contadorSpawnInimigo = 0;
        contadorSpawnEstrela = 0;
        contadorSpawnPowerUpFoguete = 0;
        contadorSpawnPowerUpTurbo = 0;
        
        boss = null;
        bossAtivo = false;
        
        iniciarJogo();
        
        if (player != null) {
            player.resetar();
        }
        
        requestFocusInWindow();
    }
    
    public void inicializaInimigos() {
        enemy1 = new ArrayList<Enemy1>();  
      
        try {
            for(int i = 0; i < 15; i++) {
                int x = (int)(Math.random() * 8000 + 1024); 
                int y = (int)(Math.random() * 650 + 30);
                Enemy1 novoInimigo = new Enemy1(x, y);
                novoInimigo.load();
                novoInimigo.setAudioManager(audioManager); // Passar áudio para inimigos
                enemy1.add(novoInimigo);
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar inimigos: " + e.getMessage());
        }
    }
    
    public void inicializaStars() {
        stars = new ArrayList<Stars>();
        
        try {
            for(int i = 0; i < 80; i++) {
                int x = (int)(Math.random() * 1024 + 0); 
                int y = (int)(Math.random() * 768 + 0);
                Stars novaStar = new Stars(x, y);
                novaStar.load();
                stars.add(novaStar);
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar estrelas: " + e.getMessage());
        }
    }
    
    private void inicializaPowerUps() {
        powerUpsFoguete = new ArrayList<PowerUpFoguete>();
        powerUpsTurbo = new ArrayList<PowerUpTurbo>();
        
        try {
            for(int i = 0; i < 3; i++) {
                int x = (int)(Math.random() * 2000 + 1024); 
                int y = (int)(Math.random() * 700 + 50);
                PowerUpFoguete powerUp = new PowerUpFoguete(x, y);
                powerUp.load();
                powerUpsFoguete.add(powerUp);
            }
            
            for(int i = 0; i < 2; i++) {
                int x = (int)(Math.random() * 3000 + 1024); 
                int y = (int)(Math.random() * 700 + 50);
                PowerUpTurbo powerUp = new PowerUpTurbo(x, y);
                powerUp.load();
                powerUpsTurbo.add(powerUp);
            }
        } catch (Exception e) {
            System.out.println("Erro ao inicializar power-ups: " + e.getMessage());
        }
    }
    
    private void inicializaBoss() {
        boss = new Boss(getWidth(), 300);
        boss.load();
        boss.setAudioManager(audioManager); // Passar áudio para o chefe
        bossAtivo = true;
        System.out.println("O CHEFE APARECEU!");
    }
    
    private void spawnNovoInimigo() {
        contadorSpawnInimigo++;
        if (contadorSpawnInimigo >= INTERVALO_SPAWN_INIMIGO && enemy1.size() < MAX_INIMIGOS) {
            int x = (int)(Math.random() * 2000 + 1024);
            int y = (int)(Math.random() * 650 + 30);
            Enemy1 novoInimigo = new Enemy1(x, y);
            novoInimigo.load();
            novoInimigo.setAudioManager(audioManager);
            enemy1.add(novoInimigo);
            contadorSpawnInimigo = 0;
        }
    }
    
    private void spawnNovaEstrela() {
        contadorSpawnEstrela++;
        if (contadorSpawnEstrela >= INTERVALO_SPAWN_ESTRELA && stars.size() < MAX_ESTRELAS) {
            int x = (int)(Math.random() * 200 + 1024);
            int y = (int)(Math.random() * 768 + 0);
            Stars novaStar = new Stars(x, y);
            novaStar.load();
            stars.add(novaStar);
            contadorSpawnEstrela = 0;
        }
    }
    
    private void spawnNovoPowerUpFoguete() {
        contadorSpawnPowerUpFoguete++;
        if (contadorSpawnPowerUpFoguete >= INTERVALO_SPAWN_POWERUP_FOGUETE && 
            powerUpsFoguete.size() < MAX_POWERUPS_FOGUETE) {
            int x = (int)(Math.random() * 500 + 1024);
            int y = (int)(Math.random() * 700 + 50);
            PowerUpFoguete powerUp = new PowerUpFoguete(x, y);
            powerUp.load();
            powerUpsFoguete.add(powerUp);
            contadorSpawnPowerUpFoguete = 0;
        }
    }
    
    private void spawnNovoPowerUpTurbo() {
        contadorSpawnPowerUpTurbo++;
        if (contadorSpawnPowerUpTurbo >= INTERVALO_SPAWN_POWERUP_TURBO && 
            powerUpsTurbo.size() < MAX_POWERUPS_TURBO) {
            int x = (int)(Math.random() * 500 + 1024);
            int y = (int)(Math.random() * 700 + 50);
            PowerUpTurbo powerUp = new PowerUpTurbo(x, y);
            powerUp.load();
            powerUpsTurbo.add(powerUp);
            contadorSpawnPowerUpTurbo = 0;
        }
    }
    
   /**
  * Renderiza todos os elementos visuais do jogo.
  * 
  * <p>Método principal de renderização que desenha fundo, estrelas, power-ups,
  * jogador, tiros, inimigos, chefe, HUD e telas de game over.</p>
  * 
  * @param g contexto gráfico para renderização
  */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);       
        Graphics2D graficos = (Graphics2D) g;

        if (emJogo == true) {
            if (fundo != null) {
                graficos.drawImage(fundo, 0, 0, getWidth(), getHeight(), this);
            }
            
            if (stars != null) {
                for (Stars star : stars) {
                    if (star != null && star.isVisivel()) {
                        if (star.getImage() == null) star.load();
                        if (star.getImage() != null) {
                            graficos.drawImage(star.getImage(), star.getX(), star.getY(), this);
                        }
                    }
                }
            }
            
            if (powerUpsFoguete != null) {
                for (PowerUpFoguete powerUp : powerUpsFoguete) {
                    if (powerUp != null && powerUp.isVisivel()) {
                        if (powerUp.getImage() == null) powerUp.load();
                        if (powerUp.getImage() != null) {
                            graficos.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(), this);
                        } else {
                            graficos.setColor(Color.ORANGE);
                            graficos.fillRect(powerUp.getX(), powerUp.getY(), 25, 25);
                            graficos.setColor(Color.RED);
                            graficos.drawString("F", powerUp.getX() + 8, powerUp.getY() + 18);
                        }
                    }
                }
            }
            
            if (powerUpsTurbo != null) {
                for (PowerUpTurbo powerUp : powerUpsTurbo) {
                    if (powerUp != null && powerUp.isVisivel()) {
                        if (powerUp.getImage() == null) powerUp.load();
                        if (powerUp.getImage() != null) {
                            graficos.drawImage(powerUp.getImage(), powerUp.getX(), powerUp.getY(), this);
                        } else {
                            graficos.setColor(Color.CYAN);
                            graficos.fillOval(powerUp.getX(), powerUp.getY(), 25, 25);
                            graficos.setColor(Color.WHITE);
                            graficos.drawString("T", powerUp.getX() + 10, powerUp.getY() + 15);
                        }
                    }
                }
            }

            if (player != null && player.isVisivel()) {
                graficos.drawImage(player.getImage(), player.getX(), player.getY(), this);    
            }

            if (player != null) {
                List<Tiro> tiros = player.getTiros();
                for (Tiro tiro : tiros) {
                    if (tiro != null && tiro.isVisivel()) {
                        if (tiro.getImage() != null) {
                            graficos.drawImage(tiro.getImage(), tiro.getX(), tiro.getY(), this);
                        } else {
                            if (tiro.isFoguete()) {
                                graficos.setColor(Color.GREEN);
                                graficos.fillRect(tiro.getX(), tiro.getY(), 15, 8);
                            } else {
                                graficos.setColor(Color.YELLOW);
                                graficos.fillRect(tiro.getX(), tiro.getY(), 10, 5);
                            }
                        }
                    }
                }
            }
            
            if (enemy1 != null) {
                for (Enemy1 in : enemy1) {
                    if (in != null && in.isVisivel()) {
                        if (in.getImage() == null) in.load();
                        if (in.getImage() != null) {
                            graficos.drawImage(in.getImage(), in.getX(), in.getY(), this);
                        }
                    }
                }
            }
            
            if (bossAtivo && boss != null && boss.isVisivel()) {
                graficos.drawImage(boss.getImagem(), boss.getX(), boss.getY(), this);
                
                graficos.setColor(Color.RED);
                graficos.fillRect(boss.getX(), boss.getY() - 15, boss.largura, 10);
                graficos.setColor(Color.GREEN);
                double vidaPorcentagem = (double) boss.getVida() / 1000.0;
                graficos.fillRect(boss.getX(), boss.getY() - 15, (int)(boss.largura * vidaPorcentagem), 10);
                graficos.setColor(Color.WHITE);
                graficos.drawRect(boss.getX(), boss.getY() - 15, boss.largura, 10);
                
                List<BossTiro> tirosBoss = boss.getTiros();
                for (BossTiro bTiro : tirosBoss) {
                    if (bTiro != null && bTiro.getImagem() != null && bTiro.isVisivel()) {
                        graficos.drawImage(bTiro.getImagem(), bTiro.getX(), bTiro.getY(), this);
                    }
                }
            }
         
            if (player != null) {
                desenharHUD(graficos);
            }
            
        } else {
            ImageIcon fimJogo = new ImageIcon("res\\Image 16 de set. de 2025, 17_37_50 (1).png");
            graficos.drawImage(fimJogo.getImage(), 0, 0, getWidth(), getHeight(), this);
            
            if (mostrarBotaoContinuar) {
                graficos.setColor(Color.DARK_GRAY);
                graficos.fillRect(botaoContinuar.x, botaoContinuar.y, botaoContinuar.width, botaoContinuar.height);
                
                graficos.setColor(Color.WHITE);
                graficos.drawRect(botaoContinuar.x, botaoContinuar.y, botaoContinuar.width, botaoContinuar.height);
                
                graficos.setFont(new Font("Arial", Font.BOLD, 24));
                graficos.setColor(Color.WHITE);
                graficos.drawString("CONTINUAR", botaoContinuar.x + 30, botaoContinuar.y + 35);
            }   
        }
    }
       
    /**
   * Método principal do loop de jogo (chamado pelo Timer).
   * 
   * <p>Atualiza todos os elementos do jogo, gerencia spawn de novos objetos,
   * controla aparição do chefe e verifica estado do jogo.</p>
   * 
   * @param e evento de ação do timer
    */
    
    @Override
    public void actionPerformed(ActionEvent e) {    
        if (!bossAtivo && (System.currentTimeMillis() - tempoInicialJogo) > 90000) {
            inicializaBoss();
        }
    
        if (emJogo) {
            if (player != null) {
                player.update();
                if(player.isTurbo()) {
                    time.setDelay(2);
                } else {
                    time.setDelay(5);
                }
                
                spawnNovoInimigo();
                spawnNovaEstrela();
                spawnNovoPowerUpFoguete();
                spawnNovoPowerUpTurbo();
            }
            
            for (int p = stars.size() - 1; p >= 0; p--) {
                Stars on = stars.get(p);
                if(on.isVisivel()) {
                    on.update();
                } else {
                    stars.remove(p);
                }
            }
            
            for (int p = powerUpsFoguete.size() - 1; p >= 0; p--) {
                PowerUpFoguete powerUp = powerUpsFoguete.get(p);
                if(powerUp.isVisivel()) {
                    powerUp.update();
                } else {
                    powerUpsFoguete.remove(p);
                }
            }
            
            for (int p = powerUpsTurbo.size() - 1; p >= 0; p--) {
                PowerUpTurbo powerUp = powerUpsTurbo.get(p);
                if(powerUp.isVisivel()) {
                    powerUp.update();
                } else {
                    powerUpsTurbo.remove(p);
                }
            }
            
            for (int o = enemy1.size() - 1; o >= 0; o--) {
                Enemy1 in = enemy1.get(o);
                if(in.isVisivel()) {
                    in.update();
                } else {
                    enemy1.remove(o);
                }
            }
            
            if (bossAtivo && boss != null && boss.isVisivel()) {
                boss.update();
                
                List<BossTiro> tirosBoss = boss.getTiros();
                for (int i = tirosBoss.size() - 1; i >= 0; i--) {
                    BossTiro bTiro = tirosBoss.get(i);
                    if (bTiro.isVisivel()) {
                        bTiro.update();
                    } else {
                        tirosBoss.remove(i);
                    }
                }
            } else if (bossAtivo && boss != null && !boss.isVisivel()) {
                bossAtivo = false;
                System.out.println("Chefe derrotado!");
            }
           
            checarColisoes();
        } else {
            mostrarBotaoContinuar = true;
        }
        repaint();
    }
    
    /**
     * Verifica e processa todas as colisões do jogo.
     * 
     * <p>Sistema centralizado que detecta colisões entre todos os elementos:
     *  jogador vs inimigos, tiros vs inimigos, jogador vs boss, tiros vs boss,
     *  jogador vs power-ups. Aplica danos, remove objetos e aciona efeitos sonoros.</p>
     */
    public void checarColisoes() {
        if (player == null || !player.isVisivel()) {
            return;
        }
        
        Rectangle formaNave = player.getBounds();
        Rectangle formaEnemy1;
        Rectangle formaTiro;
        
        if (bossAtivo && boss != null && boss.isVisivel()) {
            Rectangle formaBoss = boss.getBounds();
            
            List<Tiro> tiros = player.getTiros();
            for (int j = 0; j < tiros.size(); j++) {
                Tiro tempTiro = tiros.get(j);
                if (tempTiro.isVisivel()) {
                    formaTiro = tempTiro.getBounds();
                    if (formaTiro.intersects(formaBoss)) {
                        if (tempTiro.isFoguete()) {
                            boss.receberDano(30);
                        } else {
                            boss.receberDano(10);
                        }
                        tempTiro.setIsVisivel(false);
                       
                    }
                }
            }
            
            List<BossTiro> tirosBoss = boss.getTiros();
            for (int k = 0; k < tirosBoss.size(); k++) {
                BossTiro bTiro = tirosBoss.get(k);
                if (bTiro.isVisivel()) {
                    Rectangle formaBossTiro = bTiro.getBounds();
                    if (formaBossTiro.intersects(formaNave)) {
                        bTiro.setIsVisivel(false);
                        boolean aindaVivo = player.isVivo();
                        // SOM removido - sem som específico para player dano
                        if (!aindaVivo) {
                            emJogo = false;
                        }
                    }
                }
            }
            
            if (formaNave.intersects(formaBoss)) {
                if(!player.isTurbo()) {
                    boolean aindaVivo = player.isVivo();
                    // SOM removido - sem som específico para player dano
                    if (!aindaVivo) {
                        emJogo = false;
                    }
                }
            }
        }
        
        for(int i = 0; i < enemy1.size(); i++) {
            Enemy1 tempEnemy1 = enemy1.get(i);
            if (tempEnemy1.isVisivel()) {
                formaEnemy1 = tempEnemy1.getBounds();
                if(formaNave.intersects(formaEnemy1)) {
                    if(player.isTurbo()) {
                        tempEnemy1.setIsVisivel(false);
                        audioManager.tocarExplosao(); // SOM: Inimigo destruído
                    } else {
                        tempEnemy1.setIsVisivel(false);
                        boolean aindaVivo = player.isVivo();
                        // SOM removido - sem som específico para player dano
                        if (!aindaVivo) {
                            emJogo = false;
                        }
                        return;
                    }
                }
            }
        }
        
        List<Tiro> tiros = player.getTiros();
        for(int j = 0; j < tiros.size(); j++) {
            Tiro tempTiro = tiros.get(j);
            if (tempTiro.isVisivel()) {
                formaTiro = tempTiro.getBounds();
                for(int o = 0; o < enemy1.size(); o++) {
                    Enemy1 tempEnemy1 = enemy1.get(o);       
                    if (tempEnemy1.isVisivel()) {
                        formaEnemy1 = tempEnemy1.getBounds();
                        if(formaTiro.intersects(formaEnemy1)) {
                            tempEnemy1.setIsVisivel(false);
                            tempTiro.setIsVisivel(false);
                            audioManager.tocarExplosao(); // SOM: Inimigo destruído
                            break;
                        }
                    }
                }
            }
        }
        
        for(int i = 0; i < powerUpsFoguete.size(); i++) {
            PowerUpFoguete powerUp = powerUpsFoguete.get(i);
            if (powerUp.isVisivel()) {
                Rectangle formaPowerUp = powerUp.getBounds();
                if(formaNave.intersects(formaPowerUp)) {
                    powerUp.setIsVisivel(false);
                    player.coletarFoguetes();
                    audioManager.tocarPowerUp(); // SOM: Power-up coletado
                    break;
                }
            }
        }
        
        for(int i = 0; i < powerUpsTurbo.size(); i++) {
            PowerUpTurbo powerUp = powerUpsTurbo.get(i);
            if (powerUp.isVisivel()) {
                Rectangle formaPowerUp = powerUp.getBounds();
                if(formaNave.intersects(formaPowerUp)) {
                    powerUp.setIsVisivel(false);
                    player.coletarTurbos();
                    audioManager.tocarPowerUp(); // SOM: Power-up coletado
                    break;
                }
            }
        }
    }
      
    private class TecladoAdapter extends KeyAdapter {
        
        @Override
        public void keyPressed(KeyEvent e) {
            if (player != null) {
                player.KeyPressed(e);
            }
            
            // CONTROLES DE ÁUDIO
            int codigo = e.getKeyCode();
            
            // M: Liga/Desliga música
            if (codigo == KeyEvent.VK_M) {
                audioManager.setMusicaAtiva(!audioManager.isMusicaAtiva());
            }
            
            // E: Liga/Desliga efeitos
            if (codigo == KeyEvent.VK_E) {
                audioManager.setEfeitosAtivos(!audioManager.isEfeitosAtivos());
            }
            
            // +: Aumentar volume
            if (codigo == KeyEvent.VK_PLUS || codigo == KeyEvent.VK_EQUALS) {
                float novoVolume = Math.min(1.0f, audioManager.getVolumeMusica() + 0.1f);
                audioManager.setVolumeMusica(novoVolume);
                audioManager.setVolumeEfeitos(novoVolume);
            }
            
            // -: Diminuir volume
            if (codigo == KeyEvent.VK_MINUS) {
                float novoVolume = Math.max(0.0f, audioManager.getVolumeMusica() - 0.1f);
                audioManager.setVolumeMusica(novoVolume);
                audioManager.setVolumeEfeitos(novoVolume);
            }
        }  
        
        @Override
        public void keyReleased(KeyEvent e) {
            if (player != null) {
                player.KeyRelease(e);
            }
        }
    }
}