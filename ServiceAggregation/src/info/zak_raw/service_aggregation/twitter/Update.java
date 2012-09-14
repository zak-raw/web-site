/**
 * 
 */
package info.zak_raw.service_aggregation.twitter;

import info.zak_raw.service_aggregation.ViewCache;
import info.zak_raw.service_aggregation.util.markup.Element;
import info.zak_raw.service_aggregation.util.markup.Tag;
import info.zak_raw.service_aggregation.util.markup.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.t2framework.t2.annotation.core.Default;
import org.t2framework.t2.annotation.core.Page;
import org.t2framework.t2.navigation.SimpleText;
import org.t2framework.t2.spi.Navigation;

import twitter4j.ResponseList;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

/**
 * Twitterタイムラインを取得してHTML断片に変換して保存します．
 * 
 * @author Junta Yoshizaki
 *
 */
@Page( "/update/twitter" )
public class Update {

	//------------- Fields -------------------------------------
	private DateFormat createdAtFormat;
	
	//------------- Constructors -------------------------------
	public Update() {
		
		this.createdAtFormat = new SimpleDateFormat( "yyyy-MM-dd E HH:mm:ss" );
		this.createdAtFormat.setTimeZone( TimeZone.getTimeZone( "Asia/Tokyo" ) );
	}
	
	//------------- Methods ------------------------------------
	@Default
	public Navigation update() {
		
		try {
			CharSequence htmlFragment = createHtmlFrament();
			ViewCache cache = new ViewCache( "twitter" );
			cache.updateContent( htmlFragment );
			
			return SimpleText.out( "ok" );
		}
		catch ( TwitterException e ) {
			e.printStackTrace();
			
			return SimpleText.out( e.getMessage() );
		}
	}
	
	private CharSequence createHtmlFrament() throws TwitterException {
		
		Twitter twitter = new TwitterFactory().getInstance();
		ResponseList<Status> timeline = twitter.getUserTimeline( "zak_raw" );
		
		Tag ul = new Tag( "ul" );
		for ( Status status : timeline ) {
			Tag li = new Tag( "li" );
			li.add( this.createElements( status ) );
			ul.add( li );
		}
		
		StringBuilder builder = new StringBuilder( 300 * 20 );
		ul.serialize( builder );
		
		return builder;
	}
	
	private List<Element> createElements( Status status ) {
		
		List<Element> elements = new ArrayList<Element>( 2 );
		elements.add( this.createDate( status ) );
		elements.add( this.createText( status ) );
		
		return elements;
	}
	
	private Element createDate( Status status ) {
		
		Date date = status.getCreatedAt();
		String screenName = status.getUser().getScreenName();
		
		String url = "https://twitter.com/" + screenName + "/statuses/" + status.getId();
		Element value = new Text( this.createdAtFormat.format( date ) );
		
		Tag p = Tag.withClass( "p", "date" );
		p.add( Tag.anchor( url, value ) );
		
		return p;
	}
	
	private Element createText( Status status ) {
		
		EntityReplacements replacements = new EntityReplacements( status );
		String text = status.getText();
		
		Tag p = Tag.withClass( "p", "text" );
		p.add( new Text( replacements.replace( text ) ) );
		
		return p;
	}

}
