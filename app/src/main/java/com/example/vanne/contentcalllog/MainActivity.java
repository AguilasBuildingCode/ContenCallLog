package com.example.vanne.contentcalllog;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //	Columnas	a	consultar
//	Establecer	URI	para	acceder	a	los	contactos
//Creamos	un	 objeto	 de	 tipo	 Uri	 con	 el	 content	 provider	 que	 vamos	 a	 utilizaren	este	caso	para	obtener	información	a	cerca	de	los	contactos
        Uri	uriContactos	=	Uri.parse("content://com.android.contacts/contacts/");
//Uri	 uriContactos	 =	 ContactsContract.Contacts.CONTENT_URI;//Para	 obtener	 elnombre	del	contacto
        Uri uriTelefonos	 =	 ContactsContract.CommonDataKinds.Phone.CONTENT_URI;//Paraobtener	el	teléfono	del	contacto
//Array	especificando	las	columnas	que	queremos	obtener	de	Contactos	(Uno	paracada	uri	definida)
        String[]	idNombre	=	new	String[]	{
                ContactsContract.Contacts._ID,//Id	contacto
                ContactsContract.Contacts.DISPLAY_NAME//Nombre	del	contacto
        };
        String[]	numeroTelefono	=	new	String[]{
                ContactsContract.CommonDataKinds.Phone.NUMBER//Teléfono	del	contacto
        };
//Consulta	para	este	registro
//managedQuery	es	un	método	de	la	clase	Activity
        Cursor cursorIdNombre	=	managedQuery(	uriContactos,
                idNombre,	//Nombre	de	la	columna	que	va	a	devolver,	en	este	caso	hemos	creadoanteriormente	el	array	de	nombre	de	columnas.
        null,	//	Cláusula	where
                null,	//	Argumentos	de	selección
                null);	//	Orden	del	conenido.
        Cursor	cursorNumeroTelefono	=	managedQuery(uriTelefonos, numeroTelefono,
                null,
                null,
                null
        );
        Toast.makeText(this,	 "valor	 de	 cursor"+cursorIdNombre.getCount(),
                Toast.LENGTH_SHORT).show();
//Si	el	cursor	no	contiene	no	contiene	nigún	registro,	no	entra	en	el	if
        if	(	cursorIdNombre.getCount()	!=	0)
        {
//Colocamos	a	ambos	cursores	en	el	primer	registro
            cursorIdNombre.moveToFirst();
            cursorNumeroTelefono.moveToFirst();
//Vamos	a	obtener	el	numero	de	la	columna	con	nombre	DISPLAY_NAME(contiene	elnombre	de	los	contactos)	para	después	poder	acceder	a	ella
            int	 numeroColumnaNombre	 =
                    cursorIdNombre.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
//Ahora	 obtenemos	 el	 primer	 registro	 de	 la	 columna	 que	 será	 el	 nombre	 delprimer	contacto	de	nuestra	lista	de	contactos
            String	nombreContacto	=	cursorIdNombre.getString(numeroColumnaNombre);
//Hacemos	lo	mismo	con	el	segundo	cursor
            int	numeroColumnaTelefono	=	cursorNumeroTelefono.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
            String numeroContacto	=	cursorNumeroTelefono.getString(numeroColumnaTelefono);

            StringBuilder salida = null;
            salida.append("\n"+nombreContacto	+"-----"+numeroContacto+	"\n");
//Ahora	recorremos	el	cursor
            while(cursorIdNombre.moveToNext())
            {
                cursorNumeroTelefono.moveToNext();
                nombreContacto	=	cursorIdNombre.getString(numeroColumnaNombre);
                numeroContacto	=	cursorNumeroTelefono.getString(numeroColumnaTelefono);
                salida.append(nombreContacto	+"-----"+numeroContacto+	"\n");
            }
        }
    }
}
