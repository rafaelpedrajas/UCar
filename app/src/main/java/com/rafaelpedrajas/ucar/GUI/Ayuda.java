package com.rafaelpedrajas.ucar.GUI;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.rafaelpedrajas.ucar.R;

public class Ayuda extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ayuda);

        //Inicializar toolbar
        ImageButton back = (ImageButton) findViewById(R.id.back);
        ImageButton done = (ImageButton) findViewById(R.id.done);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);



        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Ayuda");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAtras();
            }
        });

        //----------------- Crear la ayuda -------------------


        //--------------------PRIMER LAYOUT--------------------------------

        final ImageView verMasPrimero = (ImageView) findViewById(R.id.image_ver_mas_primer_layout);
        final ImageView verMenosPrimero = (ImageView)findViewById(R.id.image_ver_menos_primer_layout);
        final TextView tPrimero = (TextView)findViewById(R.id.descripcion_primer_layout);
        final TextView tLPrimero = (TextView)findViewById(R.id.descripcion_larga_primer_layout);

        verMasPrimero.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tPrimero.setVisibility(View.GONE);
                tLPrimero.setVisibility(View.VISIBLE);
                verMasPrimero.setVisibility(View.GONE);
                verMenosPrimero.setVisibility(View.VISIBLE);
            }
        });

        verMenosPrimero.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tPrimero.setVisibility(View.VISIBLE);
                tLPrimero.setVisibility(View.GONE);
                verMasPrimero.setVisibility(View.VISIBLE);
                verMenosPrimero.setVisibility(View.GONE);
            }
        });

        //---------------- SEGUNDO LAYOUT ---------------------------

        final ImageView verMasSegundo = (ImageView) findViewById(R.id.image_ver_mas_segundo_layout);
        final ImageView verMenosSegundo  = (ImageView)findViewById(R.id.image_ver_menos_segundo_layout);
        final TextView tSegundo  = (TextView)findViewById(R.id.descripcion_segundo_layout);
        final TextView tLSegundo  = (TextView)findViewById(R.id.descripcion_larga_segundo_layout);

        verMasSegundo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tSegundo.setVisibility(View.GONE);
                tLSegundo.setVisibility(View.VISIBLE);
                verMasSegundo.setVisibility(View.GONE);
                verMenosSegundo.setVisibility(View.VISIBLE);
            }
        });

        verMenosSegundo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tSegundo.setVisibility(View.VISIBLE);
                tLSegundo.setVisibility(View.GONE);
                verMasSegundo.setVisibility(View.VISIBLE);
                verMenosSegundo.setVisibility(View.GONE);
            }
        });

        //---------------- TERCER LAYOUT ---------------------------

        final ImageView verMasTercer = (ImageView) findViewById(R.id.image_ver_mas_tercer_layout);
        final ImageView verMenosTercer  = (ImageView)findViewById(R.id.image_ver_menos_tercer_layout);
        final TextView tTercer  = (TextView)findViewById(R.id.descripcion_tercer_layout);
        final TextView tLTercer  = (TextView)findViewById(R.id.descripcion_larga_tercer_layout);

        verMasTercer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tTercer.setVisibility(View.GONE);
                tLTercer.setVisibility(View.VISIBLE);
                verMasTercer.setVisibility(View.GONE);
                verMenosTercer.setVisibility(View.VISIBLE);
            }
        });

        verMenosTercer.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tTercer.setVisibility(View.VISIBLE);
                tLTercer.setVisibility(View.GONE);
                verMasTercer.setVisibility(View.VISIBLE);
                verMenosTercer.setVisibility(View.GONE);
            }
        });

        //---------------- CUARTO LAYOUT ---------------------------

        final ImageView verMasCuarto = (ImageView) findViewById(R.id.image_ver_mas_cuarto_layout);
        final ImageView verMenosCuarto  = (ImageView)findViewById(R.id.image_ver_menos_cuarto_layout);
        final TextView tCuarto  = (TextView)findViewById(R.id.descripcion_cuarto_layout);
        final TextView tLCuarto  = (TextView)findViewById(R.id.descripcion_larga_cuarto_layout);

        verMasCuarto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tCuarto.setVisibility(View.GONE);
                tLCuarto.setVisibility(View.VISIBLE);
                verMasCuarto.setVisibility(View.GONE);
                verMenosCuarto.setVisibility(View.VISIBLE);
            }
        });

        verMenosCuarto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tCuarto.setVisibility(View.VISIBLE);
                tLCuarto.setVisibility(View.GONE);
                verMasCuarto.setVisibility(View.VISIBLE);
                verMenosCuarto.setVisibility(View.GONE);
            }
        });

        //---------------- QUINTO LAYOUT ---------------------------

        final ImageView verMasQuinto = (ImageView) findViewById(R.id.image_ver_mas_quinto_layout);
        final ImageView verMenosQuinto  = (ImageView)findViewById(R.id.image_ver_menos_quinto_layout);
        final TextView tQuinto  = (TextView)findViewById(R.id.descripcion_quinto_layout);
        final TextView tLQuinto = (TextView)findViewById(R.id.descripcion_larga_quinto_layout);

        verMasQuinto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tQuinto.setVisibility(View.GONE);
                tLQuinto.setVisibility(View.VISIBLE);
                verMasQuinto.setVisibility(View.GONE);
                verMenosQuinto.setVisibility(View.VISIBLE);
            }
        });

        verMenosQuinto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tQuinto.setVisibility(View.VISIBLE);
                tLQuinto.setVisibility(View.GONE);
                verMasQuinto.setVisibility(View.VISIBLE);
                verMenosQuinto.setVisibility(View.GONE);
            }
        });

        //---------------- SEXTO LAYOUT ---------------------------

        final ImageView verMasSexto = (ImageView) findViewById(R.id.image_ver_mas_sexto_layout);
        final ImageView verMenosSexto  = (ImageView)findViewById(R.id.image_ver_menos_sexto_layout);
        final TextView tSexto  = (TextView)findViewById(R.id.descripcion_sexto_layout);
        final TextView tLSexto = (TextView)findViewById(R.id.descripcion_larga_sexto_layout);

        verMasSexto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tSexto.setVisibility(View.GONE);
                tLSexto.setVisibility(View.VISIBLE);
                verMasSexto.setVisibility(View.GONE);
                verMenosSexto.setVisibility(View.VISIBLE);
            }
        });

        verMenosSexto.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tSexto.setVisibility(View.VISIBLE);
                tLSexto.setVisibility(View.GONE);
                verMasSexto.setVisibility(View.VISIBLE);
                verMenosSexto.setVisibility(View.GONE);
            }
        });

        //---------------- SEPTIMO LAYOUT ---------------------------

        final ImageView verMasSeptimo = (ImageView) findViewById(R.id.image_ver_mas_septimo_layout);
        final ImageView verMenosSeptimo  = (ImageView)findViewById(R.id.image_ver_menos_septimo_layout);
        final TextView tSeptimo  = (TextView)findViewById(R.id.descripcion_septimo_layout);
        final TextView tLSeptimo = (TextView)findViewById(R.id.descripcion_larga_septimo_layout);

        verMasSeptimo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tSeptimo.setVisibility(View.GONE);
                tLSeptimo.setVisibility(View.VISIBLE);
                verMasSeptimo.setVisibility(View.GONE);
                verMenosSeptimo.setVisibility(View.VISIBLE);
            }
        });

        verMenosSeptimo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tSeptimo.setVisibility(View.VISIBLE);
                tLSeptimo.setVisibility(View.GONE);
                verMasSeptimo.setVisibility(View.VISIBLE);
                verMenosSeptimo.setVisibility(View.GONE);
            }
        });

        //---------------- OCTAVO LAYOUT ---------------------------

        final ImageView verMasOctavo = (ImageView) findViewById(R.id.image_ver_mas_octavo_layout);
        final ImageView verMenosOctavo  = (ImageView)findViewById(R.id.image_ver_menos_octavo_layout);
        final TextView tOctavo  = (TextView)findViewById(R.id.descripcion_octavo_layout);
        final TextView tLOctavo = (TextView)findViewById(R.id.descripcion_larga_octavo_layout);

        verMasOctavo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tOctavo.setVisibility(View.GONE);
                tLOctavo.setVisibility(View.VISIBLE);
                verMasOctavo.setVisibility(View.GONE);
                verMenosOctavo.setVisibility(View.VISIBLE);
            }
        });

        verMenosOctavo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tOctavo.setVisibility(View.VISIBLE);
                tLOctavo.setVisibility(View.GONE);
                verMasOctavo.setVisibility(View.VISIBLE);
                verMenosOctavo.setVisibility(View.GONE);
            }
        });

        //---------------- NOVENO LAYOUT ---------------------------

        final ImageView verMasNoveno = (ImageView) findViewById(R.id.image_ver_mas_noveno_layout);
        final ImageView verMenosNoveno  = (ImageView)findViewById(R.id.image_ver_menos_noveno_layout);
        final TextView tNoveno  = (TextView)findViewById(R.id.descripcion_noveno_layout);
        final TextView tLNoveno = (TextView)findViewById(R.id.descripcion_larga_noveno_layout);

        verMasNoveno.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tNoveno.setVisibility(View.GONE);
                tLNoveno.setVisibility(View.VISIBLE);
                verMasNoveno.setVisibility(View.GONE);
                verMenosNoveno.setVisibility(View.VISIBLE);
            }
        });

        verMenosNoveno.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tNoveno.setVisibility(View.VISIBLE);
                tLNoveno.setVisibility(View.GONE);
                verMasNoveno.setVisibility(View.VISIBLE);
                verMenosNoveno.setVisibility(View.GONE);
            }
        });

        //---------------- DECIMO LAYOUT ---------------------------

        final ImageView verMasDecimo = (ImageView) findViewById(R.id.image_ver_mas_decimo_layout);
        final ImageView verMenosDecimo  = (ImageView)findViewById(R.id.image_ver_menos_decimo_layout);
        final TextView tDecimo = (TextView)findViewById(R.id.descripcion_decimo_layout);
        final TextView tLDecimo = (TextView)findViewById(R.id.descripcion_larga_decimo_layout);

        verMasDecimo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tDecimo.setVisibility(View.GONE);
                tLDecimo.setVisibility(View.VISIBLE);
                verMasDecimo.setVisibility(View.GONE);
                verMenosDecimo.setVisibility(View.VISIBLE);
            }
        });

        verMenosDecimo.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                tDecimo.setVisibility(View.VISIBLE);
                tLDecimo.setVisibility(View.GONE);
                verMasDecimo.setVisibility(View.VISIBLE);
                verMenosDecimo.setVisibility(View.GONE);
            }
        });




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
