package io.github.changjiashuai.retrofit2demo;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Email: changjiashuai@gmail.com
 *
 * Created by CJS on 16/2/26 22:58.
 */
public class MarvelSigningInterceptor implements Interceptor {
    private final String mApiKey;
    private final String mApiSecret;

    public MarvelSigningInterceptor(String mApiKey, String mApiSecret) {
        this.mApiKey = mApiKey;
        this.mApiSecret = mApiSecret;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        String marvelHash = MarvelApiUtils.generateMarvelHash(mApiKey, mApiSecret);
        Request oldRequest = chain.request();

        // 添加新的参数
        HttpUrl.Builder authorizedUrlBuilder = oldRequest.url()
                .newBuilder()
                .scheme(oldRequest.url().scheme())
                .host(oldRequest.url().host())
                .addQueryParameter(MarvelService.PARAM_API_KEY, mApiKey)
                .addQueryParameter(MarvelService.PARAM_TIMESTAMP, MarvelApiUtils.getUnixTimeStamp())
                .addQueryParameter(MarvelService.PARAM_HASH, marvelHash);

        // 新的请求
        Request newRequest = oldRequest.newBuilder()
                .method(oldRequest.method(), oldRequest.body())
                .url(authorizedUrlBuilder.build())
                .build();

        return chain.proceed(newRequest);
    }
}
