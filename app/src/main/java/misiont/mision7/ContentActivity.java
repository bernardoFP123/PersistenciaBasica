package misiont.mision7;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ContentActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, RegisterParkingOperations {
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    android.app.Fragment fragment;
    Toolbar toolbar;
    TextView textViewUserMail;
    int optionChosen = 0;
    public static final String USER_NAME_KEY = "usernamekey";
    public static final int  REQUEST_CODE = 0;
    String[] permisos = {Manifest.permission.READ_EXTERNAL_STORAGE,Manifest.permission.WRITE_EXTERNAL_STORAGE};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_content_activity);

        int leer = ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if(leer == PackageManager.PERMISSION_DENIED){
            ActivityCompat.requestPermissions(this,permisos,REQUEST_CODE);
        }




        toolbar = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        drawerLayout  = (DrawerLayout)findViewById(R.id.drawerLayout);
        navigationView = (NavigationView) findViewById(R.id.navigationView);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,
                R.string.drawerAbierto,R.string.drawerCerrado);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        fragment = ParqueosFragment.newInstance();
        getFragmentManager().beginTransaction()
                .add(R.id.container,fragment)
                .commit();
        toolbar.setSubtitle(R.string.asignaturas);



        if(getIntent().getExtras() != null){
            setDrawerUser(getIntent().getExtras().getString(USER_NAME_KEY));
        }

    }



    public void setDrawerUser(String drawerUser){
        LinearLayout layout = (LinearLayout) navigationView.getHeaderView(0);
        textViewUserMail = (TextView)layout.findViewById(R.id.textViewUserMail);
        textViewUserMail.setText(drawerUser + "@nextu.com");
    }

    public void setUserName(){
        if(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("userConfig",null) != null) {
            LinearLayout layout = (LinearLayout) navigationView.getHeaderView(0);
            TextView textViewUserName;
            textViewUserName = (TextView) layout.findViewById(R.id.textViewUserName);
            textViewUserName.setText(PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).getString("userConfig",null));
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_button_parqueos:

                fragment = ParqueosFragment.newInstance();
                toolbar.setSubtitle(R.string.mi_cuenta);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment)
                        .commit();
                break;
            case R.id.menu_button_cuenta:
                fragment = new AccountConfigFragment();
                toolbar.setSubtitle(R.string.parqueos);
                getFragmentManager().beginTransaction()
                        .replace(R.id.container,fragment)
                        .commit();
                break;
        }
        drawerLayout.closeDrawer(Gravity.START);
        return true;
    }


    @Override
    public void addRegisters(List<Registro> registros) {
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(FilesNames.localFileName,MODE_PRIVATE));
            for(Registro registro : registros)
                outputStream.writeObject(registro);
            outputStream.close();
        }
        catch (IOException e){

        }
    }

    @Override
    public void addRegistersToExternallFile(List<Registro> registros,String fileName) {
        File file = new File(getExternalFilesDir(null), FilesNames.externallFileName);
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(file));
            for(Registro registro : registros)
                outputStream.writeObject(registro);
            outputStream.close();
        }
        catch (IOException e){

        }
    }

    @Override
    public List<Registro> getRegisters() {
        List<Registro> registros = new ArrayList<>();
        ObjectInputStream inputStream = null;

         try{
             inputStream = new ObjectInputStream(openFileInput(FilesNames.localFileName));
             while(true){
                 registros.add((Registro) inputStream.readObject());
             }
         }
         catch (FileNotFoundException e){

         }
         catch(IOException e){

         }
         catch (ClassNotFoundException e){


         }

         if(inputStream != null){
             try {
                 inputStream.close();
             }
             catch (IOException e){

             }
         }

        return registros;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()){
            case R.id.options_button_cerrar_sesion:
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit().clear()
                        .commit();
                Intent intent = new Intent(this,MainActivity.class);
                startActivity(intent);
                break;
            case R.id.options_button_exportar:
                showAlertForExport();
                break;
        }
        return true;
    }

    public void showAlertForExport(){
        optionChosen = 0;
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Exportar registros")
                .setSingleChoiceItems(R.array.export_options, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        optionChosen = i;
                    }
                })
                .setPositiveButton(R.string.aceptar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        exportRegisters();
                        if(optionChosen == 0) {
                            deleteLocalRegisters();
                        }

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }

    public void exportRegisters(){
        addRegistersToExternallFile(getRegisters(), FilesNames.externallFileName);
    }
    public void deleteLocalRegisters(){
        try{
            ObjectOutputStream outputStream = new ObjectOutputStream(openFileOutput(FilesNames.localFileName,MODE_PRIVATE));
            outputStream.close();
        }
        catch (IOException e){

        }
        if(fragment instanceof ParqueosFragment){
            ((ParqueosFragment) fragment).updateRecyclerView();
        }
    }

    @Override
    public void onBackPressed() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserName();
    }

    public void showAlertForNewRegister(final List<Registro> registros, final RecyclerViewAdapterRegistros adapterRegistros){
        View view = LayoutInflater.from(this).inflate(R.layout.alert_for_new_register,null);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        final EditText editTextMatricula = view.findViewById(R.id.editTextMatricula);
        final EditText editTextNoCliente = view.findViewById(R.id.editTextCliente);

        builder.setTitle(R.string.registrar_parqueo)
                .setView(view)
                .setPositiveButton(R.string.registrar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        if(editTextMatricula.getText().toString().equals("") || editTextNoCliente.getText().toString().equals("")){
                            Toast.makeText(getApplicationContext(),R.string.llenar_datos,Toast.LENGTH_SHORT).show();
                            return;
                        }
                        else{

                            Registro registro = new Registro(editTextMatricula.getText().toString(),editTextNoCliente.getText().toString());

                            //Actualiza la lista del recyclerView
                            registros.add(registro);
                            //Agrega los registros a la base de datos
                            addRegisters(registros);
                            adapterRegistros.notifyItemInserted(registros.size()-1);
                        }

                    }
                })
                .setNegativeButton(R.string.cancelar, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .show();
    }


}
