package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Perfil extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    List<String> arrayNombreCiudades = new ArrayList<>();

    Spinner spCiudades;
    Spinner spUniversidad;

    ArrayAdapter<String> aACiudades, aAUniversidad;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ver_perfil);

        //Inicializar toolbar
        ImageButton back = (ImageButton) findViewById(R.id.back);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Perfil");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
            }
        });


        /* SELECT CIUDADES */
        //Cargar las ciudades de la BD
        spCiudades=(Spinner)findViewById(R.id.spCiudades);

        spCiudades.setOnItemSelectedListener(this);

        ciudadesBD(new VolleyCallback()
        {
            @Override
            public void onSuccess(String result)
            {
                Log.d("Tolocooo",result);

                JSONArray jsonArray= new JSONArray();

                try
                {
                    jsonArray = new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        String nombreCiudad=jsonArray.getJSONObject(i).getString("nombre");
                        arrayNombreCiudades.add(nombreCiudad);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        //Mostrar las ciudades
        aACiudades=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_item, arrayNombreCiudades);

        spCiudades.setAdapter(aACiudades);
        /* FIN SELECT CIUDADES */



        Button botonModificar = (Button)findViewById(R.id.botonModificar);
        botonModificar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {

    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    public void ciudadesBD(final VolleyCallback callback)
    {
        JSONArray jsonArray= new JSONArray();
        List<String> arrayNombreCiudades = new ArrayList<>();
        String URL = "https://www.rafaelpedrajas.com/android/listadoCiudades.php";

        final String response="";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        Log.d("Console",response);
                        callback.onSuccess(response);
                        //Toast.makeText(CrearAnuncio.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(Perfil.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(Perfil.this);
        queue.add(stringRequest);

        /*
        try
        {
            jsonArray = new JSONArray(response);

            for(int i=0;i<jsonArray.length();i++)
            {
                String nombreCiudad=jsonArray.getJSONObject(i).getString("nombre");
                arrayNombreCiudades.add(nombreCiudad);
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        */

    }
}

