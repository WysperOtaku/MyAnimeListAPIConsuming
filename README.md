# MyAnimeListAPIConsuming

Para poder buscar la informacion y asi obtener los datos directamente de la api podemos consumir directamente desde esta url.

https://api.jikan.moe/v4/anime/?

Podemo cambiar el "?" por la id que necesitemos para buscar, luego tendremos la informacion en un json del qual obtendremos la informacion parecida al siguiente:

```JSON
{
  "data": {
    "title": "Enen no Shouboutai: San no Shou",
    "episodes": 12,
    "airing": true,
    "score": 7.99,
    "season": "spring",
    "year": 2025,
    "studios": [
      {
        "name": "David Production"
      }
    ],
    "genres": [
      {
        "name": "Action"
      },
      {
        "name": "Fantasy"
      },
      {
        "name": "Sci-Fi"
      }
    ],
  }
}
```