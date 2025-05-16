USE anime_db;

DROP PROCEDURE IF EXISTS pro_anime_insert;
DROP PROCEDURE IF EXISTS pro_animes_genres_insert;
DROP PROCEDURE IF EXISTS pro_genre_insert;
DROP PROCEDURE IF EXISTS pro_studio_insert;

DELIMITER //
CREATE PROCEDURE pro_anime_insert(vStudio INT UNSIGNED, vName VARCHAR(150),
									vJikan INT UNSIGNED, vScore FLOAT, 
									vSeason ENUM('spring','summer','autumn','winter'), 
                                    vYear YEAR, vAiring BOOLEAN, vNum_episodes INT UNSIGNED)
BEGIN
	INSERT INTO animes(studio_id,name,jikan_id,score,season,year,airing,num_episodes) VALUES(vStudio,vName,vJikan,vScore,vSeason,vYear,vAiring,vNum_episodes);
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE pro_animes_genres_insert(vAnime INT UNSIGNED, vGenre INT UNSIGNED)
BEGIN
	INSERT INTO animes_genres(anime_id,genre_id) VALUES(vAnime,vGenre);
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE pro_genre_insert(vName VARCHAR(50))
BEGIN
	INSERT INTO genres(name) VALUES(vName);
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE pro_studio_insert(vName VARCHAR(50))
BEGIN
	INSERT INTO studios(name) VALUES(vName);
END
//
DELIMITER ;