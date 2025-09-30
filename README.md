# Space Cosmic 🚀  

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)  
![IDE NetBeans](https://img.shields.io/badge/IDE-NetBeans-blue?style=for-the-badge&logo=apache-netbeans-ide&logoColor=white)  
![License](https://img.shields.io/badge/license-Personal-green?style=for-the-badge)  
![Status](https://img.shields.io/badge/status-Em%20Desenvolvimento-yellow?style=for-the-badge)  

Um jogo de nave espacial desenvolvido em **Java** com **Swing/AWT**, onde o jogador deve sobreviver a ondas de inimigos e derrotar o chefe final.  

---

## 📋 Sobre o Projeto  

**Space Cosmic** é um space shooter clássico desenvolvido como meu primeiro de muitos projetos pessoais, combinando mecânicas de jogos arcade com implementações modernas de física e áudio.  

---

## 🎮 Como Jogar  

**Controles:**  
- **Setas** → Movimentação da nave  
- **A** → Atirar (consome munição de foguetes)  
- **SPACE** → Ativar turbo (aumenta velocidade por 5 segundos)  
- **M** → Liga/Desliga música  
- **E** → Liga/Desliga efeitos sonoros  
- **+/-** → Ajustar volume  

**Objetivo:**  
- Sobreviva às ondas de inimigos  
- Colete power-ups de munição e turbo  
- Derrote o chefe final que aparece após 90 segundos  

---

## 🛠️ Tecnologias Utilizadas  

- **Linguagem:** Java  
- **Interface Gráfica:** Swing/AWT  
- **Sistema de Áudio:** javax.sound.sampled  
- **IDE:** NetBeans  

---

## 📁 Estrutura do Projeto  
MeuJogo1.0/
├── src/
│ └── meujogo/
│ ├── GameWindow.java
│ └── Modelo/
│ ├── AudioManager.java
│ ├── Boss.java
│ ├── Enemy1.java
│ ├── Fase.java
│ ├── Player.java
│ ├── Tiro.java
│ └── [outros arquivos]
├── res/
│ ├── audio/ # Arquivos de som
│ └── [imagens] # Sprites do jogo
├── dist/
└── javadoc/ # Documentação gerada
---

## 🎨 Recursos  

- Sistema completo de física para projéteis  
- Herança de velocidade nos tiros  
- Sistema de power-ups  
- Boss fight com barra de vida  
- Sistema de áudio com 5 sons (música de fundo, tiro, turbo, power-up, explosão)  
- HUD informativo com vidas, munição e turbos  

---

## 🚀 Como Executar  

1. Clone o repositório  
2. Abra o projeto no NetBeans ou em qualquer IDE  
3. Execute a classe **GameWindow.java**  

Ou execute o JAR diretamente:  

```bash
java -jar dist/MeuJogo1.0.jar

📖 Documentação

A documentação JavaDoc completa está disponível em:
dist/javadoc/index.html

👨‍💻 Desenvolvimento

Estrutura base: Tutorial do YouTube de desenvolvimento de jogos em Java

Melhorias e correções: Implementadas com auxílio de IA (Claude)

Principais modificações:

Sistema completo de áudio

Física realística dos projéteis

Sistema de colisões otimizado

Mecânica de turbo balanceada

Boss fight implementado

📝 Licença

Projeto pessoal desenvolvido por Davi Paulino Conceição - 2025

🙏 Agradecimentos

Tutorial do YouTube de desenvolvimento de jogos em Java → https://www.youtube.com/playlist?list=PLlW3qrNjsvBwUmUk9kio7bNT3GR554FH5

Claude AI pelo auxílio em debugging e otimizações.
