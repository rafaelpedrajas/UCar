package com.rafaelpedrajas.ucar;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

public class CrearAnuncio extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crear_anuncio);

        ImageButton back = (ImageButton) findViewById(R.id.back);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);

        back.setVisibility(View.VISIBLE);
        tituloVentana.setText("Crear Anuncio");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
            }
        });

        Button botonCrear = (Button)findViewById(R.id.botonCrear);
        botonCrear.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                Intent volver = new Intent(getApplicationContext(),MainActivity.class);
                startActivity(volver);
            }
        });
    }


}
