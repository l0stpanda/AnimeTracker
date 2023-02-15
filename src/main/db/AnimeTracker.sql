CREATE DATABASE IF NOT EXISTS AnimeTracker;

USE AnimeTracker;

-- ==================================================================
-- Table Anime
-- ==================================================================
DROP TABLE IF EXISTS anime;
CREATE TABLE IF NOT EXISTS Anime (
    ID int auto_increment primary key,
    animeName varchar(128),
    status varchar(64),
    seasons int,
    episodes int,
    rating int);

INSERT INTO Anime(animeName, status, seasons, episodes, rating) VALUES
                                                                    ('ccc', 'Watching', 8, 8, 8),
                                                                    ('ddd', 'Watching', 9, 9, 9),
                                                                    ('eee', 'Watching', 10, 10, 10);
-- UPDATE Anime SET status = 'Watching' WHERE ID = 2;
-- DELETE FROM Anime WHERE ID = 1;
SELECT * FROM Anime;

-- ==================================================================
-- Table Users
-- ==================================================================
DROP TABLE IF EXISTS Users;
CREATE TABLE IF NOT EXISTS Users (
    userName varchar(128) PRIMARY KEY,
    password varchar(128) NOT NULL,
    isActive boolean DEFAULT 1);
INSERT INTO Users (userName, password) VALUES ('scott', 'tiger');
SELECT * FROM Users;