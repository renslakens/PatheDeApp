package com.groep3.pathedeapp.presentation;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckedTextView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.groep3.pathedeapp.R;
import com.squareup.picasso.Picasso;

public class MovieDetail extends AppCompatActivity {
    private String TAG = "MealDetail";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.movie_detail);


        // calling the action bar
        ActionBar actionBar = getSupportActionBar();

        // showing the back button in action bar
        assert actionBar != null;
        actionBar.setDisplayHomeAsUpEnabled(true);


        //makes all the views
        //meal info
        Log.i(TAG, "Creating views");
        TextView name = (TextView) findViewById(R.id.movieName);
//        TextView price = (TextView) findViewById(R.id.mealPrice);
//        TextView description = (TextView) findViewById(R.id.mealDescription);
//        TextView date = (TextView) findViewById(R.id.mealDate);
//        TextView allergens = (TextView) findViewById(R.id.mealAllergyInfo);
//        ImageView image = (ImageView) findViewById(R.id.foodImage);
//        //button
//        Button signUpButton = (Button) findViewById(R.id.signUpButton);
//        //cook info
//        TextView cook = (TextView) findViewById(R.id.cook);
//        TextView cookCity = (TextView) findViewById(R.id.cookCity);
//        TextView cookStreet = (TextView) findViewById(R.id.cookStreet);
//        ImageView cookEmail = (ImageView) findViewById(R.id.cookEmail);
//        ImageView cookPhone = (ImageView) findViewById(R.id.cookSms);
//        ImageView cookWhatsapp = (ImageView) findViewById(R.id.cookWhatsapp);

        //sign up button click handler
//        signUpButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "User clicked on sign up button");
//                Toast.makeText(getApplicationContext(),
//                        R.string.not_logged_in_error,
//                        Toast.LENGTH_SHORT)
//                        .show();
//            }
//        });
//
//        //set onclick listener for contact buttons
//        cookEmail.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "User clicked cook mail button");
//                try {
//                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
//                    emailIntent.setType("plain/text");
//                    emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{getIntent().getStringExtra("foodEmail")});
//                    startActivity(emailIntent);
//                    Log.i(TAG, "Opening mail application");
//                } catch (android.content.ActivityNotFoundException activityNotFoundException) {
//                    Log.e(TAG, "Could not find mail application");
//                    Toast.makeText(getApplicationContext(),
//                            R.string.supported_app_error_toast,
//                            Toast.LENGTH_SHORT)
//                            .show();
//                }
//            }
//        });
//        cookPhone.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "User clicked cook sms button");
//                try {
//                    Intent intent = new Intent(Intent.ACTION_SEND);
//                    intent.setData(Uri.parse("smsto:" + getIntent().getStringExtra("foodPhone")));
//                    startActivity(intent);
//                    Log.i(TAG, "Opening sms application");
//                } catch (android.content.ActivityNotFoundException activityNotFoundException) {
//                    Toast.makeText(getApplicationContext(),
//                            R.string.supported_app_error_toast,
//                            Toast.LENGTH_SHORT)
//                            .show();
//                    Log.e(TAG, "Could not find sms application");
//                }
//            }
//        });
//        cookWhatsapp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Log.i(TAG, "User clicked cook whatsapp button");
//                try {
//                    String url = "https://api.whatsapp.com/send?phone=" + getIntent().getStringExtra("foodPhone");
//                    Intent i = new Intent(Intent.ACTION_VIEW);
//                    i.setData(Uri.parse(url));
//                    startActivity(i);
//                    Log.i(TAG, "Opening whatsapp application");
//                } catch (android.content.ActivityNotFoundException activityNotFoundException) {
//                    Toast.makeText(getApplicationContext(),
//                            R.string.supported_app_error_toast,
//                            Toast.LENGTH_SHORT)
//                            .show();
//                    Log.e(TAG, "Could not find whatsapp application");
//                }
//            }
//        });


//        CheckedTextView foodItemVega = (CheckedTextView) findViewById(R.id.vegaCheckmark);
//        CheckedTextView foodItemVegan = (CheckedTextView) findViewById(R.id.veganCheckmark);

        //put view values on screen
        Log.i(TAG, "Giving views data");
        name.setText(getIntent().getStringExtra("movieName"));
//        price.setText(getIntent().getStringExtra("foodPrice"));
//        description.setText(getIntent().getStringExtra("foodDescription"));
//        date.setText(getIntent().getStringExtra("foodDate"));
//        allergens.setText(getIntent().getStringExtra("foodAllergies"));
//        cook.setText(getIntent().getStringExtra("foodCook"));
//        cookCity.setText(getIntent().getStringExtra("foodCity"));
//        cookStreet.setText(getIntent().getStringExtra("foodStreet"));


        //change button value based on vega/vegan
//        if (getIntent().getBooleanExtra("foodVega", false)) {
//            Log.d(TAG, "Food is vega");
//            foodItemVega.setCheckMarkDrawable(R.drawable.checked_circle);
//        } else {
//            Log.d(TAG, "Food is not vega");
//            foodItemVega.setCheckMarkDrawable(R.drawable.unchecked_circle);
//        }
//        if (getIntent().getBooleanExtra("foodVegan", false)) {
//            Log.d(TAG, "Food is vegan");
//            foodItemVegan.setCheckMarkDrawable(R.drawable.checked_circle);
//        } else {
//            Log.d(TAG, "Food is not vegan");
//            foodItemVegan.setCheckMarkDrawable(R.drawable.unchecked_circle);
//        }

        //load image with picasso
//        Log.d(TAG, "Attempting to load image" + getIntent().getStringExtra("image_resource"));
//        Picasso.with(this)
//                .load(getIntent().getStringExtra("image_resource"))
//                .into(image);

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                Log.d(TAG, "Going back to main menu");
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
