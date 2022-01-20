DROP DATABASE IF EXISTS mastermindDB;
CREATE DATABASE mastermindDB;

USE mastermindDB;

CREATE TABLE game(
gameId INT PRIMARY KEY AUTO_INCREMENT,
answer INT NOT NULL,
finished BOOLEAN);

CREATE TABLE round(
roundId INT PRIMARY KEY AUTO_INCREMENT,
timeAttempt DATETIME NOT NULL,
guess INT NOT NULL,
result varchar(7) NOT NULL,
gameId INT NOT NULL,
FOREIGN KEY fk_Game_Round (gameId)
        REFERENCES game(gameId));


INSERT INTO game(gameId, answer, finished)
VALUES(1, 2547 , false),
(2, 8541 , false),
(3, 5874 , true);

INSERT INTO round(roundId, timeAttempt, guess, result, gameId )
VALUES(1, '2021-01-01 14:00:00', 2635 ,"e:1:p:1" ,1),
(2, '2021-10-10 15:00:00', 2537 ,"e:3:p:0" ,1),
(3, '2021-12-12 14:00:00', 5862 ,"e:2:p:0" ,3),
(4, '2021-12-12 14:01:00', 5871 ,"e:3:p:0" ,3),
(5, '2021-12-12 14:02:00', 5874 ,"e:4:p:0" ,3);



