/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import info.zak_raw.service_aggregation.TagBuilder;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
class PhotoPost extends Post {

	//------------- Fields -------------------------------------
	private List<Photo> photos;
	
	//------------- Constructors -------------------------------
	public PhotoPost( JSONObject json ) {
		
		super( json );
	}

	//------------- Methods ------------------------------------
	@Override
	public void buildTag( TagBuilder builder ) throws JSONException {
		// TODO Auto-generated method stub
		
		for ( Photo photo : this.getPhotos() ) {
			this.buildDate( builder );
			this.buildImage( photo, builder );
		}
	}
	
	private void buildDate( TagBuilder builder ) throws JSONException {
		 
		builder.startTagWithClass( "p", "date" );
		builder.putAnchor( this.getPostUrl(), this.getDate() );
		builder.endTag( "p" );
	}
	
	private void buildImage( Photo photo, TagBuilder builder ) throws JSONException {
		
		PhotoSize size = photo.getAltSizeByWidth( 400 );
		
		builder.startTagWithClass( "p", "image" );
		
		builder.startAnchor( this.getPostUrl() );
		builder.putImg( size.getUrl(), this.getSlug() );
		builder.endTag( "a" );
		
		builder.endTag( "p" );
	}
	
	public void buildCaption( TagBuilder builder ) throws JSONException {
		
		builder.startTagWithClass( "p", "caption" );
		builder.putAnchor( this.getSourceUrl(), this.getCaption() );
		builder.endTag( "p" );
	}
	
	public String getCaption() throws JSONException {
		
		return this.json.getString( "caption" );
	}
	
	public List<Photo> getPhotos() throws JSONException {
		
		if ( this.photos == null ) {
			JSONArray array = this.json.getJSONArray( "photos" );
			int length = array.length();
			this.photos = new ArrayList<Photo>( length );
			
			for ( int i = 0; i < length; i++ ) {
				JSONObject object = array.getJSONObject( i );
				this.photos.add( new Photo( object ) );
			}
			
			this.photos = Collections.unmodifiableList( this.photos );
		}
		
		return this.photos;
	}

}
