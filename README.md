# Gamepad "Simulator"
- Este projeto visa a conversão de sinais analógicos e digitais, que serão inicialmente dados pelos módulos de um arduino (Potenciômetro, Joystick, Sensor de Luminosidade, etc) para um Gamepad.
A conversão não pode ocorrer direta neste caso, pois consideramos um Arduino UNO que possui um `ATmega`, que não capaz de simular funções do teclado, diferente do Arduino Leonard (entre outros como Due, Esplora, Zero, etc), que possui o controlador `32u4`. [Ver mais](https://www.arduino.cc/reference/en/language/functions/usb/keyboard/).

- O projeto é dividido em três principais etapas/conversões:
- - Arduino <--> Java
- - - Nesta etapa é utilizado a biblioteca [JSerialComm](https://fazecast.github.io/jSerialComm/), que consegue reconhecer os sinais recebidos pela USB (ou demais portas seriais) e transformar em dados úteis que serão processados no programa. Teoricamente a biblioteca consegue atuar em vários Sistemas Operacionais, dentre eles Windows, Linux, Android, entretanto, ao realizar o teste em um Sistema Linux, mais especificamente no Linux Mint 20.4, o programa apresentou falha, pois não conseguiu abrir a porta. Outras bibliotecas como o [RXTX](http://rxtx.qbang.org/wiki/index.php/Main_Page) ou o [Java Simple Serial Connector](https://github.com/scream3r/java-simple-serial-connector) não conseguiram listar e consequentemente conectar as portas.
---
- - Java <--> Gamepad (Ainda em desenvolvimento)
- - - A ideia desta etapa é fazer o programa emule sinais de um gamepad. A conversão deve ser bidirecional, ou seja, caso o Jogo envie o sinal de vibração para o controle emulado uma mensagem deve ser enviada ao Arduino para ser tratada devidamente.

---
- - Gamepad <--> Jogo
- - - Esta acredito ser a parte mais complicada do projeto. A priori deve ser escolhido uma biblioteca (ou algo similar) que consiga fazer o sistema operacional reconhecer o meu aplicativo como um controle, similar ao [x360ce](https://github.com/x360ce/x360ce). A responsável pelos jogos conseguirem capturar os sinais enviados pelos Gamepad é o XInput (reconhecido como controle Xbox) ou DirectInput, atualmente o XInput é a melhor opção por possuir mais compatibilidades com jogos. Para conseguir emular um gamepad será necessário o [ViGEBus](https://github.com/ViGEm/ViGEmBus/), que ainda não faço a mínima ideia de como utilizá-lo em meu programa.

### De forma ilustrativa o que pretendo constriuir é:
![Ilustracao](https://i.imgur.com/BZulMAI.png)

# Como executar?
- Este projeto está sendo desenvolvido utilizando a IDE "IntelliJ IDEA". Versão 2022.1.4
- O projeto segue a estrutura padrão de um projeto Maven, as dependências necessárias devem ser instaladas, certifique-se de possuir o [Maven](https://maven.apache.org/download.cgi).
- O programa está sendo desenvolvido sobre o JDK11, por ser uma versão altamente estável.
