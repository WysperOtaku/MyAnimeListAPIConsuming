USE anime_db;

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
	SELECT a.anime_id, s.name AS studio, a.name, a.jikan_id, a.score, a.season, a.year, a.airing, a.num_episodes, fun_Genres(IdAnime) AS gen
		FROM animes a
        INNER JOIN studios s ON s.studio_id = a.studio_id
	WHERE a.anime_id = IdAnime;
END
//
DELIMITER ;