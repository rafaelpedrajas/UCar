package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
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
    MaterialSpinner spProvincias, spUniversidades, spPlazas, spAños;
    ArrayAdapter<String> aAProvincias, aAUniversidades;
    ArrayAdapter<CharSequence> aAPlazas, aAAños;
    // Session Manager Class
    SessionManager session;

    LinearLayout elegirCoche;
    LinearLayout primerLayout;
    LinearLayout segundoLayout;
    LinearLayout datosCoche;

    Button botonRegistro;
    Button botonContinuar;

    TextInputLayout tILNombre, tILApellido, tILTelefono, tILCorreo, tILPass;
    EditText eTNombre, eTApellido, eTTelefono, eTCorreo, eTPass;

    SwitchCompat switchCoche;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        //Inicializacion de variables
        primerLayout = (LinearLayout) findViewById(R.id.primerLayout);
        segundoLayout = (LinearLayout) findViewById(R.id.segundoLayout);

        elegirCoche = (LinearLayout) findViewById(R.id.elegirCoche);
        datosCoche = (LinearLayout) findViewById(R.id.datosCoche);

        switchCoche = (SwitchCompat)findViewById(R.id.switchCoche);

        spPlazas = (MaterialSpinner) findViewById(R.id.spPlazas);
        spAños = (MaterialSpinner) findViewById(R.id.spAños);

        tILNombre=(TextInputLayout)findViewById(R.id.tILNombre);
        eTNombre=(EditText)findViewById(R.id.eTNombre);
        tILApellido=(TextInputLayout)findViewById(R.id.tILApellido);
        eTApellido=(EditText)findViewById(R.id.eTApellido);
        tILTelefono=(TextInputLayout)findViewById(R.id.tILTelefono);
        eTTelefono=(EditText)findViewById(R.id.eTTelefono);
        tILCorreo=(TextInputLayout)findViewById(R.id.tILCorreo);
        eTCorreo=(EditText)findViewById(R.id.eTCorreo);
        tILPass=(TextInputLayout)findViewById(R.id.tILPass);
        eTPass=(EditText)findViewById(R.id.eTPass);


        primerLayout.setVisibility(View.VISIBLE);
        segundoLayout.setVisibility(View.GONE);

        //Asignacion de variables
        aAPlazas = ArrayAdapter.createFromResource(this, R.array.opcionesPlaza, android.R.layout.simple_spinner_dropdown_item);
        spPlazas.setAdapter(aAPlazas);

        aAAños = ArrayAdapter.createFromResource(this, R.array.opcionesAño, android.R.layout.simple_spinner_dropdown_item);
        spAños.setAdapter(aAAños);




        //Funcionalidad botones vistas
        botonRegistro = (Button)findViewById(R.id.botonRegistro);
        botonContinuar = (Button)findViewById(R.id.botonContinuar);

        botonContinuar.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                boolean confirmarPrimerRegistro=true;

                //Comprobar nombre
                if(eTNombre.getText().toString().trim().equals(""))
                {
                    confirmarPrimerRegistro=false;
                    tILNombre.setError("El nombre es obligatorio");
                }
                else
                {
                    tILNombre.setError(null);
                }

                //Comprobar apellido
                if(eTApellido.getText().toString().trim().equals(""))
                {
                    confirmarPrimerRegistro=false;
                    tILApellido.setError("El apellido es obligatorio");
                }
                else
                {
                    tILApellido.setError(null);
                }

                //Comprobar correo
                if(eTCorreo.getText().toString().trim().equals(""))
                {
                    confirmarPrimerRegistro=false;
                    tILCorreo.setError("El correo es obligatorio");
                }
                else
                {
                    tILCorreo.setError(null);
                }

                //Comprobar contraseña
                if(eTPass.getText().toString().trim().equals(""))
                {
                    confirmarPrimerRegistro=false;
                    tILPass.setError("La contraseña es obligatoria");
                }
                else
                {
                    tILPass.setError(null);
                }

                if(confirmarPrimerRegistro)
                {
                    primerLayout.setVisibility(View.GONE);
                    segundoLayout.setVisibility(View.VISIBLE);
                }

            }
        });

        switchCoche.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener()
        {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b)
            {
                if(switchCoche.isChecked())
                {
                    datosCoche.setVisibility(View.VISIBLE);
                }
                else
                {
                    datosCoche.setVisibility(View.GONE);
                }
            }
        });

        botonRegistro.setOnClickListener(new View.OnClickListener()
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
                    elegirCoche.setVisibility(View.GONE);
                    botonRegistro.setVisibility(View.GONE);
                    switchCoche.setChecked(false);
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
                if(i!=-1)
                {
                    //Ponemos la capa del coche y el boton de registrar visible
                    elegirCoche.setVisibility(View.VISIBLE);
                    botonRegistro.setVisibility(View.VISIBLE);
                }
                else
                {
                    elegirCoche.setVisibility(View.GONE);
                    botonRegistro.setVisibility(View.GONE);
                    switchCoche.setChecked(false);
                }
                break;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }
}
