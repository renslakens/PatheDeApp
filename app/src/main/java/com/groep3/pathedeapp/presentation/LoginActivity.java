package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.groep3.pathedeapp.R;
import com.groep3.pathedeapp.dataacces.ApiClient;
import com.groep3.pathedeapp.dataacces.ApiInterface;
import com.groep3.pathedeapp.domain.UserAuthenticate;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {
    private final String apiKey = "11db3143a380ada0de96fe9028cbc905";
    public static String SESSION_ID = "";
    public static String GUEST_SESSION_ID = "";
    private final String TAG = MainActivity.class.getSimpleName();

    private UserAuthenticate requestToken = new UserAuthenticate();
    private UserAuthenticate requestTokenOnLogin = new UserAuthenticate();

    private Button mLogin;
    private TextInputEditText mUsername;
    private TextInputEditText mPassword;

    ApiInterface apiInterface = ApiClient.getClient().create(ApiInterface.class);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        getRequestToken();
        login();
    }

    private void goToURL(String url) {
        Uri uriUrl = Uri.parse(url);
        Intent launchBrowser = new Intent(Intent.ACTION_VIEW, uriUrl);
        startActivity(launchBrowser);
    }

    private void getRequestToken() {
        Call<UserAuthenticate> call = apiInterface.createNewSession(apiKey);

        call.enqueue(new Callback<UserAuthenticate>() {

            @Override
            public void onResponse(Call<UserAuthenticate> call, Response<UserAuthenticate> response) {
                requestToken = response.body();
                Log.d("Request Token", requestToken.getRequestToken());
                login();
            }

            @Override
            public void onFailure(Call<UserAuthenticate> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    //Login with username + password
    public void login() {
        mLogin = findViewById(R.id.buttonLogin);

        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mUsername = (TextInputEditText) findViewById(R.id.username);
                String username = mUsername.getText().toString();
                mPassword = (TextInputEditText) findViewById(R.id.password);
                String password = mPassword.getText().toString();

                Call<UserAuthenticate> call = apiInterface.validateRequestToken(apiKey, username, password, requestToken.getRequestToken());

                call.enqueue(new Callback<UserAuthenticate>() {
                    @Override
                    public void onResponse(Call<UserAuthenticate> call, Response<UserAuthenticate> response) {
                        if (response.isSuccessful()) {
                            requestTokenOnLogin = response.body();
                            Log.d("Login successful", response.body().toString());
                            getSessionID();
                        } else {
                            Toast.makeText(getApplicationContext(), R.string.incorrect_login, Toast.LENGTH_SHORT).show();
                            Log.d(TAG, "failure " + response.headers());
                        }
                    }

                    @Override
                    public void onFailure(Call<UserAuthenticate> call, Throwable t) {
                        Log.e(TAG, t.toString());
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
    }

    //Get session ID
    private void getSessionID() {
        Call<UserAuthenticate> call = apiInterface.createSessionID(apiKey, requestToken.getRequestToken());

        call.enqueue(new Callback<UserAuthenticate>() {
            @Override
            public void onResponse(Call<UserAuthenticate> call, Response<UserAuthenticate> response) {
                if (response.isSuccessful()) {
                    UserAuthenticate session = response.body();
                    SESSION_ID = session.getSessionID();
                    Log.d("Created Session", SESSION_ID);
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.putExtra("request_token", session.getRequestToken());
                    intent.putExtra("session_id", SESSION_ID);
                    intent.putExtra("logged_in", true);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), R.string.logged_in, Toast.LENGTH_SHORT).show();
                } else {
                    Log.d(TAG, "failure " + response.headers());
                }

            }

            @Override
            public void onFailure(Call<UserAuthenticate> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });
    }

    //Login as guest
    public void guestLogin(View view) {
        Call<UserAuthenticate> call = apiInterface.newGuestSession(apiKey);

        call.enqueue(new Callback<UserAuthenticate>() {
            @Override
            public void onResponse(Call<UserAuthenticate> call, Response<UserAuthenticate> response) {
                if (response.isSuccessful()) {
                    UserAuthenticate guestSession = response.body();
                    SESSION_ID = guestSession.getGuestSessionID();
                    GUEST_SESSION_ID = SESSION_ID;
                    Log.d("Created Session", SESSION_ID);
                    Toast.makeText(getApplicationContext(), R.string.logged_in_guest, Toast.LENGTH_SHORT).show();
                    Log.d("Login", "Signed in as guest");
                    Intent intent = new Intent(view.getContext(), MainActivity.class);
                    intent.putExtra("request_token", guestSession.getRequestToken());
                    intent.putExtra("session_id", GUEST_SESSION_ID);
                    intent.putExtra("logged_in", false);
                    view.getContext().startActivity(intent);
                } else {
                    Log.d(TAG, "failure " + response.headers());
                }
            }

            @Override
            public void onFailure(Call<UserAuthenticate> call, Throwable t) {
                Log.e(TAG, t.toString());
            }
        });

    }

    //Register an account at themoviedb.org
    public void register(View view) {
        goToURL("https://www.themoviedb.org/signup");
    }
}
