/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import info.zak_raw.service_aggregation.TagBuilder;
import info.zak_raw.service_aggregation.ViewCache;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.t2framework.t2.annotation.core.Default;
import org.t2framework.t2.annotation.core.Page;
import org.t2framework.t2.navigation.SimpleText;
import org.t2framework.t2.spi.Navigation;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
@Page( "/update/tumblr" )
public class Update {
	
	//------------- Fields -------------------------------------
	private TagBuilder builder;
	
	//------------- Constructors -------------------------------
	public Update() {
		
		this.builder = new TagBuilder( 300 * 20 );
	}
	
	//------------- Methods ------------------------------------
	@Default
	public Navigation update() {
		
		String apiKey = this.loadApiKey();
		
		if ( apiKey.isEmpty() ) return SimpleText.out( "Tumblr API Key is empty." );
		
		try {
			ViewCache cache = new ViewCache( "tumblr" );
			
			String htmlFragment = this.createHtmlFragment( apiKey );
			if ( ! htmlFragment.isEmpty() ) {
				cache.updateContent( htmlFragment );
				
				return SimpleText.out( "updated" );
			}
			else {
				return SimpleText.out( "not updated" );
			}
		}
		catch ( IOException e ) {
			e.printStackTrace();
			return SimpleText.out( e.getMessage() );
		}
		catch ( JSONException e ) {
			e.printStackTrace();
			return SimpleText.out( e.getMessage() );
		}
	}
	
	private String createHtmlFragment( String apiKey ) throws IOException, JSONException {
		
		URL url = new URL(
				"http://api.tumblr.com/v2/blog/zak-raw.tumblr.com/posts?api_key=" + apiKey );
		URLConnection connection = url.openConnection();
		
		String body = fetchBody( connection );
		JSONObject json = new JSONObject( body );
		int status = json.getJSONObject( "meta" ).getInt( "status" );
		
		if ( status != 200 ) return "";
		
		PostsResponse response = new PostsResponse( json.getJSONObject( "response" ) );
		
		return this.build( response );
	}
	
	private String build( PostsResponse response ) throws JSONException {
		
		this.builder.startTag( "ul" );
		
		for ( Post post : response.getPosts() ) {
			this.builder.startTag( "li" );
			post.buildTag( this.builder );
			this.builder.endTag( "li" );
		}
		
		this.builder.endTag( "ul" );
		
		String text = this.builder.toString();
		this.builder.clear();
		
		return text;
	}
	
	private static String fetchBody( URLConnection connection ) throws IOException {
		
		// TODO getContentLength() が -1 返すうんこ
		
		StringWriter writer = new StringWriter( 26000 );
		Reader reader = null;
		try {
			reader = new InputStreamReader( connection.getInputStream() );
			
			char[] buffer = new char[1024 * 8];
			int readSize = reader.read( buffer );
			while ( -1 < readSize ) {
				writer.write( buffer, 0, readSize );
				readSize = reader.read( buffer );
			}
		}
		finally {
			close( reader );
		}
		
		return writer.toString();
	}
	
	private String loadApiKey() {
		
		InputStream input = this.getClass().getResourceAsStream( "/tumblr.properties" );
		
		if ( input == null ) return "";
		
		Properties props = new Properties();
		try {
			props.load( input );
			String key = props.getProperty( "oauth.consumerKey" );
			
			return key == null ? "" : key;
		}
		catch ( IOException e ) {
			e.printStackTrace();
			return "";
		}
		finally {
			close( input );
		}
	}
	
	private static void close( Closeable closeable ) {
		
		if ( closeable == null ) return;
		
		try {
			closeable.close();
		}
		catch ( IOException e ) {
		}
	}
	
}
