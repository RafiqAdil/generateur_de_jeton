package edu.adil.liguelorraine.utils;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.codehaus.jackson.map.ObjectMapper;

import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import edu.adil.liguelorraine.MainActivity;

public class WebServiceRequests {
	
	private TextView info_zone = null ;
	private EditText username  = null ;
	private EditText password  = null ;
	private Button   co        = null ;
	private Button   jeton     = null ;
	
	final public static String URL_JETON   = "http://172.20.10.2/LigueLorraine/ws-setjeton" ;
	final public static String URL_ACCOUNT = "http://172.20.10.2/LigueLorraine/ws-getaccount" ;
	
	public WebServiceRequests( TextView infos , EditText username , EditText password , Button co , Button jeton ) {
		this.info_zone = infos ;
		this.username  = username ;
		this.password  = password ;
		this.co        = co       ;
		this.jeton     = jeton    ;
	} ;
	
	public void authProcess(){
    	if( MainActivity.user.getAccount_id() == -1 )
    		new AuthReq( this.info_zone , this.co , this.jeton , this.username , this.password ).execute( this.username.getText().toString() , this.password.getText().toString() ) ;
    	else{
    		this.co.setText("Authentification");
    		MainActivity.user.setAccount_id(-1);

	    	this.username.setText("");
	    	this.password.setText("");
	    	this.info_zone.setText("");
	    	this.username.setVisibility(View.VISIBLE);
	    	this.password.setVisibility(View.VISIBLE);
	    	this.jeton.setVisibility(View.INVISIBLE);
    	}
	} ;
	
	public void setJeton( String username , String password , String jeton ){
		new JetonReq( this.info_zone ).execute( username , password , jeton ) ;
	} ;

	private class AuthReq extends AsyncTask<String, Void, User>{
		
		private TextView infos_zone = null ;
		private Button   co        = null ;
		private Button   jeton     = null ;
		private EditText username  = null ;
		private EditText password  = null ;
		
		public AuthReq( TextView tv , Button co , Button jeton , EditText username , EditText password ){
			this.infos_zone = tv ;
			this.co         = co       ;
			this.jeton      = jeton    ;
			this.username  = username ;
			this.password  = password ;
		} ;
		
	    @Override
	    protected void onPreExecute(){
	    	this.infos_zone.setText("Connexion en cours...."); 
	    } ;
	    
	    @Override
	    protected void onPostExecute(User u) {
	    	if( u == null || u.getStatus() != 1 ){
	    		this.infos_zone.setText("Erreur de connexion !");
	    		return ;
	    	}
	    	
	    	this.infos_zone.setText("Bienvenue " + u.getAccount_lastname() + " " + u.getAccount_firstname() ); 

	    	MainActivity.user.setAccount_id(u.getAccount_id());
	    	MainActivity.user.setAccount_firstname(u.getAccount_firstname());
	    	MainActivity.user.setAccount_lastname(u.getAccount_lastname());
	    	
	    	this.co.setText("Deconnexion"); 
	    	this.username.setVisibility(View.INVISIBLE);
	    	this.password.setVisibility(View.INVISIBLE);
	    	this.jeton.setVisibility(View.VISIBLE);
	    } ;
		
		@Override
		protected User doInBackground(String... params) {
			
			String user  = params[0] ;
			String pass  = params[1] ;
			
			User userObject = null ;
			
			try {
				StringBuilder response = new StringBuilder() ;
					
				HttpGet get = new HttpGet() ;
				get.setURI( new URI( WebServiceRequests.URL_ACCOUNT + "?user=" + user + "&sha1=" + MainActivity.sha1( pass ) + "&md5=" + MainActivity.md5( pass ) ) ) ;
				System.out.println(get.getURI().toString());
				DefaultHttpClient httpClient = new DefaultHttpClient() ;
					
				HttpResponse httpResponse = httpClient.execute(get) ;
					
				if( httpResponse.getStatusLine().getStatusCode() == 200 ){
					HttpEntity messageEntity = httpResponse.getEntity() ;
					InputStream is = messageEntity.getContent() ;
					BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;
					String line ;
					while( ( line = br.readLine() ) != null )
						response.append(line) ;
					
					System.out.println("la réponse : " + response.toString());
					
					ObjectMapper mapper = new ObjectMapper();
					userObject = mapper.readValue( response.toString() , User.class);
					
					if( userObject.getStatus() != 1 )
						return null ;
					return userObject ;
				} else {
				System.out.println("erreur");
					return null ;
				}
			} catch( Exception e ) {
				e.printStackTrace();
				return null ;
			}
		} ;
	} ;

	// Classe de gestion des requêtes concernant les jetons et le webservice
	private class JetonReq extends AsyncTask<String, Void, Integer>{
		
		private TextView infos_zone = null ;
		private String   jeton      = null ;
		
		public JetonReq( TextView tv ){
			this.infos_zone = tv ;
		} ;
		
	    @Override
	    protected void onPreExecute(){
	    	this.infos_zone.setText( "Jeton en cours de génération..." ) ;
	    } ;
	    
	    @Override
	    protected void onPostExecute(Integer result) {
	    	if( result == -1 ){
	    		this.infos_zone.setText("Merci de bien vouloir vous connecté avant !");
	    	} else if( result == 1 )
	    		this.infos_zone.setText( "Votre jeton de connexion est : " + this.jeton );
	    	else 
	    		this.infos_zone.setText( "Une erreur est survenue !" ) ;
	    } ;
		
		@Override
		protected Integer doInBackground(String... params) {
			
			if( params.length < 3 )
				return 0 ;
			
			if( MainActivity.user.getAccount_id() < 0 )
				return -1 ;
			
			String id    = params[0] ;
			String mdp   = params[1] ;
			this.jeton   = params[2] ;
			
			Boolean stat = true ;
			
			try {
				StringBuilder response = new StringBuilder() ;
				
				HttpGet get = new HttpGet() ;
				get.setURI( new URI( WebServiceRequests.URL_JETON + "?id=" + id + "&sha1=" + MainActivity.sha1( mdp ) + "&md5=" + MainActivity.md5( mdp ) + "&jeton=" + this.jeton ) ) ;
				System.out.println(get.getURI().toString());
				DefaultHttpClient httpClient = new DefaultHttpClient() ;
				
				HttpResponse httpResponse = httpClient.execute(get) ;
				
				if( httpResponse.getStatusLine().getStatusCode() == 200 ){
					Log.d( "[GET REQUEST]" , "HTTP Get succeeded" );
					
					HttpEntity messageEntity = httpResponse.getEntity() ;
					InputStream is = messageEntity.getContent() ;
					BufferedReader br = new BufferedReader(new InputStreamReader(is)) ;
					String line ;
					while( ( line = br.readLine() ) != null )
						response.append(line) ;
					
					System.out.println(response.toString());
				}
				
				
			} catch( Exception e ) {
				e.printStackTrace();
				return 0 ;
			}
			
			return ( stat ) ? 1 : 0 ;
		} ;
	} ;
} ;
