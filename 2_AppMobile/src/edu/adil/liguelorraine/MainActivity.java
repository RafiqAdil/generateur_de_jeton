package edu.adil.liguelorraine;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.adil.liguelorraine.utils.User;
import edu.adil.liguelorraine.utils.WebServiceRequests;

public class MainActivity extends Activity {
	
	final public static User user = new User() ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) { // est appeler a la création de l'activity
		super.onCreate(savedInstanceState); // appel de la fonction mére
		setContentView(R.layout.activity_main); // utilisation de la vue correspondance
	
		final Button auth  = (Button) findViewById(R.id.auth_btn)  , // bouton de connextion
			         jeton = (Button) findViewById(R.id.jeton_btn) ; // bouton pour générer un jetton
		
		jeton.setVisibility(View.INVISIBLE); // On masque le bouton du jeton
		
		final TextView tv = (TextView) findViewById(R.id.textView1) ; // zonne d'affichage  du jetton
		
		final EditText username = (EditText) findViewById(R.id.username_field) , // champs de l'identfiant
				       password = (EditText) findViewById(R.id.password_field) ; // champs du mot de passe
		
		final WebServiceRequests ws = new WebServiceRequests( tv , username , password , auth , jeton ) ; // on instancie la classe des webservice

		auth.setOnClickListener(new OnClickListener() { // on associe le clique du bouton... 
			@Override
			public void onClick(View v) {
				ws.authProcess() ; // ... au processus d'authentification du webservice
			} ;
		});
		
		jeton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				ws.setJeton(
						String.valueOf(MainActivity.user.getAccount_id()) ,
						password.getText().toString() ,
						MainActivity.md5(
								( new Random().nextInt( 1000 ) + 1 ) +
								username.getText().toString() +
								password.getText().toString() +
								( new Random().nextInt( 1000 ) + 1 )
						).substring( 0 , 10 )
				) ;
			} ;
		});
	} ;
	
	public static final String md5(final String s) {
	    final String MD5 = "MD5";
	    try {
	        // Create MD5 Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuilder hexString = new StringBuilder();
	        for (byte aMessageDigest : messageDigest) {
	            String h = Integer.toHexString(0xFF & aMessageDigest);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
	
	public static final String sha1(final String s) {
	    final String MD5 = "SHA";
	    try {
	        // Create SHA Hash
	        MessageDigest digest = java.security.MessageDigest.getInstance(MD5);
	        digest.update(s.getBytes());
	        byte messageDigest[] = digest.digest();

	        // Create Hex String
	        StringBuilder hexString = new StringBuilder();
	        for (byte aMessageDigest : messageDigest) {
	            String h = Integer.toHexString(0xFF & aMessageDigest);
	            while (h.length() < 2)
	                h = "0" + h;
	            hexString.append(h);
	        }
	        return hexString.toString();

	    } catch (NoSuchAlgorithmException e) {
	        e.printStackTrace();
	    }
	    return "";
	}
} ;
