package com.rafaelpedrajas.ucar.GUI;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.rafaelpedrajas.ucar.Interfaces.VolleyCallback;
import com.rafaelpedrajas.ucar.R;
import com.rafaelpedrajas.ucar.Sesion.SessionManager;

import org.json.JSONArray;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class Login extends AppCompatActivity
{
    // Email, password edittext
    TextInputEditText eTCorreo, eTPass;

    TextInputLayout tILCorreo;
    TextInputLayout tILPass;

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
        tILCorreo = (TextInputLayout)findViewById(R.id.tILCorreo);
        tILPass = (TextInputLayout)findViewById(R.id.tILPass);


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

                boolean datosCorrectos = true;

                // Check if username, password is filled
                if(correo.trim().equals("")){
                    tILCorreo.setError("El correo es obligatorio");
                    datosCorrectos=false;
                }

                if(password.trim().equals("")){
                    datosCorrectos=false;
                    tILPass.setError("La contaseña es obligatoria");
                }


                if(datosCorrectos)
                {
                    comprobarLogin(new VolleyCallback()
                    {
                        @Override
                        public void onSuccess(String result)
                        {
                            Log.d("Tolocooo", result);

                            JSONArray jsonArray = new JSONArray();

                            try
                            {
                                jsonArray = new JSONArray(result);

                                session.createLoginSession(
                                        jsonArray.getJSONObject(0).getString("correo"),
                                        jsonArray.getJSONObject(0).getString("nombre"),
                                        jsonArray.getJSONObject(0).getString("apellido"),
                                        jsonArray.getJSONObject(0).getString("foto"),
                                        jsonArray.getJSONObject(0).getString("telefono"),
                                        jsonArray.getJSONObject(0).getString("nombreUniversidad"),
                                        jsonArray.getJSONObject(0).getString("nombreProvincia"),
                                        jsonArray.getJSONObject(0).getString("marcaCoche"),
                                        jsonArray.getJSONObject(0).getString("modeloCoche"),
                                        jsonArray.getJSONObject(0).getString("idUniversidad"),
                                        jsonArray.getJSONObject(0).getString("idProvincia"),
                                        jsonArray.getJSONObject(0).getString("idCoche"),
                                        jsonArray.getJSONObject(0).getString("plazasCoche"),
                                        jsonArray.getJSONObject(0).getString("valoracionPuntualidad"),
                                        jsonArray.getJSONObject(0).getString("valoracionAmabilidad"),
                                        jsonArray.getJSONObject(0).getString("valoracionConduccion")
                                        /*
                                        //Para convertir de double a float
                                        BigDecimal.valueOf(jsonArray.getJSONObject(0).getDouble("valoracionPuntualidad")).floatValue(),
                                        BigDecimal.valueOf(jsonArray.getJSONObject(0).getDouble("valoracionAmabilidad")).floatValue(),
                                        BigDecimal.valueOf(jsonArray.getJSONObject(0).getDouble("valoracionConduccion")).floatValue()
                                        */
                                );


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
                    }, correo, password);
                }
            }
        });
    }

    public void comprobarLogin(final VolleyCallback callback, final String correo, final String password)
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
