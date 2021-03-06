/**
 * 
 */
package info.zak_raw.service_aggregation;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.Key;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.appengine.api.datastore.Text;

public class ViewCache {
	
	//------------- Constants ----------------------------------
	private static final String KIND = "info.zak_raw.service_aggregation.view_cache";
	private static final String CONTENT = "content";
	private static final String LAST_MODIFIED = "last_modified";
	
	//------------- Fields -------------------------------------
	private Entity entity;
	
	//------------- Constructors -------------------------------
	public ViewCache( String name ) {
		
		DatastoreService datastore = DatastoreServiceFactory.getDatastoreService();
		Key key = KeyFactory.createKey( KIND, name );
		try {
			this.entity = datastore.get( key );
		}
		catch ( EntityNotFoundException e ) {
			this.entity = new Entity( key );
		}
	}
	
	//------------- Methods ------------------------------------
	public String getContent() {
		
		Object value = this.entity.getProperty( CONTENT );
		if ( value instanceof Text ) {
			Text text = (Text) value;
			
			return text.getValue();
		}
		else {
			return "";
		}
	}
	
	public long getLastModified() {
		
		Object value = this.entity.getProperty( LAST_MODIFIED );
		if ( value instanceof Number ) {
			Number number = (Number) value;
			return number.longValue();
		}
		else {
			return 0;
		}
	}
	
	public void updateContent( CharSequence value ) {
		
		this.update( CONTENT, new Text( value.toString() ) );
	}
	
	public void updateLastModified( long time ) {
		
		this.update( LAST_MODIFIED, time );
	}
	
	private void update( String name, Object value ) {
		
		this.entity.setProperty( name, value );
		DatastoreServiceFactory.getDatastoreService().put( this.entity );
	}
	
}
