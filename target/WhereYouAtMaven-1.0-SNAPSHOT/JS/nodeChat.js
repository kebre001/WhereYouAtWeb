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
        console.log("CHECKING DATES FOR NEW MESSAGES");
            
            var GameScore = Parse.Object.extend("Messages");
            var query = new Parse.Query(GameScore);
            
            var GameScore2 = Parse.Object.extend("Messages");
            var query2 = new Parse.Query(GameScore2);
            
            query.equalTo("sender", document.getElementById("receiveOld:sender").value);
            query.equalTo("receiver", document.getElementById("receiveOld:receiver").value);
            
            query2.equalTo("receiver", document.getElementById("receiveOld:sender").value);
            query2.equalTo("sender", document.getElementById("receiveOld:receiver").value);
            
            var mainQuery = Parse.Query.or(query, query2);
            
            mainQuery.descending("createdAt");
            mainQuery.limit(1);
            
            mainQuery.find({
              success: function(results) {
                // Do something with the returned Parse.Object values
                var latestDate = new Date(latestMessage);
                for (var i = 0; i < results.length; i++) {
                    
                    var newDate = new Date(results[i].createdAt);
                    
                    if(newDate > latestDate){
                        var object = results[i];
                        console.log(object.get("sender") + ' - ' + object.get('message'));
                        if(object.get('sender') == document.getElementById("receiveOld:sender").value){
                            writeToScreen('<span style="color: green;">SENT: ' + object.get('message') + '</span>');
                        }else{
                            writeToScreen('<span style="color: blue;"> ' + object.get('message') + '</span>');
                        }
                        latestMessage = object.createdAt;
                        fetched++;
                    }else{
                        console.log("NO NEW MESSAGE TO FETCH");
                    }
                }
                console.log("LATEST MESSAGE: " + latestMessage);
              },
              error: function(error) {
                alert("Error: " + error.code + " " + error.message);
              }
            });
    }
    
    $(function(){
    setInterval(oneSecondFunction, 5000);
    });

    window.addEventListener("load", init, false);