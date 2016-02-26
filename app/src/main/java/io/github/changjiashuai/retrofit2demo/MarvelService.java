package io.github.changjiashuai.retrofit2demo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/2/26 22:49.
 */
public interface MarvelService {
    String ENDPOINT = "http://geteway.marvel.com/";
    String PARAM_API_KEY = "apikey";
    String PARAM_HASH = "hash";
    String PARAM_TIMESTAMP = "ts";

    @GET("/v1/public/characters")
    Observable<List<MarvelCharacter>> getCharacters(@Query("offset")int offset);
}
