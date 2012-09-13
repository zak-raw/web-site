/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import info.zak_raw.service_aggregation.util.markup.Element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
abstract class Post extends JSONObjectWrapper {

	//------------- Statics ------------------------------------
	public static Post from( JSONObject object ) throws JSONException {
		
		// text, quote, link, answer, video, audio, photo
		String type = object.getString( "type" );
		
		if ( "photo".equals( type ) ) return new PhotoPost( object );
		
		return null;
	}
	
	//------------- Constructors -------------------------------
	public Post( JSONObject json ) {
		
		super( json );
	}
	
	//------------- Methods ------------------------------------
	public abstract Collection<Element> createElements() throws JSONException;
	
	/**
	 * @return The short name used to uniquely identify a blog
	 */
	public String getBlogName() throws JSONException {
		
		return this.json.getString( "blog_name" );
	}
	
	/**
	 * @return The post's unique ID
	 */
	public String getId() throws JSONException {
		
		return this.json.getString( "id" );
	}
	
	/**
	 * @return The location of the post
	 */
	public String getPostUrl() throws JSONException {
		
		return this.json.getString( "post_url" );
	}
	
	public String getSlug() throws JSONException {
		
		return this.json.getString( "slug" );
	}
	
	/**
	 * @return The type of post
	 */
	public String getType() throws JSONException {
		
		return this.json.getString( "type" );
	}
	
	/**
	 * @return The time of the post, in seconds since the epoch
	 */
	public long getTimestamp() throws JSONException {
		
		return this.json.getLong( "timestamp" );
	}
	
	/**
	 * @return The GMT date and time of the post, as a string
	 */
	public String getDate() throws JSONException {
		
		return this.json.getString( "date" );
	}
	
	/**
	 * @return The post format: html or markdown
	 */
	public String getFormat() throws JSONException {
		
		return this.json.getString( "format" );
	}
	
	/**
	 * @return The key used to reblog this post
	 */
	public String getReblogKey() throws JSONException {
		
		return this.json.getString( "reblog_key" );
	}
	
	/**
	 * @return Tags applied to the post
	 */
	public List<String> getTags() throws JSONException {
		
		JSONArray array = this.json.getJSONArray( "tags" );
		int length = array.length();
		List<String> tags = new ArrayList<String>( length );
		
		for ( int i = 0; i < length; i++ ) {
			tags.add( array.getString( i ) );
		}
		
		return tags;
	}
	
	/**
	 * @return Indicates whether the post was created via the Tumblr bookmarklet
	 */
	public boolean isBookmarklet() throws JSONException {
		
		return this.json.getBoolean( "bookmarklet" );
	}
	
	/**
	 * @return Indicates whether the post was created via mobile/email publishing
	 */
	public boolean isMobile() throws JSONException {
		
		return this.json.getBoolean( "mobile" );
	}
	
	/**
	 * @return The URL for the source of the content (for quotes, reblogs, etc.)
	 */
	public String getSourceUrl() throws JSONException {
		
		return this.json.getString( "source_url" );
	}
	
	/**
	 * @return The title of the source site
	 */
	public String getSourceTitle() throws JSONException {
		
		return this.json.getString( "source_title" );
	}
	
	/**
	 * @return Indicates if a user has already liked a post or not
	 */
	public boolean isLiked() throws JSONException {
		
		return this.json.getBoolean( "liked" );
	}
	
	/**
	 * @return Indicates the current state of the post (published, queued, draft, private)
	 */
	public String getState() throws JSONException {
		
		return this.json.getString( "state" );
	}
	
}
