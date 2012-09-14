/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import info.zak_raw.service_aggregation.util.markup.Element;
import info.zak_raw.service_aggregation.util.markup.Text;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
class NullPost extends Post {

	//------------- Constructors -------------------------------
	public NullPost( JSONObject json ) {
		
		super( json );
	}

	//------------- Methods ------------------------------------
	@Override
	public Element createElement() {
		
		return new Text( "" );
	}

}
