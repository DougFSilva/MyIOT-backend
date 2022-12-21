# MyIOT-backend
O projeto é uma plataforma para controle e medição de dispositivios IOT, onde o usuário logado pode cadastrar dispositivos privados do tipo analógico, digital ou dispositivo de medição. Para os dispositivos do tipo analógico ou discreto é possível enviar comandos e receber a atualização do estado ou valor em tempo real via websocket, persistindo-os no banco de dados. Para os dispositivos de medição os valores medidos são persistidos e enviados também via websocket para visualização em tempo real por meio de gráfico e tabela.

Na pasta frontend se encontra uma aplicação de frontend desenvolvida com Angular. Veja a seguir algumas imagens:

Exemplo de tela de um dispositivos de medição ![tela de dispositivo de medição](./images/exemplo-tela-medicao.jpg)

Exemplo de tela de cadastro de um dispositivo de medição ![tela e cadastro de um dispositivo de medição](./images/exemplo-tela-medicao-cadastro.jpg)

Exemplo de tela de um dispositivo discreto ![tela de dispositivo discreto](./images/exemplo-tela-discreto.jpg)

Exemplo de tela de um dispositivo analógico ![tela de dispositivo analógico](./images/exemplo-tela-analogico.jpg)

Também foi criada uma biblioteca em C++ para facilitar a utilização com arduino, ESP8266 ou ESP32. Acesse [Biblioteca para arduino/ESP](https://github.com/DougFSilva/MyIOT-arduino-esp)

Repositório para o código do frontend em Angular. Acesse [MyIOT-frontend](https://github.com/DougFSilva/MyIOT-frontend)

# 🚀 Começando
Essas instruções permitiram que você instale e utilize a API em sua máquina desejada

## ⚙️ **Configurando e executando**

## 1. Configurar usuário e senha do Mosquitto dynamic security

Baixe a pasta completa do projeto, abra pasta mosquitto e o arquivo Dockerfile. Neste arquivo, configure como desejar as variáveis ADMIN_USERNAME e ADMIN_PASS.

## 2. Configurar as variáveis de ambiente

Agora, abra o arquivo docker-compose.yml na raiz do projeto e configure as seguintes variáveis de ambiente:

- **MONGO_INITDB_ROOT_USERNAME** - Usuário admin do mongo
- **MONGO_INITDB_ROOT_PASSWORD** - Senha do usuário admin do mongo
- **MONGODB_USERNAME** - Usuário admin do mongo
- **MONGODB_PASSWORD** - Senha do usuário admin do mongo

<span style="color:orange">**Obs.:** As variáveis MONGO_INITDB_ROOT_USERNAME e MONGODB_USERNAME devem ser iguais, e as variáveis MONGO_INITDB_ROOT_PASSWORD e MONGODB_PASSWORD também devem ser iguais, pois se tratam do mesmo usuário e senha, porém em containers diferentes:</span>
- **MONGODB_DATABASE** - Nome do database
- **USER_MASTER_PASSWORD** - Senha do usuário master do sistema
- **USER_MASTER_MQTTPASSWORD** - Senha do client gerado no broker mqtt para o usuário master do sistema
- **MQTT_SYSTEM_PASSWORD** - Senha do client mqtt para que o sistema acesse o broker mosquitto
- **MQTT_DYNSEC_USERNAME** - Usuário admin do dynamic security do broker mosquitto. Deve ser o mesmo configurado no Dockerfile do passo 1
- **MQTT_DYNSEC_PASSWORD** - Senha do usuário admin do dynamic security do broker mosquitto. Deve ser a mesma configurada no Dockerfile do passo 1
- **JWT_SECRET** - String secreta para geração do token JWT

## 3. Executar o Docker Compose

Com o terminal aberto na pasta raiz do projeto execute o Docker Compose. 
```
docker compose up
```
Concluída toda a instalação a aplicação ficará disponível no endereço e porta configurados do docker-compose.

## 🔧 **Entendendo e utilizando**

## 1. Veja a documentação

Com a API em execução, acesse o endpoint **"/swagger-ui/index.html"** e acesse a documentação com todos os endpoits disponíveis e suas descrições.

## 2. Informações gerais
* Ao criar um usuário, o mesmo só poderá criar dispositivos caso tenha sido aprovado. A aprovação é realizada pelo usuário **Master** criado automaticamente ao iniciar a aplicação, ou por qualquer usuário de perfil **ADMIN**. Por padrão todo usuário criado possui um perfim **SILVER_USER**, e a alteração do perfil para **ADMIN** ou **GOLD_USER** somente pode seer realizada pelo usuário **Master** ou qualquer outro usuário de perfil **ADMIN**. Lembrando que as credenciais do usuário **Master** foram configuradas no item **2. Configurar as variáveis de ambiente**. Automaticamente ao ser criado e aprovado, será gerado uma senha para acesso ao Broker Mosquitto. Para verificar a senha o usuário deve fazer login no sistema e e acessar o endpoint **"/user"** com o verbo **GET**

* Cada usuário pode cadastrar uma certa quantidade de dispositivos, dependendo de seu perfil, sendo:

   - **Dispositivo de controle analógico**: **ADMIN**(25), **GOLD_USER**(12), **SILVER_USER**(6)  

   - **Dispositivo de sinal discreto**: **ADMIN**(25), **GOLD_USER**(12), **SILVER_USER**(6)  

   - **Dispositivo de medição**: **ADMIN**(20), **GOLD_USER**(8), **SILVER_USER**(4)  

* Ao cadastrar um dispositivo, será gerado automaticamente uma permissão no Broker Mosquitto para que o usuário acesse o tópico do dispositivo. Somente o usuário tem acesso a esse tópico. Os tópicos seguem o seguinte padrão:
    
    - **iot/*<.tipo de dispositivo.>*/*<.id do dispositivo.>*** - Permite publicar e se inscrever, porém não persiste no banco de dados

    - **iot/*<.tipo de dispositivo.>*/*<.id do dispositivo.>*/ persist** - Permite publicar e se inscrever e persiste no banco de dados

    Onde o **tipo do dispositivo** pode ser:
    
    - **AnalogOutputDevice** - Para dispositivo de comando analógico
    - **DiscreteDevice** - Para dispositivos de sinais discretos
    - **MeasuringDevice** - Para dispositivos de medição


    E o **id do dispositivo** é número de identificação do dispositivo, que pode ser obtido nos endpoints **/analog-output-device/all**, **/discrete-device/all** e **/measuring-device/all**

    Exemplo de tópico para um dispositivo de sinal discreto, com id 112d54s6aa8s95s48s:

    **iot/DiscreteDevice/112d54s6aa8s95s48s** 

     **iot/DiscreteDevice/112d54s6aa8s95s48s/persist**

* Para acessar a aplicação via websocket é preciso fazer uma conexão SocketJs no endpoint **"/myiot-websocket/?token=*<.token JWT.>*"**, onde <.token JWT.> é o token JWT recebido ao autenticar. Então se inscrever no tópico referente ao dispositivo a ser acessado, da seguinte forma:

    **/user/queue/message/*<.Id do dispositivo.>***


## 🛠️Construído com

* Spring boot
* Spring Security
* Json web token JWT
* MongoDb
* Websocket
* Docker
* Docker compose
* Eclipse Mosquitto
* OpenAPI
---
## ✒️ Autor
* Douglas Ferreira da Silva



