/**
 * 
 */
package info.zak_raw.service_aggregation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import twitter4j.ResponseList;
import twitter4j.Status;

/**
 * @author Junta Yoshizaki
 *
 */
class TwitterViewBuilder {

	//------------- Fields -------------------------------------
	private final TagBuilder builder;
	private final ResponseList<Status> timeline;
	private DateFormat createdAtFormat;
	
	//------------- Constructors -------------------------------
	public TwitterViewBuilder( ResponseList<Status> timeline ) {
		
		this.timeline = timeline;
		this.builder = new TagBuilder( timeline.size() * 200 );
		this.createdAtFormat = new SimpleDateFormat( "yyyy年M月d日(E)HH:mm:ss", Locale.JAPAN );
	}
	
	//------------- Methods ------------------------------------
	@Override
	public String toString() {
		
		this.builder.startTag( "ul" );
		for ( Status status : this.timeline ) {
			this.builder.startTag( "li" );
			this.build( status );
			this.builder.endTag( "li" );
		}
		
		this.builder.endTag( "ul" );
		
		return this.builder.toString();
	}
	
	private void build( Status status ) {
		// TODO
		this.buildCreatedAt( status.getCreatedAt() );
	}
	
	private void buildCreatedAt( Date date ) {
		
		String value = this.createdAtFormat.format( date );
		this.builder.putTagWithClass( "p", "created-at", value );
	}
	
	private void buildText( Status status ) {
		
		
	}

}
