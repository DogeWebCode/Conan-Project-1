# STYLiSH

## How to Start the Server on Port 80

### WebSiteURL：http://3.113.167.117/

Initially, I considered using the authbind approach. However, I later felt that relying on a package might not be stable in the long term. If authbind suddenly stops being maintained, all my servers using authbind would fail.

Therefore, I opted for the following method:

> sudo iptables -t nat -A PREROUTING -p tcp --dport 80 -j REDIRECT --to-ports 8080

By redirecting traffic from port 80 to port 8080, you can connect to the web server directly via HTTP.

## How to Run the Web Server in the Background

You can use screen, which is available on most Linux distributions, to keep your web server running even if your SSH connection is interrupted.

If screen is not installed, you can install it with the following command:

> sudo apt-get install screen

To use screen, simply execute the following in the terminal:

> screen

Once inside screen, it acts like a normal terminal. After starting your server, you can detach from the current screen session and return to the main terminal with the following key sequence:

> Ctrl + A, then D

To reattach to the screen session, you can use:

> screen -r

If you want to close the screen session, you can use the exit command, or you can kill the session with the following key sequence:

> Ctrl + A, then K (kill)

This way, even if the SSH connection drops, your web server continues to run in the background.
