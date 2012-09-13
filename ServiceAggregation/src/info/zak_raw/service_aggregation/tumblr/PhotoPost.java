/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import info.zak_raw.service_aggregation.util.markup.Element;
import info.zak_raw.service_aggregation.util.markup.Tag;
import info.zak_raw.service_aggregation.util.markup.Text;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

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
	public Collection<Element> createElements() throws JSONException {
		
		Collection<Element> elements = new ArrayList<Element>( this.getPhotos().size() * 3 );
		for ( Photo photo : this.getPhotos() ) {
			elements.add( this.createDate() );
			elements.add( this.createImage( photo ) );
			elements.add( this.createCaption() );
		}
		
		return elements;
	}
	
	private Element createDate() throws JSONException {
		
		String href = this.getPostUrl();
		Text text = new Text( this.getDate() );
		
		Tag p = Tag.withClass( "p", "date" );
		p.add( Tag.anchor( href, text ) );
		
		return p;
	}
	
	private Element createImage( Photo photo ) throws JSONException {
		
		String href = this.getPostUrl();
		String src = photo.getAltSizeByWidth( 400 ).getUrl();
		String alt = this.getSlug();
		
		Tag p = Tag.withClass( "p", "image" );
		p.add( Tag.anchor( href, Tag.img( src, alt ) ) );
		
		return p;
	}
	
	private Element createCaption() throws JSONException {
		
		String href = this.getSourceUrl();
		Text caption = new Text( this.getCaption() );
		
		Tag p = Tag.withClass( "p", "caption" );
		p.add( Tag.anchor( href, caption ) );
		
		return p;
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
