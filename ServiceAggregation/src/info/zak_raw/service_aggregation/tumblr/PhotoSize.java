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
class PhotoSize extends JSONObjectWrapper {

	//------------- Constructors -------------------------------
	public PhotoSize( JSONObject json ) {
		
		super( json );
	}
	
	//------------- Methods ------------------------------------
	/**
	 * @return width of the photo, in pixels
	 */
	public int getWidth() throws JSONException {
		
		return this.json.getInt( "width" );
	}
	
	/**
	 * @return  height of the photo, in pixels
	 */
	public int getHeight() throws JSONException {
		
		return this.json.getInt( "height" );
	}
	
	/**
	 * @return Location of the photo file (either a JPG, GIF, or PNG)
	 */
	public String getUrl() throws JSONException {
		
		return this.json.getString( "url" );
	}

}
