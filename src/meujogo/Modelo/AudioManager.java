/**
 * Gerenciador centralizado de áudio para o jogo Space Cosmic
 * 
 * <p> Esta classe implementa o padrão Singleton para fornecer controle centralizado
 * de todos os recursos de áudio do jogo, incluindo música de fundo e efeitos sonoros.
 * Utiliza a API javax.sound.sampled para reprodução de arquivos WAV.</p>
 * 
 * <p><strong>Funcionalidades principais:</strong></p>
 * <ul.
 *   <li>Carregamneto e gerenciamneto de arquivos de áudio WAV</li>
 *   <li>Controle de música de fundo com loop contínuo</li>
 *   <li>Reprodução de efeitos sonoros (tiros, explosoes, power-ups, turbo) </li>
 *   <li>Controle individual de volume para musicas e efeitos</li>
 *   <li>Sistema de Ligar/desligar para música e efeito separadamente</li>
 * </ul>
 * 
 * <p><strong> Desenvolvimento:</strong></p>
 * <ul>
 *   <li>Implementação completa: Desenvolvida com auxilio de IA (claude)</li>
 *   <li>Padrão utilizado: Sigleton para acesso global</li>
 *   <li>Principais caracteríss: Sistema robustocom tratamentos de erros</li>
 * </ul>
 * 
 *  @author Davi Paulino Conceição
 * @version 1.0
 * @since 2025
 * 
 */
package meujogo.Modelo;

