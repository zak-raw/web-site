/**
 * 
 */
package info.zak_raw.service_aggregation.tumblr;

import com.google.appengine.labs.repackaged.org.json.JSONObject;

/**
 * @author Junta Yoshizaki
 *
 */
class JSONObjectWrapper {

	//------------- Fields -------------------------------------
	protected final JSONObject json;
	
	//------------- Constructors -------------------------------
	public JSONObjectWrapper( JSONObject json ) {
		
		this.json = json;
	}
	
}
