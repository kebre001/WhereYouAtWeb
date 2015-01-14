/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


    var wsUri = "ws://130.237.84.233:8090";
    var output;
    function init() { output = document.getElementById("output"); testWebSocket();
    }

    function testWebSocket()
    {
            websocket = new WebSocket(wsUri);
            websocket.onopen = function(evt) { onOpen(evt) };
            websocket.onclose = function(evt) { onClose(evt) };
            websocket.onmessage = function(evt) { onMessage(evt) };
            websocket.onerror = function(evt) { onError(evt) };
    }

    function onOpen(evt)
    {
            //writeToScreen("CONNECTED");
            //doSend("WebSocket rocks"); //Causing error

            websocket.send(document.getElementById('from').value,document.getElementById('to').value,document.getElementById('value').value);
    }

    function onClose(evt)
    {
            writeToScreen("DISCONNECTED");
    }
    function onMessage(evt)
    {
            writeToScreen('<span style="color: blue;"> ' + evt.data+'</span>');
                //setTimeout(function() {	websocket.close();}, 20000);
    }

    function onError(evt)
    {
            writeToScreen('<span style="color: red;">ERROR:</span> ' + evt.data);

    }
    function doSend(from, to, message)
    {
            writeToScreen('<span style="color: green;">SENT: ' + message + '</span>');
            var strucMessage = from + '<:>' + to + "<:>" + message;
            websocket.send(strucMessage);
    }
    function writeToScreen(message)
    {
            var pre = document.createElement("p");
            var child = document.getElementById('parentOut');
            pre.style.wordWrap = "break-word";
            pre.innerHTML = message; 
            child.insertBefore(pre, child.childNodes[0]);
    }
    
    function oneSecondFunction() {
    // stuff you want to do every second
    console.log("1");
    }
    
    $(function(){
    setInterval(oneSecondFunction, 1000);
    });

    window.addEventListener("load", init, false);