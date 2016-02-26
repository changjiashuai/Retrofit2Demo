package io.github.changjiashuai.retrofit2demo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    private RestDataSource mRestDataSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRestDataSource = new RestDataSource();
        loadData();
    }

    private void loadData() {
        mRestDataSource.getCharacters(0)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(marvelCharacters -> {
                    Log.i("TAG", "marvelCharacters=" + marvelCharacters);
                });
    }
}
