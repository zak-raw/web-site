/**
 * 
 */
package info.zak_raw.service_aggregation.twitter;

import info.zak_raw.service_aggregation.TagBuilder;
import info.zak_raw.service_aggregation.ViewCache;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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
public class TwitterUpdate {

	//------------- Fields -------------------------------------
	private final TagBuilder builder;
	private DateFormat createdAtFormat;
	
	//------------- Constructors -------------------------------
	public TwitterUpdate() {
		
		this.builder = new TagBuilder( 200 * 20 ); // 1ツイートあたり平均200文字くらい？
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
		
		this.builder.clear();
		
		Twitter twitter = new TwitterFactory().getInstance();
		ResponseList<Status> timeline = twitter.getUserTimeline( "zak_raw" );
		
		this.builder.startTag( "ul" );
		for ( Status status : timeline ) {
			this.builder.startTag( "li" );
			this.build( status );
			this.builder.endTag( "li" );
		}
		
		this.builder.endTag( "ul" );
		
		return this.builder.toString();
	}
	
	private void build( Status status ) {
		
		this.buildCreatedAt( status );
		this.buildText( status );
	}
	
	private void buildCreatedAt( Status status ) {
		
		Date date = status.getCreatedAt();
		String screenName = status.getUser().getScreenName();
		
		String url = "https://twitter.com/" + screenName + "/statuses/" + status.getId();
		String value = this.createdAtFormat.format( date );
		
		this.builder.startTagWithClass( "p", "date" );
		this.builder.putAnchor( url, value );
		this.builder.endTag( "p" );
	}
	
	private void buildText( Status status ) {
		
		this.builder.startTagWithClass( "p", "text" );
		
		EntityReplacements replacements = new EntityReplacements( status );
		String text = status.getText();
		if ( replacements.isEmpty() ) {
			this.builder.putText( text );
		}
		else {
			this.buildText( text, replacements );
		}
		
		this.builder.endTag( "p" );
	}
	
	private void buildText( CharSequence text, EntityReplacements replacements ) {
		
		int start = 0;
		for ( EntityReplacement replacement : replacements ) {
			if ( start < replacement.start ) {
				this.builder.putText( text.subSequence( start, replacement.start ) );
			}
			
			this.builder.putText( replacement.text );
			start = replacement.end;
		}
		
		if ( start < text.length() ) {
			this.builder.putText( text.subSequence( start, text.length() ) );
		}
	}

}
