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
    name 			VARCHAR(150),
    jikan_id		INT UNSIGNED,
    score			FLOAT,
    season			ENUM("spring","summer","autumn","winter"),
    year			YEAR,
    airing			BOOLEAN DEFAULT false,
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