package car.com.cartique;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import car.com.cartique.app.Config;
import car.com.cartique.model.User;

public class CompleteSignUpActivity extends AppCompatActivity {

    private EditText txtName;
    private EditText txtSurname;
    private EditText txtAddress;
    private EditText txtUserPhone;
    private EditText txtCity;
    AppCompatButton btnNext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.complete_registration_activity);
        btnNext = (AppCompatButton) findViewById(R.id.btnNext);
        txtName = (EditText) findViewById(R.id.userName);
        txtSurname = (EditText) findViewById(R.id.userSurname);
        txtUserPhone = (EditText) findViewById(R.id.userPhone);
        txtAddress = (EditText) findViewById(R.id.userAddress);
        txtCity = (EditText) findViewById(R.id.userCity);
        btnNext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (txtName.getText().toString().isEmpty()) {
                        txtName.setError("Enter Your Name");
                    } else if (txtSurname.getText().toString().isEmpty()) {
                        txtSurname.setError("Enter Your Surname");
                    } else if (txtUserPhone.getText().toString().isEmpty()) {
                        txtUserPhone.setError("Enter Your Contact Number");
                    } else if (txtCity.getText().toString().isEmpty()) {
                        txtCity.setError("Enter Your City");
                    }else if (txtAddress.getText().toString().isEmpty()) {
                        txtAddress.setError("Enter Your Address");
                    }
                    else {
                        User user = new User(txtName.getText().toString(), txtSurname.getText().toString(), txtAddress.getText().toString(), txtUserPhone.getText().toString(),txtCity.getText().toString());
                        storeLocationStringInPref(user.getCity());
                        Intent intent = new Intent(getApplicationContext(), CompleteSignUpCarActivity.class);
                        intent.putExtra(Config.SIGNUP_USER, user);
                        startActivity(intent);
                    }
                }
            });
        }

    private void storeLocationStringInPref(String location) {
        SharedPreferences pref = getApplicationContext().getSharedPreferences(Config.SHARED_PREF, 0);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(Config.LOCATION_STRING,location);
        editor.commit();
    }

    }
