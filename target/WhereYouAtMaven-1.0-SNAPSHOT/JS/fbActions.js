/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


           var App = App || {};

            // When user logs out hide logout button and remove user data
            App.logOut = function(){
              $(".logged-out").show();
              $(".logged-in").hide();
              $("#welcome").text("");
              $("#profilepic").html("");
              if(Parse.User.current()) {
                Parse.User.logOut();
                // Unfortunately due to a problem with the interaction between Parse and Facebook's SDKs
                // we need to log the user out of Facebook too [as of July 16, 2013]
                FB.logout();
              }
              window.location = "logout.xhtml";
            };

            // When user logs in show logout button, user name and pic
            App.logIn = function(){
              $(".logged-out").hide();
              $(".logged-in").show();
              $("#welcome").text("Welcome " + Parse.User.current().get('name'));
              $("#profilepic").html('<img src="https://graph.facebook.com/' + Parse.User.current().attributes.authData.facebook.id + '/picture">')
              //window.location = "welcomePrimefaces.xhtml";
              post('fbLogin.xhtml', {email: Parse.User.current().get('email'), firstname: Parse.User.current().get('firstname'), lastname: Parse.User.current().get('lastname'), username: Parse.User.current().get('username')});
            };
            
            function post(path, params, method) {
                method = method || "post"; // Set method to post by default if not specified.

                // The rest of this code assumes you are not using a library.
                // It can be made less wordy if you use one.
                var form = document.createElement("form");
                form.setAttribute("method", method);
                form.setAttribute("action", path);

                for(var key in params) {
                    if(params.hasOwnProperty(key)) {
                        var hiddenField = document.createElement("input");
                        hiddenField.setAttribute("type", "hidden");
                        hiddenField.setAttribute("name", key);
                        hiddenField.setAttribute("value", params[key]);

                        form.appendChild(hiddenField);
                     }
                }

                document.body.appendChild(form);
                form.submit();
            }

            // Initialize Parse with Parse app_id and javascript_key
            Parse.initialize("wMNwNpfj87bX3Dia3mDqXiczJkmay2q9xNN7Fa6s", "GiJ5WaiGX9VIR8GDnqE2B0x8vCN4qvPkta30Tmrb");

            // Initialize the Facebook SDK with Parse as described at
            // https://parse.com/docs/js_guide#fbusers
            window.fbAsyncInit = function() {
              // init the FB JS SDK
              Parse.FacebookUtils.init({
                appId      : '777332015693683', // Facebook App ID
                channelUrl : 'http://178.78.213.33:8081/WhereYouAt/faces/channel.html', // Channel File
                status     : true,  // check Facebook Login status
                cookie     : true,  // enable cookies to allow Parse to access the session
                xfbml      : true,  // initialize Facebook social plugins on the page
                version    : 'v2.1' // point to the latest Facebook Graph API version
              });
            };

            // Post initialization - if we have a current user let's show the logged in view
            // Additional post init code can be added here

            

            (function(d, s, id){
                var js, fjs = d.getElementsByTagName(s)[0];
                if (d.getElementById(id)) {return;}
                js = d.createElement(s); js.id = id;
                js.src = "//connect.facebook.net/en_US/sdk.js";
                fjs.parentNode.insertBefore(js, fjs);
              }(document, 'script', 'facebook-jssdk'));

            $(document).ready(function() {
              $("#logout-button").click(function() {
                console.log("Logout");
                App.logOut();
              });
            })