package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.rafaelpedrajas.ucar.Complementos.WorkaroundMapFragment;

import java.util.HashMap;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

public class CrearAnuncio extends AppCompatActivity implements OnMapReadyCallback
{

    // Session Manager Class
    SessionManager session;

    MaterialSpinner spProvincias, spUniversidades, spPlazas, spAños;

    TextInputLayout tILDia, tILHora, tILPrecio, tILMarca, tILModelo;
    EditText etDia, etHora, etPrecio, etMarca, etModelo;

    private GoogleMap mapa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        //Comprobamos que este logueado para crear un anuncio,
        // si no, se reenvia a la pagina de log
        session.checkLogin();

        //Inicializar toolbar

        ImageButton back = (ImageButton) findViewById(R.id.back);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Crear Anuncio");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAtras();
            }
        });

        //Incializar variables

        spPlazas = (MaterialSpinner) findViewById(R.id.spPlazas);
        spAños = (MaterialSpinner) findViewById(R.id.spAños);
        tILDia=(TextInputLayout)findViewById(R.id.tILDia);
        etDia=(EditText)findViewById(R.id.eTDia);
        tILHora=(TextInputLayout)findViewById(R.id.tILHora);
        etHora=(EditText)findViewById(R.id.eTHora);
        tILPrecio=(TextInputLayout)findViewById(R.id.tILPrecio);
        etPrecio=(EditText)findViewById(R.id.eTPrecio);
        tILMarca=(TextInputLayout)findViewById(R.id.tILMarca);
        etMarca=(EditText)findViewById(R.id.eTMarca);
        tILModelo=(TextInputLayout)findViewById(R.id.tILModelo);
        etModelo=(EditText)findViewById(R.id.eTModelo);


        Button botonCrear = (Button)findViewById(R.id.botonCrear);
        botonCrear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                boolean camposRellenos = true;
                int posicionProvincias = spProvincias.getSelectedItemPosition();
                int posicionUniversidades = spUniversidades.getSelectedItemPosition();

                //---  COMPROBAR CAMPOS RELLENOS----------

                //Comprobación de la provincia
                if(posicionProvincias==0)
                {
                    camposRellenos=false;
                    spProvincias.setError("La provincia es obligatoria");
                }
                else
                {
                    spProvincias.setError(null);
                }

                //Comprobación de la universidad
                if(posicionUniversidades==0)
                {
                    camposRellenos=false;
                    spUniversidades.setError("La universidad es obligatoria");
                }
                else
                {
                    spUniversidades.setError(null);
                }

                //Comprobación del precio
                if(etPrecio.getText().toString().trim().equals(""))
                {
                    camposRellenos=false;
                    tILPrecio.setError("El precio es obligatorio");
                }
                else
                {
                    tILPrecio.setError(null);
                }

                //Comprobación de la marca
                if(etMarca.getText().toString().trim().equals(""))
                {
                    camposRellenos=false;
                    tILMarca.setError("La marca es obligatoria");
                }
                else
                {
                    tILMarca.setError(null);
                }

                //Comprobación del modelo
                if(etModelo.getText().toString().trim().equals(""))
                {
                    camposRellenos=false;
                    tILModelo.setError("El modelo es obligatoriao");
                }
                else
                {
                    tILModelo.setError(null);
                }

                //Comprobación de las plazas
                if(spPlazas.getSelectedItemPosition()==0)
                {
                    camposRellenos=false;
                    spPlazas.setError("Las plazas son obligatorias");
                }
                else
                {
                    spPlazas.setError(null);
                }

                if(camposRellenos){
                    /*CREAR ANUNCIO*/
                    String URL = "https://www.rafaelpedrajas.com/android/insertarAnuncio.php";

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                            new Response.Listener<String>()
                            {
                                @Override
                                public void onResponse(String response)
                                {
                                    Log.d("Console",response);
                                    //Toast.makeText(CrearAnuncio.this,response,Toast.LENGTH_LONG).show();
                                }
                            },
                            new Response.ErrorListener()
                            {
                                @Override
                                public void onErrorResponse(VolleyError error)
                                {
                                    Toast.makeText(CrearAnuncio.this,error.toString(),Toast.LENGTH_LONG).show();
                                }
                            })
                    {
                        @Override
                        protected Map<String,String> getParams()
                        {
                            Map<String,String> params = new HashMap<String, String>();
                            params.put("idUsuario", "1");
                            params.put("idCoche", "1");
                            params.put("puntoEncuentro", "toloco");
                            params.put("precio", "0.50");
                            return params;
                        }

                    };

                    RequestQueue queue = Volley.newRequestQueue(CrearAnuncio.this);
                    queue.add(stringRequest);
                    /*FIN CREAR ANUNCIO*/


                    Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                    startActivity(volver);

                }

            }
        });


        //MAPA
        MapFragment mapFragment = (MapFragment) getFragmentManager().findFragmentById(R.id.map);

        mapFragment.getMapAsync(this);

        /*
        // check if we have got the googleMap already
        if (mapa == null) {
            //mapa = ((WorkaroundMapFragment) getFragmentManager().findFragmentById(R.id.map)).getMapAsync(onMapReady(mapa));
            //mapa.setMapType(GoogleMap.MAP_TYPE_NORMAL);
            //mapa.getUiSettings().setZoomControlsEnabled(true);

            final ScrollView mScrollView = (ScrollView) findViewById(R.id.scrollAnuncio); //parent scrollview in xml, give your scrollview id value

            ((WorkaroundMapFragment) getFragmentManager().findFragmentById(R.id.map))
                    .setListener(new WorkaroundMapFragment.OnTouchListener() {
                        @Override
                        public void onTouch() {
                            mScrollView.requestDisallowInterceptTouchEvent(true);
                        }
                    });

        }
        */

        final ScrollView scrollAnuncio = (ScrollView) findViewById(R.id.scrollAnuncio);
        ImageView transparent = (ImageView)findViewById(R.id.imageViewTransparente);

        transparent.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                int action = event.getAction();
                switch (action) {
                    case MotionEvent.ACTION_DOWN:
                        // Disallow ScrollView to intercept touch events.
                        scrollAnuncio.requestDisallowInterceptTouchEvent(true);
                        // Disable touch on transparent view
                        return false;

                    case MotionEvent.ACTION_UP:
                        // Allow ScrollView to intercept touch events.
                        scrollAnuncio.requestDisallowInterceptTouchEvent(false);
                        return true;

                    case MotionEvent.ACTION_MOVE:
                        scrollAnuncio.requestDisallowInterceptTouchEvent(true);
                        return false;

                    default:
                        return true;
                }
            }
        });

        //FIN MAPA
    }

    private void volverAtras(){
        Intent volver = new Intent(getApplicationContext(),MainActivity.class);
        startActivity(volver);
    }
    @Override
    public void onBackPressed (){
        volverAtras();
    }


    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mapa=googleMap;
        googleMap.getUiSettings().setZoomControlsEnabled(true);
        //googleMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
    }
}
