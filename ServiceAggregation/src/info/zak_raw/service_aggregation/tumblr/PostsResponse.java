/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import java.util.ArrayList;
import java.util.List;

import com.google.appengine.labs.repackaged.org.json.JSONArray;
import com.google.appengine.labs.repackaged.org.json.JSONException;
import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
class PostsResponse extends JSONObjectWrapper {

	//------------- Constructors -------------------------------
	public PostsResponse( JSONObject json ) {
		
		super( json );
	}
	
	//------------- Methods ------------------------------------
	public Blog getBlog() throws JSONException {
		
		return new Blog( this.json.getJSONObject( "blog" ) );
	}

	public List<Post> getPosts() throws JSONException {
		
		JSONArray array = this.json.getJSONArray( "posts" );
		int length = array.length();
		List<Post> posts = new ArrayList<Post>( length );
		
		for ( int i = 0; i < length; i++ ) {
			JSONObject object = array.getJSONObject( i );
			posts.add( Post.from( object ) );
		}
		
		return posts;
	}
	
}
