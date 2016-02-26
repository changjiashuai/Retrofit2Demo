package io.github.changjiashuai.retrofit2demo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/2/26 22:56.
 */
public class RestDataSource implements CharacterRepository {

    private MarvelService mMarvelService;

    public RestDataSource(){
        // Log信息
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

        // 公私密钥
        MarvelSigningInterceptor signingInterceptor = new MarvelSigningInterceptor(
                BuildConfig.MARVEL_PUBLIC_KEY, BuildConfig.MARVEL_PRIVATE_KEY);

        // OkHttp3.0的使用方式
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(signingInterceptor)
                .addInterceptor(loggingInterceptor)
                .build();

        // 选择人物信息
        Gson customGsonInstance = new GsonBuilder()
                .registerTypeAdapter(new TypeToken<List<MarvelCharacter>>() {
                        }.getType(),
                        new MarvelResultsDeserializer<MarvelCharacter>())
                .create();

        // 适配器
        Retrofit marvelApiAdapter = new Retrofit.Builder()
                .baseUrl(MarvelService.ENDPOINT)
                .addConverterFactory(GsonConverterFactory.create(customGsonInstance))
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .client(client)
                .build();

        // 服务
        mMarvelService = marvelApiAdapter.create(MarvelService.class);
    }

    @Override
    public Observable<List<MarvelCharacter>> getCharacters(int offset) {
        return mMarvelService.getCharacters(offset);
    }
}
