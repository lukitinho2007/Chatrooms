
function fetchChatrooms() {
    fetch('/room')
        .then(response => response.json())
        .then(chatrooms => {
            const chatroomsList = document.getElementById('chatrooms-list');
            chatroomsList.innerHTML = '';
            chatrooms.forEach(chatroom => {
                const li = document.createElement('li');
                li.textContent = chatroom.name + ' (' + chatroom.members + ' members)';
                chatroomsList.appendChild(li);
            });
        });
}


function createChatroom() {
    const name = document.getElementById('chatroom-name').value;
    const maxMembers = document.getElementById('max-members').value;
    const data = { name, maxMembers };

    fetch('/room', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (response.status === 201) {
            fetchChatrooms();
        }
    });
}


function showCreateChatroomForm() {
    document.getElementById('create-chatroom-form').style.display = 'block';
}

const socket = new WebSocket('ws://localhost:8080/chat');


socket.onopen = function(event) {

};

socket.onmessage = function(event) {
    const message = JSON.parse(event.data);

    const messagesContainer = document.getElementById('messages-container');
    const div = document.createElement('div');
    div.textContent = message.username + ': ' + message.message;
    messagesContainer.appendChild(div);
};

socket.onclose = function(event) {

};


function sendMessage() {
    const username = document.getElementById('username').value;
    const message = document.getElementById('message').value;
    const data = { username, message };

    socket.send(JSON.stringify(data));
}