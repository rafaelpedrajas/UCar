package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Login extends AppCompatActivity
{
    // Email, password edittext
    TextInputEditText eTCorreo, eTPass;

    // login button
    Button btnLogin;

    // Alert Dialog Manager
    //AlertDialogManager alert = new AlertDialogManager();

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //INICIALIZAR TOOLBAR
        ImageButton back = (ImageButton) findViewById(R.id.back);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Inciar sesión");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAtras();
            }
        });

        // Session Manager
        session = new SessionManager(getApplicationContext());

        // Email, Password input text
        eTCorreo = (TextInputEditText) findViewById(R.id.eTCorreo);
        eTPass = (TextInputEditText) findViewById(R.id.eTPass);

        Toast.makeText(getApplicationContext(), "User Login Status: " + session.isLoggedIn(), Toast.LENGTH_LONG).show();


        // Login button
        btnLogin = (Button) findViewById(R.id.btnLogin);
        TextView registro = (TextView)findViewById(R.id.tvRegistro);

        registro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), Registro.class);
                startActivity(i);
                finish();
            }
        });


        // Login button click event
        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // Get username, password from EditText
                final String correo = eTCorreo.getText().toString();
                final String password = eTPass.getText().toString();

                // Check if username, password is filled
                if(correo.trim().length() > 0 && password.trim().length() > 0){
                    // For testing puspose username, password is checked with sample data
                    // username = test
                    // password = test
                   /* if(username.equals("test") && password.equals("test")){

                        // Creating user login session
                        // For testing i am stroing name, email as follow
                        // Use user real data
                        session.createLoginSession("Android Hive", "anroidhive@gmail.com");

                        // Staring MainActivity
                        Intent i = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(i);
                        finish();

                    }else{
                        // username / password doesn't match
                        Toast.makeText(getApplicationContext(), "Login failed. Username/Password is incorrect", Toast.LENGTH_LONG).show();
                        //alert.showAlertDialog(LoginActivity.this, "Login failed..", "Username/Password is incorrect", false);
                    }*/

                    guardarUsuario(new VolleyCallback()
                    {
                        @Override
                        public void onSuccess(String result)
                        {
                            Log.d("Tolocooo",result);

                            JSONArray jsonArray= new JSONArray();

                            try
                            {
                                jsonArray = new JSONArray(result);

                                session.createLoginSession(jsonArray.getJSONObject(0).getString("correo"),
                                                            jsonArray.getJSONObject(0).getString("pass"),
                                                            jsonArray.getJSONObject(0).getString("nombre"),
                                                            jsonArray.getJSONObject(0).getString("foto"),
                                                            jsonArray.getJSONObject(0).getString("telefono"));


                                // Staring MainActivity
                                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(i);
                                finish();

                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    },correo,password);


                }else{
                    // user didn't entered username or password
                    // Show alert asking him to enter the details
                    Toast.makeText(getApplicationContext(), "Login failed. Please enter username and password ", Toast.LENGTH_LONG).show();
                   // alert.showAlertDialog(LoginActivity.this, "Login failed..", "Please enter username and password", false);
                }

            }
        });
    }

    public void guardarUsuario(final VolleyCallback callback, final String correo, final String password)
    {
        JSONArray jsonArray= new JSONArray();

         /*COMPROBAR LOGIN*/
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/comprobarLogin.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("Console",response);
                callback.onSuccess(response);
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Console","Error en el web service");
                    }
                }
        ){
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("correo", correo);
                params.put("pass", password);

                return params;
            }
        };
        queue.add(stringRequest);
                    /*FIN COMPROBAR LOGIN*/

    }

    private void volverAtras(){
        Intent volver = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(volver);
    }
    @Override
    public void onBackPressed (){
        volverAtras();
    }
}
