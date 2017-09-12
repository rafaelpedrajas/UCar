package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    // Session Manager Class
    SessionManager session;
    TextView tvLogIn;
    TextView tvRegistro;
    TextView tvNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //Inicializar toolbar y menu
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), CrearAnuncio.class);
                startActivity(i);
                //Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerview = navigationView.getHeaderView(0);
        LinearLayout header = (LinearLayout) headerview.findViewById(R.id.item_menu_perfil);
        header.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                Intent i = new Intent(getApplicationContext(),Perfil.class);
                startActivity(i);
            }
        });



        /*LISTADO ANUNCIOS*/
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "https://www.rafaelpedrajas.com/android/listadoAnuncios.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("Console",response);
            }
        },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        Log.d("Console","Error en el web service");
                    }
                });
        queue.add(stringRequest);
        /*FIN LISTADO ANUNCIOS*/





        //Poner los linear layout, hay que rellenarlos con datos de la BD
        TextView destino = (TextView)findViewById(R.id.destino);
        TextView dia = (TextView)findViewById(R.id.dia);
        TextView hora = (TextView)findViewById(R.id.hora);

        destino.setText("UCO : Rabanales");
        dia.setText("Lunes 29");
        hora.setText("08:30");

        //Cuando se pincha en el layout de cada anuncio e4n el main

        LinearLayout layoutAnuncio = (LinearLayout)findViewById(R.id.layout_anuncio_main);
        layoutAnuncio.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent i = new Intent(getApplicationContext(), VerAnuncio.class);
                startActivity(i);
            }
        });


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

        //---------------CONTROL SESION USUARIO--------------------------------

        //Hay que meterlo aqui porque es donde se crea el menu lateral, sino falla

        // Session class instance
        session = new SessionManager(getApplicationContext());


        LinearLayout layoutLogIn = (LinearLayout) findViewById(R.id.layout_log_in);
        LinearLayout layoutPerfil = (LinearLayout) findViewById(R.id.layout_perfil);


        if(session.isLoggedIn()){

            tvNombre = (TextView)findViewById(R.id.tvNombre);
            HashMap<String, String> user = session.getUserDetails();

            tvNombre.setText(user.get(SessionManager.KEY_NOMBRE));
            layoutLogIn.setVisibility(View.GONE);
            layoutPerfil.setVisibility(View.VISIBLE);
        }
        else{
            layoutLogIn.setVisibility(View.VISIBLE);
            layoutPerfil.setVisibility(View.GONE);

            tvLogIn = (TextView)findViewById(R.id.tVLogIn);
            tvRegistro = (TextView)findViewById(R.id.tVResgistro);

            tvLogIn.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent iniciarSesion = new Intent(getApplicationContext(),Login.class);
                    startActivity(iniciarSesion);
                }
            });

            tvRegistro.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View view)
                {
                    Intent registro = new Intent(getApplicationContext(),Registro.class);
                    startActivity(registro);
                }
            });


        }


        //------------FIN CONTROL SESION USUARIO-------------------------

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action

            Intent nuevaVentana = new Intent(getApplicationContext(),CrearAnuncio.class);
            startActivity(nuevaVentana);

        } else if (id == R.id.nav_calendario) {

            Intent nuevaVentana = new Intent(getApplicationContext(),CrearAnuncio.class);
            startActivity(nuevaVentana);

        } else if (id == R.id.nav_ayuda) {

            Intent nuevaVentana = new Intent(getApplicationContext(),Ayuda.class);
            startActivity(nuevaVentana);

        } else if (id == R.id.nav_contacto) {

            Intent nuevaVentana = new Intent(getApplicationContext(),Contacto.class);
            startActivity(nuevaVentana);

        } else if (id == R.id.nav_logout) {
            session.logoutUser();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
