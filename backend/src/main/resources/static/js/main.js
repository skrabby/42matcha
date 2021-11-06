'use strict';


var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('#connecting');

var stompClient = null;
var username = null;
var senderID = null;
 

function connect() {
    username = document.querySelector('#username').innerText.trim();
    senderID = document.querySelector('#senderID').innerText.trim();
     
    var socket = new SockJS("/ws");
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected(), onError);
}

// Connect to WebSocket Server.
connect();

function onConnected() {
    // Subscribe to the Public Topic
     stompClient.subscribe(
          "/user/" + senderID + "/queue/messages",
          onMessageReceived
        );
}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    if(messageContent && stompClient) {
        var message = {
            sender: username,
            content: messageInput.value,
            timestamp: new Date(),
            type: 'CHAT'
        };
        stompClient.send("/app/chat", {}, JSON.stringify(message));
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    messageElement.classList.add('chat-message');
    var usernameElement = document.createElement('strong');
    usernameElement.classList.add('nickname');
    var usernameText = document.createTextNode(message.sender);
    var usernameText = document.createTextNode(message.sender);
    usernameElement.appendChild(usernameText);
    messageElement.appendChild(usernameElement);


    var textElement = document.createElement('span');
    var messageText = document.createTextNode(message.content);
    textElement.appendChild(messageText);

    messageElement.appendChild(textElement);

    messageArea.appendChild(messageElement);
    messageArea.scrollTop = messageArea.scrollHeight;
}
 
 
messageForm.addEventListener('submit', sendMessage, true);