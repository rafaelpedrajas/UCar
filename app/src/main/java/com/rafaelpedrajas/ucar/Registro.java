package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
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

import fr.ganfra.materialspinner.MaterialSpinner;

public class Registro extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    List<String> arrayNombreProvincias = new ArrayList<>();
    List<String> arrayNombreUniversidades = new ArrayList<>();
    MaterialSpinner spProvincias, spUniversidades;
    ArrayAdapter<String> aAProvincias, aAUniversidades;
    // Session Manager Class
    SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Mostrar primera vista
        final LinearLayout primerLayout = (LinearLayout) findViewById(R.id.primerLayout);
        final LinearLayout segundoLayout = (LinearLayout) findViewById(R.id.segundoLayout);

        primerLayout.setVisibility(View.VISIBLE);



        //Funcionalidad botones vistas
        Button registro = (Button)findViewById(R.id.botonRegistro);
        Button continuar = (Button)findViewById(R.id.botonContinuar);

        continuar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                primerLayout.setVisibility(View.INVISIBLE);
                segundoLayout.setVisibility(View.VISIBLE);
            }
        });

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
                            //finish();

                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },nombre.getText().toString(), telefono.getText().toString(), correo.getText().toString(),pass.getText().toString());
            }
        });



        /*INICIALIZACION SPINER UNIVERSIDADES*/
        spUniversidades=(MaterialSpinner) findViewById(R.id.spUniversidades);
        spUniversidades.setOnItemSelectedListener(this);
        /*FIN INICIALIZACION SPINER UNIVERSIDADES*/



        /* SELECT PROVINCIAS */
        //Cargar las ciudades de la BD
        spProvincias=(MaterialSpinner) findViewById(R.id.spProvincias);
        spProvincias.setOnItemSelectedListener(this);

        provinciasBD(new VolleyCallback()
        {
            @Override
            public void onSuccess(String result)
            {
                JSONArray jsonArray= new JSONArray();

                try
                {
                    jsonArray = new JSONArray(result);

                    for(int i=0;i<jsonArray.length();i++)
                    {
                        String nombreProvincia=jsonArray.getJSONObject(i).getString("nombre");
                        arrayNombreProvincias.add(nombreProvincia);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        //Mostrar las ciudades
        aAProvincias=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayNombreProvincias);
        aAProvincias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spProvincias.setAdapter(aAProvincias);
        /* FIN SELECT PROVINCIAS */


    }

    public void registrarUsuario(final VolleyCallback callback, final String nombre, final String telefono, final String correo, final String password)
    {
        Log.d("Datos Registro",nombre);
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

    public void provinciasBD(final VolleyCallback callback)
    {
        String URL = "https://www.rafaelpedrajas.com/android/listadoCiudades.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response)
                    {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(Registro.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(Registro.this);
        queue.add(stringRequest);

    }


    public void universidadesBD(final VolleyCallback callback, final int idProvincia)
    {
        JSONArray jsonArray= new JSONArray();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/universidadesPorProvincia.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("Console Universidades",response);
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
                params.put("idProvincia", String.valueOf(idProvincia));

                return params;
            }
        };
        queue.add(stringRequest);
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        switch (adapterView.getId())
        {
            case R.id.spProvincias:
            {
                //Do something
                //Toast.makeText(this, "Spinner: " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();

                Log.d("Item spinner",String.valueOf(i));

                //Si ha seleccionado alguna ciudad, cargamos las universidades de esa ciudad
                if(i!=-1)
                {
                    //Lo ponemos visible
                    spUniversidades.setVisibility(View.VISIBLE);

                    arrayNombreUniversidades.clear();
                    spUniversidades.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrayNombreUniversidades));

                    universidadesBD(new VolleyCallback()
                    {
                        @Override
                        public void onSuccess(String result)
                        {
                            JSONArray jsonArray= new JSONArray();

                            try
                            {
                                if(!result.equals("No hay datos"))
                                {
                                    jsonArray = new JSONArray(result);

                                    for(int i = 0; i < jsonArray.length(); i++)
                                    {
                                        String nombreUniversidad = jsonArray.getJSONObject(i).getString("nombre");
                                        Log.d("Nombre Universidad", nombreUniversidad);
                                        arrayNombreUniversidades.add(nombreUniversidad);
                                    }
                                }
                            }
                            catch(Exception e)
                            {
                                e.printStackTrace();
                            }
                        }
                    },i+1);

                    aAUniversidades=new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item, arrayNombreUniversidades);
                    aAUniversidades.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spUniversidades.setAdapter(aAUniversidades);

                }
                else
                {
                    spUniversidades.setVisibility(View.GONE);
                }

                /*
                switch(i)
                {
                    case 0:
                        Toast.makeText(adapterView.getContext(), "Spinner item 1!", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(adapterView.getContext(), "Spinner item 2!", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(adapterView.getContext(), "Spinner item 3!", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        Toast.makeText(adapterView.getContext(), "Nada seleccionado", Toast.LENGTH_SHORT).show();
                        break;
                }
                */

                break;
            }
            case R.id.spUniversidades:
            {
                //Do another thing
                //Toast.makeText(this, "Option Selected: " + adapterView.getSelectedItem().toString(), Toast.LENGTH_SHORT).show();
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}
