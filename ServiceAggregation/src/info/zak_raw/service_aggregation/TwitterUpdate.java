/**
 * 
 */
package info.zak_raw.service_aggregation;

import org.t2framework.t2.annotation.core.Default;
import org.t2framework.t2.annotation.core.Page;

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
@Page( "update/twitter" )
public class TwitterUpdate {

	@Default
	public void update() {
		
		try {
			CharSequence htmlFragment = createHtmlFrament();
			ViewCache cache = new ViewCache( "twitter" );
			cache.updateContent( htmlFragment );
		}
		catch ( TwitterException e ) {
			e.printStackTrace();
		}
	}
	
	private static CharSequence createHtmlFrament() throws TwitterException {
		
		Twitter twitter = new TwitterFactory().getInstance();
		ResponseList<Status> timeline = twitter.getUserTimeline( "zak_raw" );
		StringBuilder htmlFragments = new StringBuilder( timeline.size() * 200 );
		
		htmlFragments.append( "<ul>" );
		for ( Status status : timeline ) {
			CharSequence fragment = createHtmlFragment( status );
			htmlFragments.append( "<li>" ).append( fragment ).append( "</li>" );
		}
		htmlFragments.append( "</ul>" );
		
		return htmlFragments;
	}
	
	private static CharSequence createHtmlFragment( Status status ) {
		
		// TODO
		
		
		return "";
	}

}
