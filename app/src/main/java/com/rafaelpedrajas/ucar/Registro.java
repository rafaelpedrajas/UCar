package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;

import java.util.HashMap;
import java.util.Map;

public class Registro extends AppCompatActivity
{

    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        Button registro = (Button)findViewById(R.id.botonRegistro);
        registro.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                //---RECOGER DATOS REGISTRO---//
                TextInputEditText nombre = (TextInputEditText)findViewById(R.id.eTNombre);
                TextInputEditText apellido = (TextInputEditText)findViewById(R.id.eTApellido);
                TextInputEditText telefono = (TextInputEditText)findViewById(R.id.eTTelefono);
                TextInputEditText correo = (TextInputEditText)findViewById(R.id.eTCorreo);
                TextInputEditText pass = (TextInputEditText)findViewById(R.id.eTPass);

                registrarUsuario(new VolleyCallback()
                {
                    @Override
                    public void onSuccess(String result)
                    {
                        Log.d("Tolocooo",result);

                        JSONArray jsonArray= new JSONArray();

                        try
                        {
                            /*jsonArray = new JSONArray(result);

                            session.createLoginSession(jsonArray.getJSONObject(0).getString("correo"),
                                    jsonArray.getJSONObject(0).getString("pass"),
                                    jsonArray.getJSONObject(0).getString("nombre"),
                                    jsonArray.getJSONObject(0).getString("foto"),
                                    jsonArray.getJSONObject(0).getString("telefono"));*/


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
                },nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString(),pass.getText().toString());
            }
        });

    }

    public void registrarUsuario(final VolleyCallback callback, final String nombre, final String telefono, final String correo, final String password)
    {
        JSONArray jsonArray= new JSONArray();

         /*COMPROBAR LOGIN*/
        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/registro.php";

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
                params.put("nombre", nombre);
                params.put("telefono", telefono);
                params.put("correo", correo);
                params.put("pass", password);

                return params;
            }
        };
        queue.add(stringRequest);
                    /*FIN COMPROBAR LOGIN*/

    }
}
