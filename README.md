# Mastermind

It is a Spring Boot REST application using JDBC Template to access the database.

A Game should have an answer and a status (in progress or finished). While the game is in progress, users should not be able to see the answer. The answer will be a 4-digit number with no duplicate digits.

Each Round will have a guess, the time of the guess, and the result of the guess in the format "e:0:p:0" where "e" stands for exact matches and "p" stands for partial matches.

REST endpoints:

+ "begin" - POST – Starts a game, generates an answer, and sets the correct status. Should return a 201 CREATED message as well as the created gameId.
+ "guess" – POST – Makes a guess by passing the guess and gameId in as JSON. The program must calculate the results of the guess and mark the game finished if the guess is correct. It returns the Round object with the results filled in.
+ "game" – GET – Returns a list of all games. Be sure in-progress games do not display their answer.
+ "game/{gameId}" - GET – Returns a specific game based on ID. Be sure in-progress games do not display their answer.
+ "rounds/{gameId} – GET – Returns a list of rounds for the specified game sorted by time.