///// IMPORTES////
import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class AudioManager {
    private static AudioManager instance;
    private Map<String, Clip> clips;
    private Clip musicaFundo;
    private boolean musicaAtiva = true;
    private boolean efeitosAtivos = true;
    private float volumeMusica = 0.8f;
    private float volumeEfeitos = 1.0f;
    
 /**
 * 
 * Construtor privado para implementação do padrão Singleton.
 * 
 * <p>Inicializa o mapa de clips de áudio e carrega todos os sons do jogo.</p>
 */
 
    private AudioManager() {
        clips = new HashMap<>();
        carregarSons();
    }
    
  /**
 * Retorna a instância única do AudioManager (padrão Singleton).
 * 
 * @return a instância única do AudioManager
 */
    public static AudioManager getInstance() {
        if (instance == null) {
            instance = new AudioManager();
        }
        return instance;
    }
    
  /**
 * Carrega todos os arquivos de áudio necessários para o jogo.
 * 
 * <p>Carrega música de fundo e efeitos sonoros essenciais:
 * tiro, turbo, powerup e explosão.</p>
 */
    
    private void carregarSons() {
        try {
            // Carregar música de fundo
            carregarSom("musica_fundo", "res/audio/musica_fundo.wav");
            
            // Carregar apenas os 5 efeitos sonoros essenciais
            carregarSom("tiro", "res/audio/tiro.wav");
            carregarSom("turbo", "res/audio/turbo.wav");
            carregarSom("powerup", "res/audio/powerup.wav");
            carregarSom("explosao", "res/audio/explosao.wav");
            
            System.out.println("Sistema de áudio inicializado com sucesso!");
            
        } catch (Exception e) {
            System.out.println("Erro ao inicializar sistema de áudio: " + e.getMessage());
        }
    }
    
    private void carregarSom(String nome, String caminho) {
        try {
            File arquivo = new File(caminho);
            if (arquivo.exists()) {
                AudioInputStream audioStream = AudioSystem.getAudioInputStream(arquivo);
                Clip clip = AudioSystem.getClip();
                clip.open(audioStream);
                clips.put(nome, clip);
                System.out.println("Som carregado: " + nome);
            } else {
                System.out.println("Arquivo de áudio não encontrado: " + caminho);
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            System.out.println("Erro ao carregar som " + nome + ": " + e.getMessage());
        }
    }
      /**
 * Inicia a reprodução da música de fundo em loop contínuo.
 * 
 * <p>A música só será iniciada se estiver ativa e o arquivo estiver carregado.</p>
 */
    
    /**
 * Inicia a reprodução da música de fundo em loop contínuo.
 * 
 * <p>A música só será iniciada se estiver ativa e o arquivo estiver carregado.</p>
 */
    public void iniciarMusicaFundo() {
        if (musicaAtiva && clips.containsKey("musica_fundo")) {
            musicaFundo = clips.get("musica_fundo");
            if (musicaFundo != null) {
                ajustarVolume(musicaFundo, volumeMusica);
                musicaFundo.loop(Clip.LOOP_CONTINUOUSLY);
                System.out.println("Música de fundo iniciada");
            }
        }
    }
    
    public void pararMusicaFundo() {
        if (musicaFundo != null && musicaFundo.isRunning()) {
            musicaFundo.stop();
        }
    }
    
    /**
 * Reproduz um efeito sonoro específico.
 * 
 * <p>Para o som se já estiver tocando e reinicia do início,
 * permitindo sobreposição de efeitos.</p>
 * 
 * @param nomeEfeito nome do efeito sonoro a ser reproduzido
 */
    
    public void tocarEfeito(String nomeEfeito) {
        if (efeitosAtivos && clips.containsKey(nomeEfeito)) {
            try {
                Clip clip = clips.get(nomeEfeito);
                if (clip != null) {
                    // Para o som se já estiver tocando e reinicia
                    clip.stop();
                    clip.setFramePosition(0);
                    ajustarVolume(clip, volumeEfeitos);
                    clip.start();
                }
            } catch (Exception e) {
                System.out.println("Erro ao tocar efeito " + nomeEfeito + ": " + e.getMessage());
            }
        }
    }
    
    public void tocarTiro() {
        tocarEfeito("tiro");
    }
    
    public void tocarTurbo() {
        tocarEfeito("turbo");
    }
    
    public void tocarPowerUp() {
        tocarEfeito("powerup");
    }
    
    public void tocarExplosao() {
        tocarEfeito("explosao");    
    }
    
    /**
 * Ajusta o volume de um clip de áudio específico.
 * 
 * <p>Converte o valor de volume (0.0-1.0) para decibéis
 * e aplica ao controle de volume do clip.</p>
 * 
 * @param clip o clip de áudio a ter o volume ajustado
 * @param volume valor do volume entre 0.0 e 1.0
 */
    
    private void ajustarVolume(Clip clip, float volume) {
        try {
            FloatControl volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            // Converte de 0-1 para decibéis
            float dB = (float) (Math.log(volume) / Math.log(10.0) * 20.0);
            volumeControl.setValue(dB);
        } catch (Exception e) {
            System.out.println("Erro ao ajustar volume: " + e.getMessage());
        }
    }
    
    public void setVolumeMusica(float volume) {
        this.volumeMusica = Math.max(0.0f, Math.min(1.0f, volume));
        if (musicaFundo != null) {
            ajustarVolume(musicaFundo, this.volumeMusica);
        }
    }
    
    public void setVolumeEfeitos(float volume) {
        this.volumeEfeitos = Math.max(0.0f, Math.min(1.0f, volume));
    }
    
    public void setMusicaAtiva(boolean ativa) {
        this.musicaAtiva = ativa;
        if (!ativa && musicaFundo != null) {
            pararMusicaFundo();
        } else if (ativa) {
            iniciarMusicaFundo();
        }
    }
    
    public void setEfeitosAtivos(boolean ativos) {
        this.efeitosAtivos = ativos;
    }
    
    public void pararTodos() {
        pararMusicaFundo();
        for (Clip clip : clips.values()) {
            if (clip != null && clip.isRunning()) {
                clip.stop();
            }
        }
    }
    
    // Getters
    public boolean isMusicaAtiva() { return musicaAtiva; }
    public boolean isEfeitosAtivos() { return efeitosAtivos; }
    public float getVolumeMusica() { return volumeMusica; }
    public float getVolumeEfeitos() { return volumeEfeitos; }
}