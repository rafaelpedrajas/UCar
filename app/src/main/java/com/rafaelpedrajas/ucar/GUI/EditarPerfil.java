package com.rafaelpedrajas.ucar.GUI;

import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.rafaelpedrajas.ucar.Clases.Coche;
import com.rafaelpedrajas.ucar.Interfaces.VolleyCallback;
import com.rafaelpedrajas.ucar.R;
import com.rafaelpedrajas.ucar.Sesion.SessionManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fr.ganfra.materialspinner.MaterialSpinner;

//Import para usar la camara
import static android.Manifest.permission.CAMERA;
import static android.Manifest.permission.WRITE_EXTERNAL_STORAGE;

public class EditarPerfil extends AppCompatActivity implements AdapterView.OnItemSelectedListener
{

    //Variables para camara
    private static String APP_DIRECTORY = "MyPictureApp/";
    private static String MEDIA_DIRECTORY = APP_DIRECTORY + "PictureApp";

    private final int MY_PERMISSIONS = 100;
    private final int PHOTO_CODE = 200;
    private final int SELECT_PICTURE = 300;
    private String mPath;

    private ImageView mSetImage;
    private LinearLayout linearLayoutContenedorImagen;

    private int posicionCochePorDefecto=0;

    //Otras variables

    List<String> arrayNombreCiudades = new ArrayList<>();
    List<String> arrayNombreUniversidades = new ArrayList<>();
    List<String> arrayNombreCoches = new ArrayList<>();

    //String para recoger los datos que se almacenan en R.array.plazas y R.array.años
    String[] stringPlazas;
    String[] stringAños;

    //Listas para convertir los String[] plazas y años en Listas y poder usar el "indexOf" para marcar en el spinner las plazas y años del coche
    List<String> arrayPlazas;
    List<String> arrayAños;

    List<Coche> arrayCoches = new ArrayList<>();
    List<Coche> arrayCochesBorrados = new ArrayList<>();

    MaterialSpinner spProvincias, spUniversidades, spCoches, spPlazas, spAños;

    ArrayAdapter<String> aACiudades, aAUniversidades, aACoches;
    ArrayAdapter<CharSequence> aAPlazas, aAAños;

    TextInputLayout tILMarca, tILModelo, tILConsumo;
    EditText etMarca, etModelo, eTConsumo;

    SwitchCompat switchCochePorDefecto;

    // Session Manager Class
    SessionManager session;

