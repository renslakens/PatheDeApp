package com.groep3.pathedeapp.presentation;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.groep3.pathedeapp.MainActivity;
import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.PostUser;
import com.groep3.pathedeapp.domain.UserRequestToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String apiKey = "11db3143a380ada0de96fe9028cbc905";
    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        getRequestToken(apiKey);
    }

    private void goToURL(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void getRequestToken(String apiKey) {
        Call<UserRequestToken> call = apiInterface.createNewSession(apiKey);

        call.enqueue(new Callback<UserRequestToken>() {

            @Override
            public void onResponse(Call<UserRequestToken> call, Response<UserRequestToken> response) {
                UserRequestToken token = response.body();
                Log.d("Request Token: ", token.toString());
            }

            @Override
            public void onFailure(Call<UserRequestToken> call, Throwable t) {
                Log.d("Request Token", "Error occured");
            }
        });
    }

    //Login with username + password
    public void login(View view) {
        EditText usernameInput = (EditText) findViewById(R.id.username);
        String username = usernameInput.getText().toString();
        EditText passwordInput = (EditText) findViewById(R.id.password);
        String password = passwordInput.getText().toString();

        //PostUser postUser = new PostUser(username, password, //request token moet hier staan);

        //Call<PostUser> call = apiInterface.validateRequestToken(apiKey, postUser);
    }

    //Login as guest
    public void guestLogin(View view) {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    //Register an account at themoviedb.org
    public void register(View view) {
        goToURL("https://www.themoviedb.org/signup");
    }
}
