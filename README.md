# ZoomApiIntegration
a sample project for Zoom Api Integration

to redirect the request to the local server I have user NGROK server.

Just install NGROK server from https://dashboard.ngrok.com/get-started

the commands used are following

./ngrok http 8080

After you start the ngrok server you need to change the redirect url in the Zoom -> Manage App -> App Credentails
(This is due to the fact the free version of NGROK server generates random url everytime it is restarted)
