package com.rafaelpedrajas.ucar.Sesion;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.rafaelpedrajas.ucar.Clases.Usuario;
import com.rafaelpedrajas.ucar.GUI.Login;

import java.util.HashMap;

/**
 * Created by Elena on 23/08/2017.
 */

public class SessionManager
{
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Sharedpref file name
    private static final String PREF_NAME = "AndroidHivePref";

    // All Shared Preferences Keys
    private static final String IS_LOGIN = "IsLoggedIn";

    // User name (make variable public to access from outside)
    public static final String KEY_CORREO = "correo";

    // Email address (make variable public to access from outside)
    public static final String KEY_USUARIO= "usuario";
    public static final String KEY_NOMBRE = "nombre";
    public static final String KEY_APELLIDO = "apellido";
    public static final String KEY_FOTO = "foto";
    public static final String KEY_TELEFONO = "telefono";
    public static final String KEY_NOMBRE_UNIVERSIDAD = "nombreUniversidad";
    public static final String KEY_NOMBRE_PROVINCIA = "nombreProvincia";
    public static final String KEY_MARCA_COCHE = "marcaCoche";
    public static final String KEY_ID_UNIVERSIDAD = "idUniversidad";
    public static final String KEY_ID_PROVINCIA = "idPRovincia";
    public static final String KEY_ID_COCHE = "idCoche";
    public static final String KEY_PLAZAS_COCHE = "plazasCoche";
    public static final String KEY_MODELO_COCHE = "modeloCoche";
    public static final String KEY_VALORACION_PUNTUALIDAD = "valoracionPuntualidad";
    public static final String KEY_VALORACION_AMABILIDAD = "valoracionAmabilidad";
    public static final String KEY_VALORACION_CONDUCCION = "valoracionConduccion";


    // Constructor
    public SessionManager(Context context){
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    /**
     * Create login session
     * */
    public void createLoginSession(String correo, String nombre, String apellido, String foto, String telefono, String nombreUniversidad, String nombreProvincia, String marcaCoche, String modeloCoche, int idUniversidad, int idProvincia, int idCoche, int plazasCoche, float valoracionPuntualidad, float valoracionAmabilidad, float valoracionConduccion){
        // Storing login value as TRUE
        editor.putBoolean(IS_LOGIN, true);

        // Storing name in pref
        editor.putString(KEY_CORREO, correo);

        // Storing email in pref
        editor.putString(KEY_NOMBRE, nombre);
        editor.putString(KEY_APELLIDO, apellido);
        editor.putString(KEY_FOTO, foto);
        editor.putString(KEY_TELEFONO, telefono);
        editor.putString(KEY_NOMBRE_UNIVERSIDAD, nombreUniversidad);
        editor.putString(KEY_NOMBRE_PROVINCIA, nombreProvincia);
        editor.putString(KEY_MARCA_COCHE, marcaCoche);
        editor.putString(KEY_MODELO_COCHE, modeloCoche);
        editor.putInt(KEY_ID_UNIVERSIDAD, idUniversidad);
        editor.putInt(KEY_ID_PROVINCIA, idProvincia);
        editor.putInt(KEY_ID_COCHE, idCoche);
        editor.putInt(KEY_PLAZAS_COCHE, plazasCoche);
        editor.putFloat(KEY_VALORACION_PUNTUALIDAD, valoracionPuntualidad);
        editor.putFloat(KEY_VALORACION_AMABILIDAD, valoracionAmabilidad);
        editor.putFloat(KEY_VALORACION_CONDUCCION, valoracionConduccion);


        // commit changes
        editor.commit();
    }

    /**
     * Check login method wil check user login status
     * If false it will redirect user to login page
     * Else won't do anything
     * */
    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, Login.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }



    /**
     * Get stored session data
     * */
    public HashMap<String, Usuario> getUserDetails(){
        HashMap<String, Usuario> user = new HashMap<String, Usuario>();

        Usuario usuario = new Usuario();
        usuario.setCorreo(pref.getString(KEY_CORREO, null));
        usuario.setNombre(pref.getString(KEY_NOMBRE, null));
        usuario.setApellido(pref.getString(KEY_APELLIDO, null));
        usuario.setFoto(pref.getString(KEY_FOTO, null));
        usuario.setTelefono(pref.getString(KEY_TELEFONO, null));
        usuario.setNombreUniversidad(pref.getString(KEY_NOMBRE_UNIVERSIDAD, null));
        usuario.setNombreProvincia(pref.getString(KEY_NOMBRE_PROVINCIA, null));
        usuario.setMarcaCoche(pref.getString(KEY_MARCA_COCHE, null));
        usuario.setModeloCoche(pref.getString(KEY_MODELO_COCHE, null));
        usuario.setIdUniversidad(pref.getInt(KEY_ID_UNIVERSIDAD, 0));
        usuario.setIdProvincia(pref.getInt(KEY_ID_PROVINCIA, 0));
        usuario.setIdCoche(pref.getInt(KEY_ID_COCHE, 0));
        usuario.setPlazas(pref.getInt(KEY_PLAZAS_COCHE, 0));
        usuario.setValoracionPuntualidad(pref.getFloat(KEY_VALORACION_PUNTUALIDAD, 0));
        usuario.setValoracionAmabilidad(pref.getFloat(KEY_VALORACION_AMABILIDAD, 0));
        usuario.setValoracionConduccion(pref.getFloat(KEY_VALORACION_CONDUCCION, 0));

        user.put(KEY_USUARIO,usuario);

        // user name
        /*
        user.put(KEY_CORREO, pref.getString(KEY_CORREO, null));

        // user email id
        user.put(KEY_NOMBRE, pref.getString(KEY_NOMBRE, null));
        user.put(KEY_APELLIDO, pref.getString(KEY_APELLIDO, null));
        user.put(KEY_FOTO, pref.getString(KEY_FOTO, null));
        user.put(KEY_TELEFONO, pref.getString(KEY_TELEFONO, null));
        user.put(KEY_VALORACION_PUNTUALIDAD, pref.getInt(KEY_VALORACION_PUNTUALIDAD, 0));
        */


        // return user
        return user;
    }

    /**
     * Clear session details
     * */
    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(_context, Login.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);
    }

    /**
     * Quick check for login
     * **/
    // Get Login State
    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }
}
