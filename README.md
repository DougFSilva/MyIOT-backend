# MyIOT-backend
O projeto é uma plataforma na qual o usuário cria uma conta, se conecta e cadastra dispositivos que poderão enviar dados para um Broker MQTT e receber comandos do tipo analógico e digital. A API então será responsável por receber os dados do Broker e por salvar as informações no banco de dados, e também oferecer uma interface para envio de comandos para os dispositivos. 

# 🚀 Começando
Essas instruções permitiram que você instale e utilize a API em sua máquina desejada

## ⚙️ **Configurando e executando**

## 1. Configurar usuário e senha do Mosquitto para acesso às configurações de segurância dinâmica

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
## 🔧 **Utilizando**
  







