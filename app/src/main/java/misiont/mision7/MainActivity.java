package misiont.mision7;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button buttonIniciarSesion;
    CheckBox checkBoxRememberMe;
    EditText editTextUser;
    EditText editTextPass;
    SharedPreferences defaultSharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        buttonIniciarSesion = (Button)findViewById(R.id.buttonIniciarSesion);
        checkBoxRememberMe = (CheckBox)findViewById(R.id.checkBoxRecordar);
        editTextPass = (EditText)findViewById(R.id.editTextUserPassword);
        editTextUser = (EditText)findViewById(R.id.editTextUserName);

        defaultSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());

        if(defaultSharedPreferences.getString(SharedPreferencesKeys.PassKey,null) != null ){
            editTextPass.setText(defaultSharedPreferences.getString(SharedPreferencesKeys.PassKey,null));
            editTextUser.setText(defaultSharedPreferences.getString(SharedPreferencesKeys.UserKey,null));
            checkBoxRememberMe.setChecked(true);

        }

        buttonIniciarSesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(editTextUser.getText().toString().equals("") || editTextPass.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(),"Introduce los datos de inicio de sesión",Toast.LENGTH_SHORT).show();
                    return;
                }


                Intent intent = new Intent(MainActivity.this,ContentActivity.class);
                //Salvar preferencias
                if(checkBoxRememberMe.isChecked()){
                    defaultSharedPreferences.edit().putString(SharedPreferencesKeys.PassKey,editTextPass.getText().toString())
                        .putString(SharedPreferencesKeys.UserKey,editTextUser.getText().toString())
                        .commit();
                }
                else{
                    defaultSharedPreferences.edit().putString(SharedPreferencesKeys.PassKey,null)
                                                .putString(SharedPreferencesKeys.UserKey,null)
                                                .commit();
                }
                intent.putExtra(ContentActivity.USER_NAME_KEY,editTextUser.getText().toString());
                startActivity(intent);

            }
        });


    }
}
