DROP DATABASE IF EXISTS anime_db;

CREATE DATABASE anime_db;

USE anime_db;

CREATE TABLE studios(
	studio_id		INT UNSIGNED AUTO_INCREMENT,
    name			VARCHAR(50),
    CONSTRAINT pk_studios PRIMARY KEY studios(studio_id)
);

CREATE TABLE animes(
	anime_id		INT UNSIGNED AUTO_INCREMENT,
    studio_id 		INT UNSIGNED,
    name 			VARCHAR(75),
    mal_id			INT UNSIGNED,
    season			ENUM("spring","summer","fall","winter"),
    year			YEAR,
    status          ENUM("finished_airing","currently_airing","not_yet_aired"),
    num_episodes	INT UNSIGNED,
    CONSTRAINT pk_animes PRIMARY KEY animes(anime_id),
    CONSTRAINT fk_animes_studios FOREIGN KEY animes(studio_id)
		REFERENCES studios(studio_id)
);

CREATE TABLE genres(
	genre_id		INT UNSIGNED AUTO_INCREMENT,
    name			VARCHAR(50),
    CONSTRAINT pk_genres PRIMARY KEY genres(genre_id)
);

CREATE TABLE animes_genres(
	anime_genre_id	INT UNSIGNED AUTO_INCREMENT,
    anime_id		INT UNSIGNED,
	genre_id		INT UNSIGNED,
    CONSTRAINT pk_animes_genres PRIMARY KEY animes_genres(anime_genre_id),
    CONSTRAINT fk_animes_genres_animes FOREIGN KEY animes_genres(anime_id)
		REFERENCES animes(anime_id),
	CONSTRAINT fk_animes_genres_genres FOREIGN KEY animes_genres(genre_id)
		REFERENCES genres(genre_id)
);

DROP PROCEDURE IF EXISTS pro_anime_select;
DROP FUNCTION IF EXISTS fun_Genres;

DELIMITER //
CREATE FUNCTION fun_Genres(IdAnime INT UNSIGNED) RETURNS VARCHAR(500)
NOT DETERMINISTIC READS SQL DATA
BEGIN
	DECLARE fin_curs BOOLEAN DEFAULT false;
    DECLARE temp_genre VARCHAR(25);
    DECLARE genres VARCHAR(500) DEFAULT "";
	DECLARE cGenreCurs CURSOR FOR SELECT g.name FROM animes_genres ag INNER JOIN genres g ON g.genre_id = ag.genre_id WHERE ag.anime_id = IdAnime;

	DECLARE CONTINUE HANDLER FOR NOT FOUND
    BEGIN
		SET fin_curs = true;
    END;

	OPEN cGenreCurs;

    FETCH cGenreCurs INTO temp_genre;

	SET genres = temp_genre;

    WHILE (fin_curs = false) DO
		FETCH cGenreCurs INTO temp_genre;
        IF (fin_curs = false)
			THEN SET genres = CONCAT(genres, " - ", temp_genre);
		END IF;
    END WHILE;

    CLOSE cGenreCurs;

    RETURN genres;
END
//
DELIMITER ;



DELIMITER //
CREATE PROCEDURE pro_anime_select(IdAnime INT UNSIGNED)
BEGIN
	SELECT s.name AS studio, a.name, a.mal_id, a.season, a.year, a.status, a.num_episodes, fun_Genres(IdAnime) AS gen
		FROM animes a
        INNER JOIN studios s ON s.studio_id = a.studio_id
	WHERE a.anime_id = IdAnime;
END
//
DELIMITER ;

DROP PROCEDURE IF EXISTS pro_anime_insert;
DROP PROCEDURE IF EXISTS pro_animes_genres_insert;
DROP PROCEDURE IF EXISTS pro_genre_insert;
DROP PROCEDURE IF EXISTS pro_studio_insert;

DELIMITER //
CREATE PROCEDURE pro_anime_insert(vStudio INT UNSIGNED, vName VARCHAR(75),
									vMal INT UNSIGNED, vSeason ENUM('spring','summer','fall','winter'),
                                    vYear YEAR, vStatus ENUM("finished_airing","currently_airing","not_yet_aired"),
                                    vNum_episodes INT UNSIGNED)
BEGIN
	INSERT INTO animes(studio_id,name,mal_id,season,year,status,num_episodes) VALUES(vStudio,vName,vMal,vSeason,vYear,vStatus,vNum_episodes);
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