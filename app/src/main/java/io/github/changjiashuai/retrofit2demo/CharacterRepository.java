package io.github.changjiashuai.retrofit2demo;

import java.util.List;

import rx.Observable;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/2/26 22:54.
 */
public interface CharacterRepository {
    Observable<List<MarvelCharacter>> getCharacters (int offset);
}
