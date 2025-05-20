USE anime_db;

CALL pro_studio_insert("a");
CALL pro_studio_insert("b");
CALL pro_studio_insert("c");

CALL pro_genre_insert("shonen");
CALL pro_genre_insert("romance");
CALL pro_genre_insert("caca");

CALL pro_anime_insert(1,"animeSRC",1,"spring",2025,"finished_airing",20);
CALL pro_anime_insert(2,"animeS",2,"fall",2022,"currently_airing",12);
CALL pro_anime_insert(3,"animeR",3,"summer",2021,"not_yet_aired",24);
CALL pro_anime_insert(1,"animeC",4,"winter",2020,"currently_airing",18);
CALL pro_anime_insert(2,"animeSC",5,"spring",2023,"finished_airing",12);

CALL pro_animes_genres_insert(1,1); CALL pro_animes_genres_insert(1,2); CALL pro_animes_genres_insert(1,3);

CALL pro_animes_genres_insert(2,1);

CALL pro_animes_genres_insert(3,2);

CALL pro_animes_genres_insert(4,3);

CALL pro_animes_genres_insert(5,1);
CALL pro_animes_genres_insert(5,3);