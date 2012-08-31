/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
public class Photo extends JSONObjectWrapper {

	//------------- Fields -------------------------------------
	private List<PhotoSize> sizes;
	
	//------------- Constructors -------------------------------
	public Photo( JSONObject json ) {
		
		super( json );
	}
	
	//------------- Methods ------------------------------------
	/**
	 * @return user supplied caption for the individual photo (Photosets only)
	 */
	public String getCaption() throws JSONException {
		
		return this.json.getString( "caption" );
	}

	/**
	 * @return alternate photo sizes, each with: 
	 */
	public List<PhotoSize> getAltSizes() throws JSONException {
		
		if ( this.sizes == null ) {
			JSONArray array = this.json.getJSONArray( "alt_sizes" );
			int length = array.length();
			this.sizes = new ArrayList<PhotoSize>( length );
			
			for ( int i = 0; i < length; i++ ) {
				JSONObject object = array.getJSONObject( i );
				this.sizes.add( new PhotoSize( object ) );
			}
			
			this.sizes = Collections.unmodifiableList( this.sizes );
		}
		
		return this.sizes;
	}
	
	public PhotoSize getAltSizeByWidth( int targetWidth ) throws JSONException {
		
		int minDiff = Integer.MAX_VALUE;
		PhotoSize nearestSize = null;
		for ( PhotoSize size : this.getAltSizes() ) {
			if ( size.getWidth() == targetWidth ) return size;
			
			if ( nearestSize == null ) {
				nearestSize = size;
			}
			else {
				int diff = Math.abs( nearestSize.getWidth() - size.getWidth() );
				if ( diff < minDiff ) {
					nearestSize = size;
					minDiff = diff;
				}
			}
		}
		
		return nearestSize;
	}
	
	/**
	 * @return original photo size
	 */
	public PhotoSize getOriginalSize() throws JSONException {
		
		return new PhotoSize( this.json.getJSONObject( "original_size" ) );
	}
	
}
