/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
class Blog extends JSONObjectWrapper {

	//------------- Constructors -------------------------------
	public Blog( JSONObject json ) {
		
		super( json );
	}
	
	//------------- Methods ------------------------------------
	public String getTitle() throws JSONException {
		
		return this.json.getString( "title" );
	}
	
	public int getPosts() throws JSONException {
		
		return this.json.getInt( "posts" );
	}
	
	public String getName() throws JSONException {
		
		return this.json.getString( "name" );
	}
	
	public String getUrl() throws JSONException {
		
		return this.json.getString( "url" );
	}
	
	public long getUpdated() throws JSONException {
		
		return this.json.getLong( "updated" );
	}
	
	public String getDescription() throws JSONException {
		
		return this.json.getString( "description" );
	}
	
	public boolean isAsk() throws JSONException {
		
		return this.json.getBoolean( "ask" );
	}

}