    HashMap<String, String> user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_perfil);

        // Session class instance
        session = new SessionManager(getApplicationContext());

        //Comprobamos si esta logeado
        session.checkLogin();

        SharedPreferences settings = getSharedPreferences("AndroidHivePref", MODE_PRIVATE);
        final SharedPreferences.Editor prefEditor = settings.edit();

        user = session.getUserDetails();

        //Inicializar toolbar
        ImageButton back = (ImageButton) findViewById(R.id.back);
        ImageButton done = (ImageButton) findViewById(R.id.done);
        TextView tituloVentana = (TextView) findViewById(R.id.tituloVentana);
        mSetImage = (ImageView) findViewById(R.id.set_picture);
        linearLayoutContenedorImagen = (LinearLayout)findViewById(R.id.layout_contenedor_imagen);


        back.setVisibility(View.VISIBLE);
        done.setVisibility(View.VISIBLE);
        tituloVentana.setText("EditarPerfil");

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                volverAtras();
            }
        });


        //Agregamos las plazas y los años por defecto
        //Obtenemos los datos de R.array....
        stringPlazas = getResources().getStringArray(R.array.opcionesPlaza);
        stringAños = getResources().getStringArray(R.array.opcionesAño);

        //Los convertimos en listas
        arrayPlazas = Arrays.asList(stringPlazas);
        arrayAños = Arrays.asList(stringAños);



        //DATOS POR DEFECTO USUARIO

        final TextInputEditText nombre = (TextInputEditText) findViewById(R.id.eTNombre);
        final TextInputEditText apellido = (TextInputEditText) findViewById(R.id.eTApellido);
        final TextInputEditText correo = (TextInputEditText) findViewById(R.id.eTCorreo);
        final TextInputEditText telefono = (TextInputEditText) findViewById(R.id.eTTelefono);

        nombre.setText(user.get(SessionManager.KEY_NOMBRE));
        apellido.setText(user.get(SessionManager.KEY_APELLIDO));
        correo.setText(user.get(SessionManager.KEY_CORREO));
        telefono.setText(user.get(SessionManager.KEY_TELEFONO));

        /* SELECT CIUDADES */
        //Cargar las ciudades de la BD
        spProvincias=(MaterialSpinner) findViewById(R.id.spProvincias);
        spProvincias.setOnItemSelectedListener(this);

        ciudadesBD(new VolleyCallback()
        {
            @Override
            synchronized public void onSuccess(String result)
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

                    //Mostrar las ciudades
                    aACiudades=new ArrayAdapter<String>(getApplicationContext(),R.layout.estilo_spinner, arrayNombreCiudades);
                    aACiudades.setDropDownViewResource(R.layout.estilo_items_spinner);
                    spProvincias.setAdapter(aACiudades);

                    spProvincias.setSelection(Integer.parseInt(user.get(SessionManager.KEY_ID_PROVINCIA)));
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        /* FIN SELECT CIUDADES */


        //SELECT UNIVERSIDAD
        spUniversidades=(MaterialSpinner) findViewById(R.id.spUniversidades);
        spUniversidades.setOnItemSelectedListener(this);

        spUniversidades.setAdapter(new ArrayAdapter<String>(this,android.R.layout.simple_spinner_dropdown_item,arrayNombreUniversidades));

        universidadesBD(new VolleyCallback()
        {
            @Override
            synchronized public void onSuccess(String result)
            {
                JSONArray jsonArray= new JSONArray();

                try
                {
                    if(!result.equals("No hay datos"))
                    {
                        jsonArray = new JSONArray(result);

                        List<Integer> idsUniversidades=new ArrayList<Integer>();

                        for(int i = 0; i < jsonArray.length(); i++)
                        {
                            String nombreUniversidad = jsonArray.getJSONObject(i).getString("nombre");
                            Log.d("Nombre Universidad", nombreUniversidad);
                            arrayNombreUniversidades.add(nombreUniversidad);

                            idsUniversidades.add(jsonArray.getJSONObject(i).getInt("idUniversidad"));
                        }

                        aAUniversidades=new ArrayAdapter<String>(getApplicationContext(),R.layout.estilo_spinner, arrayNombreUniversidades);
                        aAUniversidades.setDropDownViewResource(R.layout.estilo_items_spinner);
                        spUniversidades.setAdapter(aAUniversidades);

                        int indexUniversidad=0;
                        for(int i=0;i<idsUniversidades.size();i++)
                        {
                            if(idsUniversidades.get(i)==Integer.parseInt(user.get(SessionManager.KEY_ID_UNIVERSIDAD)))
                            {
                                indexUniversidad=i;
                                i=idsUniversidades.size();
                            }
                        }

                        //Ponemos la universidad del usuario seleccionada por defecto
                        spUniversidades.setSelection(indexUniversidad+1);

                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        },Integer.parseInt(user.get(SessionManager.KEY_ID_PROVINCIA)));





        //GUARDAR DATOS EN BD Y EN SESION
        done.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                editarUsuario(new VolleyCallback()
                {
                    @Override
                    synchronized public void onSuccess(String result)
                    {
                        JSONArray jsonArray;

                        String nombreProvincia= spProvincias.getSelectedItem().toString();
                        String nombreUniversidad= spUniversidades.getSelectedItem().toString();
                        int idProvincia= spProvincias.getSelectedItemPosition();
                        int indexUniversidad= spUniversidades.getSelectedItemPosition();

                        try
                        {

                            if(!result.equals("No hay datos"))
                            {
                                jsonArray = new JSONArray(result);

                                int idUniversidad = jsonArray.getJSONObject(0).getInt("idUniversidad");

                                prefEditor.putString("correo",correo.getText().toString().trim());
                                prefEditor.putString("nombre",nombre.getText().toString().trim());
                                prefEditor.putString("apellido",apellido.getText().toString().trim());
                                prefEditor.putString("telefono",telefono.getText().toString().trim());
                                prefEditor.putString("nombreUniversidad",nombreUniversidad);
                                prefEditor.putString("nombreProvincia",nombreProvincia);
                                prefEditor.putString("idUniversidad",String.valueOf(idUniversidad));
                                prefEditor.putString("idProvincia",String.valueOf(idProvincia));
                                prefEditor.apply();

                                Intent aceptar = new Intent(getApplicationContext(),Perfil.class);
                                startActivity(aceptar);

                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },correo.getText().toString().trim(), nombre.getText().toString().trim(), apellido.getText().toString().trim(), telefono.getText().toString().trim(), spProvincias.getSelectedItemPosition(), spUniversidades.getSelectedItemPosition());



            }
        });



        //CAMBIAR CONTRASEÑA
        Button cambiarPass = (Button)findViewById(R.id.BTCambiarPass);
        cambiarPass.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditarPerfil.this);
                View mView = getLayoutInflater().inflate(R.layout.cambiar_pass, null);

                final TextInputLayout tILPassActual = mView.findViewById(R.id.tILPassActual);
                final TextInputLayout tILNuevaPass = mView.findViewById(R.id.tILNuevaPass);
                final TextInputLayout tILRepetirPass = mView.findViewById(R.id.tILRepetirPass);

                final TextInputEditText passActual = mView.findViewById(R.id.eTPassActual);
                final TextInputEditText nuevaPass = mView.findViewById(R.id.eTNuevaPass);
                final TextInputEditText repetirPass = mView.findViewById(R.id.eTRepetirPass);
                Button aceptarCambiarPass = mView.findViewById(R.id.aceptarCambiarPass);
                Button cancelarCambiarPass = mView.findViewById(R.id.cancelarCambiarPass);

                //Aceptar cambiar pass
                aceptarCambiarPass.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        boolean confirmarPrimerRegistro=true;

                        //Comprobar contraseña actual
                        if(passActual.getText().toString().trim().equals(""))
                        {
                            confirmarPrimerRegistro=false;
                            tILPassActual.setError("La contraseña actual es obligatoria");
                        }
                        else
                        {
                            tILPassActual.setError(null);
                        }

                        //Comprobar nueva contraseña
                        if(nuevaPass.getText().toString().trim().equals(""))
                        {
                            confirmarPrimerRegistro=false;
                            tILNuevaPass.setError("La nueva contraseña es obligatoria");
                        }
                        else
                        {
                            tILNuevaPass.setError(null);
                        }

                        //Comprobar repetir contraseña
                        if(repetirPass.getText().toString().trim().equals(""))
                        {
                            confirmarPrimerRegistro=false;
                            tILRepetirPass.setError("Repetir la nueva contraseña es obligatorio");
                        }
                        else
                        {
                            tILRepetirPass.setError(null);
                        }

                        //Comprobar que la nueva contraseña coincide con repetir contraseña
                        if(!nuevaPass.getText().toString().trim().equals("") && !repetirPass.getText().toString().trim().equals(""))
                        {
                            if(!nuevaPass.getText().toString().trim().equals(repetirPass.getText().toString().trim()))
                            {
                                confirmarPrimerRegistro=false;
                                tILRepetirPass.setError("La contraseña nueva debe coincidir");
                            }
                            else
                            {
                                tILRepetirPass.setError(null);
                            }
                        }

                        //HACER EL AJAX PARA COMPROBAR LA CONTRASEÑA ACTUAL Y GUARDAR LA CONTRASEÑA NUEVA
                    }
                });

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();

                //Cancelar cambiar pass
                cancelarCambiarPass.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.cancel();
                    }
                });


            }
        });


        /*
        //CREAR ARRAY DE COCHES
        cochesUsuario(new VolleyCallback()
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
                            int idCoche = jsonArray.getJSONObject(i).getInt("idCoche");
                            String marca = jsonArray.getJSONObject(i).getString("marca");
                            String modelo = jsonArray.getJSONObject(i).getString("modelo");
                            String plazas = jsonArray.getJSONObject(i).getString("plazas");
                            String año = jsonArray.getJSONObject(i).getString("año");
                            String consumo = jsonArray.getJSONObject(i).getString("consumo");
                            int porDefecto = jsonArray.getJSONObject(i).getInt("porDefecto");

                            Coche coche = new Coche(idCoche,marca,modelo,plazas,año,consumo,porDefecto);
                            arrayCoches.add(coche);

                            //Nos quedamos con el coche por defecto para luego cuando le de a editar coches, se marque el coche por defecto
                            if(porDefecto==1)
                            {
                                posicionCochePorDefecto=i;
                            }
                        }
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        },Integer.parseInt(user.get(SessionManager.KEY_ID_USUARIO)));
        //FIN CREAR ARRAY DE COCHES
        */

        //EDITAR COCHES
        Button editarCoches = (Button)findViewById(R.id.BTEditarCoches);
        editarCoches.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {

                //Cada vez que pulsemos en editar, obtenemos los coches de la bd y lo asignamos a un array de coches
                //CREAR ARRAY DE COCHES
                cochesUsuario(new VolleyCallback()
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

                                //Borramos el array de coches
                                arrayCoches.clear();
                                for(int i = 0; i < jsonArray.length(); i++)
                                {
                                    int idCoche = jsonArray.getJSONObject(i).getInt("idCoche");
                                    String marca = jsonArray.getJSONObject(i).getString("marca");
                                    String modelo = jsonArray.getJSONObject(i).getString("modelo");
                                    String plazas = jsonArray.getJSONObject(i).getString("plazas");
                                    String año = jsonArray.getJSONObject(i).getString("año");
                                    String consumo = jsonArray.getJSONObject(i).getString("consumo");
                                    int porDefecto = jsonArray.getJSONObject(i).getInt("porDefecto");

                                    //Vamos añadiendo cada coche al array
                                    Coche coche = new Coche(idCoche,marca,modelo,plazas,año,consumo,porDefecto);
                                    arrayCoches.add(coche);

                                    //Nos quedamos con el coche por defecto para luego cuando le de a editar coches, se marque el coche por defecto
                                    if(porDefecto==1)
                                    {
                                        posicionCochePorDefecto=i;
                                    }
                                }

                                //Nos creamos un AlertDialog y a traves de el obtenemos los objetos que hay dentro
                                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditarPerfil.this);
                                View mView = getLayoutInflater().inflate(R.layout.editar_coches, null);

                                Button aceptarEditarCoche = mView.findViewById(R.id.aceptarEditarCoche);
                                Button cancelarEditarCoche = mView.findViewById(R.id.cancelarEditarCoche);

                                ImageButton borrarCoche = mView.findViewById(R.id.borrarCoche);

                                spCoches=mView.findViewById(R.id.spCoches);
                                spCoches.setOnItemSelectedListener(EditarPerfil.this);

                                spPlazas= mView.findViewById(R.id.spPlazas);
                                spAños= mView.findViewById(R.id.spAños);

                                etMarca=mView.findViewById(R.id.eTMarca);
                                etModelo=mView.findViewById(R.id.eTModelo);
                                eTConsumo=mView.findViewById(R.id.eTConsumo);

                                switchCochePorDefecto=mView.findViewById(R.id.switchCochePorDefecto);


                                //Recorremos la lista de coches del usuario
                                arrayNombreCoches.clear();
                                for(int i=0; i<arrayCoches.size();i++)
                                {
                                    arrayNombreCoches.add(arrayCoches.get(i).getMarca()+" "+arrayCoches.get(i).getModelo());
                                }

                                //Asignamos al spinner de coches los nombres
                                aACoches=new ArrayAdapter<String>(getApplicationContext(),R.layout.estilo_spinner, arrayNombreCoches);
                                aACoches.setDropDownViewResource(R.layout.estilo_items_spinner);
                                spCoches.setAdapter(aACoches);

                                spCoches.setSelection(posicionCochePorDefecto+1);

                                aAPlazas = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opcionesPlaza, R.layout.estilo_spinner);
                                aAPlazas.setDropDownViewResource(R.layout.estilo_items_spinner);
                                spPlazas.setAdapter(aAPlazas);

                                aAAños = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opcionesAño, R.layout.estilo_spinner);
                                aAAños.setDropDownViewResource(R.layout.estilo_items_spinner);
                                spAños.setAdapter(aAAños);

                                mBuilder.setView(mView);
                                final AlertDialog dialog = mBuilder.create();
                                dialog.show();

                                //Cancelar editar coche
                                cancelarEditarCoche.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        dialog.cancel();
                                    }
                                });

                                //Cancelar editar coche
                                aceptarEditarCoche.setOnClickListener(new View.OnClickListener()
                                {
                                    @Override
                                    public void onClick(View view)
                                    {
                                        dialog.cancel();
                                    }
                                });

                                borrarCoche.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View view) {

                                        //Añadimos el coche al array de coches a borrar
                                        arrayCochesBorrados.add(arrayCoches.get(spCoches.getSelectedItemPosition()-1));

                                        //Eliminamos el coche del array de coches
                                        arrayCoches.remove(spCoches.getSelectedItemPosition()-1);
                                        //Eliminamos el coche del array de NOMBRES de coches, que se utiliza para generar el select de coches
                                        arrayNombreCoches.remove(spCoches.getSelectedItemPosition()-1);

                                        //Reinicializamos el spinner de coches, con los coches restantes
                                        spCoches.setAdapter(aACoches);

                                        for(int i=0;i<arrayCoches.size();i++)
                                        {
                                            Log.d("Console nombre coche",arrayCoches.get(i).getMarca());
                                        }
                                    }
                                });
                            }
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                },Integer.parseInt(user.get(SessionManager.KEY_ID_USUARIO)));
                //FIN CREAR ARRAY DE COCHES

            }
        });
        //FIN EDITAR COCHES

        //AÑADIR COCHES
        final Button añadirCoche = (Button)findViewById(R.id.BTAñadirCoche);
        añadirCoche.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(EditarPerfil.this);
                View mView = getLayoutInflater().inflate(R.layout.add_coche, null);

                Button aceptarAñadirCoche = mView.findViewById(R.id.aceptarAñadirCoche);
                Button cancelarAñadirCoche = mView.findViewById(R.id.cancelarAñadirCoche);

                spPlazas= mView.findViewById(R.id.spPlazas);
                spAños= mView.findViewById(R.id.spAños);

                etMarca=mView.findViewById(R.id.eTMarca);
                etModelo=mView.findViewById(R.id.eTModelo);
                eTConsumo=mView.findViewById(R.id.eTConsumo);

                switchCochePorDefecto=mView.findViewById(R.id.switchCochePorDefecto);

                tILMarca=mView.findViewById(R.id.tILMarca);
                tILModelo=mView.findViewById(R.id.tILModelo);
                tILConsumo=mView.findViewById(R.id.tILConsumo);


                aAPlazas = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opcionesPlaza, R.layout.estilo_spinner);
                aAPlazas.setDropDownViewResource(R.layout.estilo_items_spinner);
                spPlazas.setAdapter(aAPlazas);

                aAAños = ArrayAdapter.createFromResource(getApplicationContext(), R.array.opcionesAño, R.layout.estilo_spinner);
                aAAños.setDropDownViewResource(R.layout.estilo_items_spinner);
                spAños.setAdapter(aAAños);

                mBuilder.setView(mView);
                final AlertDialog dialog = mBuilder.create();
                dialog.show();


                aceptarAñadirCoche.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        boolean confirmarAñadirCoche=true;

                        int porDefecto=0;

                        //Comprobación de la marca
                        if(etMarca.getText().toString().trim().equals(""))
                        {
                            confirmarAñadirCoche=false;
                            tILMarca.setError("La marca es obligatoria");
                        }
                        else
                        {
                            tILMarca.setError(null);
                        }

                        //Comprobación del modelo
                        if(etModelo.getText().toString().trim().equals(""))
                        {
                            confirmarAñadirCoche=false;
                            tILModelo.setError("El modelo es obligatoriao");
                        }
                        else
                        {
                            tILModelo.setError(null);
                        }

                        //Comprobación de las plazas
                        if(spPlazas.getSelectedItemPosition()==0)
                        {
                            confirmarAñadirCoche=false;
                            spPlazas.setError("Las plazas son obligatorias");
                        }
                        else
                        {
                            spPlazas.setError(null);
                        }


                        if(switchCochePorDefecto.isChecked())
                        {
                            porDefecto=1;
                        }

                        //Si no hay errores, añadimos el coche
                        if(confirmarAñadirCoche)
                        {
                            addCoche(new VolleyCallback()
                            {
                                @Override
                                public void onSuccess(String result)
                                {
                                    if(result.equals("insertado"))
                                    {
                                        Intent editarPerfil = new Intent(getApplicationContext(),EditarPerfil.class);
                                        startActivity(editarPerfil);
                                    }
                                    else
                                    {
                                        Toast.makeText(getApplicationContext(),"Ha ocurrido algún error, vuelva a intentarlo en unos minutos por favor",Toast.LENGTH_LONG).show();
                                    }

                                }
                            },Integer.parseInt(user.get(SessionManager.KEY_ID_USUARIO)) ,etMarca.getText().toString().trim(), etModelo.getText().toString().trim(), spPlazas.getSelectedItem().toString(), spAños.getSelectedItem().toString(), eTConsumo.getText().toString(), porDefecto);
                        }

                    }
                });

                //Cancelar cambiar pass
                cancelarAñadirCoche.setOnClickListener(new View.OnClickListener()
                {
                    @Override
                    public void onClick(View view)
                    {
                        dialog.cancel();
                    }
                });
            }
        });
        //FIN AÑADIR COCHE


        //Usar camara

        if(mayRequestStoragePermission())
            mSetImage.setEnabled(true);
        else
            mSetImage.setEnabled(false);


        mSetImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOptions();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l)
    {
        switch (adapterView.getId())
        {
            case R.id.spCoches:
            {

                Log.d("Console Item spinner",String.valueOf(i));
                Log.d("Console Marca",spCoches.getSelectedItem().toString());

                //Si ha seleccionado algun coche, cargamos los datos de ese coche
                if(i!=-1)
                {
                    //Primero ponemos por defecto todo "vacio"
                    etMarca.setText("");
                    etModelo.setText("");
                    eTConsumo.setText("");
                    spPlazas.setSelection(0);
                    spAños.setSelection(0);

                    //Rellenamos los datos del coche con el array de coches
                    String marca = arrayCoches.get(i).getMarca();
                    String modelo = arrayCoches.get(i).getModelo();
                    String consumo = String.valueOf(arrayCoches.get(i).getConsumo());
                    String plazas = arrayCoches.get(i).getPlazas();
                    String años = arrayCoches.get(i).getAño();
                    int cochePorDefecto= arrayCoches.get(i).getPorDefecto();



                    //Rellenamos los datos del coche en la vista
                    etMarca.setText(marca);
                    etModelo.setText(modelo);

                    if(consumo!=null && !consumo.equals("null"))
                    {
                        eTConsumo.setText(consumo);
                    }
                    else
                    {
                        eTConsumo.setText("");
                    }

                    if(años!=null && !años.equals("null"))
                    {
                        spAños.setSelection(arrayAños.indexOf(años)+1);       //Los años tienen que ser con +1
                    }
                    else
                    {
                        spAños.setSelection(0);
                    }

                    spPlazas.setSelection(arrayPlazas.indexOf(plazas)+1); //Las plazas tienen que ser con +1


                    if(cochePorDefecto==1)
                    {
                        switchCochePorDefecto.setChecked(true);
                    }
                    else
                    {
                        switchCochePorDefecto.setChecked(false);
                    }


                }
                else
                {
                    etMarca.setText("");
                    etModelo.setText("");
                    eTConsumo.setText("");
                    spPlazas.setSelection(0);
                    spAños.setSelection(0);
                    switchCochePorDefecto.setChecked(false);
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
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView)
    {

    }

    public void ciudadesBD(final VolleyCallback callback)
    {
        String URL = "https://www.rafaelpedrajas.com/android/listadoCiudades.php";

        final String response="";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL,
                new Response.Listener<String>()
                {
                    @Override
                    synchronized public void onResponse(String response)
                    {
                        Log.d("Console",response);
                        callback.onSuccess(response);
                        //Toast.makeText(CrearAnuncio.this,response,Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    synchronized public void onErrorResponse(VolleyError error)
                    {
                        Toast.makeText(EditarPerfil.this,error.toString(),Toast.LENGTH_LONG).show();
                    }
                });

        RequestQueue queue = Volley.newRequestQueue(EditarPerfil.this);
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
            synchronized public void onResponse(String response)
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
            synchronized protected Map<String, String> getParams()
            {
                Map<String, String>  params = new HashMap<String, String>();
                params.put("idProvincia", String.valueOf(idProvincia));

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void cochesUsuario(final VolleyCallback callback, final int idUsuario)
    {
        JSONArray jsonArray= new JSONArray();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/cochesUsuario.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            public void onResponse(String response)
            {
                Log.d("Console Coches",response);
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
                params.put("idUsuario", String.valueOf(idUsuario));

                return params;
            }
        };
        queue.add(stringRequest);
    }

    public void editarUsuario(final VolleyCallback callback, final String correo, final String nombre, final String apellido, final String telefono, final int idProvincia, final int indexUniversidad)
    {
        JSONArray jsonArray= new JSONArray();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/editarUsuario.php";

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>()
        {
            @Override
            synchronized public void onResponse(String response)
            {
                Log.d("Console Editar Usuario",response);
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
            synchronized protected Map<String, String> getParams()
            {
                Log.d("ID PROVINCIA",String.valueOf(idProvincia));
                Log.d("INDEX UNIVERSIDAD",String.valueOf(indexUniversidad));
                Map<String, String>  params = new HashMap<String, String>();
                params.put("correo", correo);
                params.put("nombre", nombre);
                params.put("apellido", apellido);
                params.put("telefono", telefono);
                params.put("idProvincia", String.valueOf(idProvincia));
                params.put("indexUniversidad", String.valueOf(indexUniversidad));

                return params;
            }
        };
        queue.add(stringRequest);
    }

    //Añadir Coche
    public void addCoche(final VolleyCallback callback, final int idUsuario, final String marca, final String modelo, final String plazas, final String año, final String consumo, final int porDefecto)
    {
        JSONArray jsonArray= new JSONArray();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/insertarCoche.php";

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
                //Datos Usuario
                params.put("idUsuario", String.valueOf(idUsuario));
                params.put("marca", marca);
                params.put("modelo", modelo);
                params.put("plazas", String.valueOf(plazas));
                params.put("año", String.valueOf(año));
                params.put("consumo", consumo);
                params.put("porDefecto", String.valueOf(porDefecto));

                return params;
            }
        };
        queue.add(stringRequest);

    }

    //Añadir Coche
    public void editarCoche(final VolleyCallback callback)
    {
        JSONArray jsonArray= new JSONArray();

        RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
        String URL = "https://www.rafaelpedrajas.com/android/editarCoche.php";

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
                String cochesEditados = new Gson().toJson(arrayCoches);
                String cochesBorrados = new Gson().toJson(arrayCochesBorrados);

                Map<String, String>  params = new HashMap<String, String>();
                //Datos Usuario
                params.put("cochesEditados", cochesEditados);
                params.put("cochesBorrados", cochesBorrados);

                return params;
            }
        };
        queue.add(stringRequest);

    }


    //----------Funciones para poder usar la camara----
    private boolean mayRequestStoragePermission() {

        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)
            return true;

        if((checkSelfPermission(WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) &&
                (checkSelfPermission(CAMERA) == PackageManager.PERMISSION_GRANTED))
            return true;

        if((shouldShowRequestPermissionRationale(WRITE_EXTERNAL_STORAGE)) || (shouldShowRequestPermissionRationale(CAMERA))){
            Snackbar.make(linearLayoutContenedorImagen, "Los permisos son necesarios para poder usar la aplicación",
                    Snackbar.LENGTH_INDEFINITE).setAction(android.R.string.ok, new View.OnClickListener() {
                @TargetApi(Build.VERSION_CODES.M)
                @Override
                public void onClick(View v) {
                    requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
                }
            });
        }else{
            requestPermissions(new String[]{WRITE_EXTERNAL_STORAGE, CAMERA}, MY_PERMISSIONS);
        }

        return false;
    }

    private void showOptions() {
        final CharSequence[] option = {"Tomar foto", "Elegir de galeria", "Cancelar"};
        final AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Eleige una opción");
        builder.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if(option[which] == "Tomar foto"){
                    openCamera();
                }else if(option[which] == "Elegir de galeria"){
                    Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Selecciona app de imagen"), SELECT_PICTURE);
                }else {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }

    private void openCamera() {
        File file = new File(Environment.getExternalStorageDirectory(), MEDIA_DIRECTORY);
        boolean isDirectoryCreated = file.exists();

        if(!isDirectoryCreated)
            isDirectoryCreated = file.mkdirs();

        if(isDirectoryCreated){
            Long timestamp = System.currentTimeMillis() / 1000;
            String imageName = timestamp.toString() + ".jpg";

            mPath = Environment.getExternalStorageDirectory() + File.separator + MEDIA_DIRECTORY
                    + File.separator + imageName;

            File newFile = new File(mPath);

            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(newFile));
            startActivityForResult(intent, PHOTO_CODE);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString("file_path", mPath);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPath = savedInstanceState.getString("file_path");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK){
            switch (requestCode){
                case PHOTO_CODE:
                    MediaScannerConnection.scanFile(this,
                            new String[]{mPath}, null,
                            new MediaScannerConnection.OnScanCompletedListener() {
                                @Override
                                public void onScanCompleted(String path, Uri uri) {
                                    Log.i("ExternalStorage", "Scanned " + path + ":");
                                    Log.i("ExternalStorage", "-> Uri = " + uri);
                                }
                            });


                    Bitmap bitmap = BitmapFactory.decodeFile(mPath);
                    mSetImage.setImageBitmap(bitmap);
                    break;
                case SELECT_PICTURE:
                    Uri path = data.getData();
                    mSetImage.setImageURI(path);
                    break;

            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if(requestCode == MY_PERMISSIONS){
            if(grantResults.length == 2 && grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED){
                Toast.makeText(getApplicationContext(), "Permisos aceptados", Toast.LENGTH_SHORT).show();
                mSetImage.setEnabled(true);
            }
        }else{
            showExplanation();
        }
    }

    private void showExplanation() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
        builder.setTitle("Permisos denegados");
        builder.setMessage("Para usar las funciones de la app necesitas aceptar los permisos");
        builder.setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent();
                intent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                Uri uri = Uri.fromParts("package", getPackageName(), null);
                intent.setData(uri);
                startActivity(intent);
            }
        });
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        builder.show();
    }

    private void volverAtras(){
        Intent volver = new Intent(getApplicationContext(),Perfil.class);
        startActivity(volver);
    }
    @Override
    public void onBackPressed (){
        volverAtras();
    }
}