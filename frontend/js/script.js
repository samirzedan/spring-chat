document.addEventListener("DOMContentLoaded", () => {
    // Referências aos elementos do DOM
    const chatApp = document.getElementById('chat-app');
    const loaderContainer = document.getElementById('loader-container');
    const messageForm = document.getElementById('message-form');
    const messageInput = document.getElementById('message-input');
    const greetingsDiv = document.getElementById('greetings');
    const statusDot = document.getElementById('status-dot');
    const statusText = document.getElementById('status-text');

    let stompClient = null;

    // Função para atualizar a UI de status
    const updateStatus = (connected, text) => {
        statusText.textContent = text;
        statusDot.classList.toggle('connected', connected);
        document.querySelector("#message-form button").disabled = !connected;
    };

    // Função para exibir uma mensagem na tela
    const showMessage = (message) => {
        const messageElement = document.createElement('div');
        messageElement.className = 'message';
        messageElement.appendChild(document.createTextNode(message));
        greetingsDiv.appendChild(messageElement);
        // Rola para a mensagem mais recente
        greetingsDiv.scrollTop = greetingsDiv.scrollHeight;
    };

    // Função para enviar mensagem
    const sendMessage = (event) => {
        event.preventDefault();
        const messageContent = messageInput.value.trim();
        if (messageContent && stompClient) {
            stompClient.send("/app/hello", {}, JSON.stringify({ 'name': messageContent }));
            messageInput.value = '';
        }
    };
    
    messageForm.addEventListener('submit', sendMessage);

    // Função de conexão assíncrona
    const connect = async () => {
        try {
            // Retorna uma Promise que resolve ou rejeita na conexão
            await new Promise((resolve, reject) => {
                const socket = new SockJS('https://spring-chat-api.samirdev.com.br/ws');
                stompClient = Stomp.over(socket);
                
                // Desabilita o logging do STOMP no console
                stompClient.debug = null;

                stompClient.connect({}, 
                    (frame) => resolve(frame), // Sucesso
                    (error) => reject(error)   // Erro
                );
            });

            // Se a Promise resolveu (conectou com sucesso)
            console.log('Conectado com sucesso!');
            updateStatus(true, 'Conectado');
            loaderContainer.classList.add('hidden');
            chatApp.classList.remove('hidden');

            stompClient.subscribe('/topic/greetings', (greeting) => {
                showMessage(JSON.parse(greeting.body).content);
            });

        } catch (error) {
            // Se a Promise rejeitou (falha na conexão)
            console.error('Falha na conexão:', error);
            updateStatus(false, 'Falha na conexão');
            loaderContainer.querySelector('p').textContent = 'Não foi possível conectar ao servidor. Tente recarregar a página.';
            loaderContainer.querySelector('.loader').classList.add('hidden');
        }
    };

    // Inicia a conexão assim que a página carregar
    connect();
});