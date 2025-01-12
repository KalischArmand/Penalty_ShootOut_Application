const socket = new WebSocket("ws://localhost:8080/game");

socket.onopen = () => {
    console.log("Conectat la server.");
};

socket.onmessage = (event) => {
    console.log("Mesaj primit: ", event.data);
};

socket.onclose = () => {
    console.log("Deconectat de la server.");
};