
Implemented functionality
Client Side
1.	Login Game – YES 
2.	Exit Game – YES
3.	Enter ID (Only Integer) – YES
4.	Check if this ID is unique – YES
5.	Kick a Ball – YES
6.	Validate if player have permission to kick a Ball – YES
7.	Validate If User ID is existing to whom ball is kicked– YES
8.	Refresh player list manually – YES
9.	Synchronize user interface among all players - YES
Server Side
1.	Display new connected User – YES
2.	Display disconnected user – YES
3.	Give ball to another user if player has a ball before disconnection – YES
4.	Player passing the ball to another player (display the IDs of both players). – YES

Protocol
All commands and user identification are transmitted as an integer value. 
User ID – is any integer value
Commands – is also integer values in a specific range.
Initially, when game is started you need to choose one out of two option:
1.	Login Game – Enter the Session
2.	Exit Game – Exit from the game 
After that, enter your user ID, program will validate if this ID is unique (if there is already user with such id, it will ask you to choose another)

 
User interface is quite simplified. There are 3 main components:
It is:
1.	Your ID – display your ID 
2.	Player List – it display as Integer other player ID and as Boolean value permission to hold a ball (True – player has a ball; False – player doesn’t have a ball) 
3.	Option Menu
Option Menu has 3 option:
3.1.	Kick Ball – kick ball to another player
3.2.	Refresh User List Manually – give you opportunity to refresh menu manually (Initially was created for testing purpose bet I leave it in a final build)
3.3.	Exit Game – exit game, user will be deleted from a server and all other payers will be notified about it. If player hold a ball, ball will be automatically given to another person (Randomly) 

Kick Ball: 
When you kick ball, you must enter ID of existed user and have permission to do it (Have a ball) otherwise program send you notification that you can’t do it 

Client Threads
There are two main threads on a client side: 
1.	Option Choice – main Thread 
2.	Menu Update and synchronization 

Option Choice – main Thread 
This Thread is living in Client.java file – is responsible for sending commands to the server and getting output from it. It is created in a main loop. 

Menu Update and synchronization 
This thread is living in AutoReloader.java file – it is responsible for updating user list and ball permission list after event such as:
1.	New user connected
2.	User disconnected 
3.	Users kick a ball
It is refreshing user list in approximately 0.5 second time. I was using TimerTask class to make it 


Server Threads
Number of Threads is proportional to number of players due to fact that each player is processed in its own thread. Bet all player or all threads are sharing information about other players through 

