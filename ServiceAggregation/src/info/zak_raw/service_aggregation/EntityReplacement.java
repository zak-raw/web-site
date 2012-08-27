/**
 * 
 */
package info.zak_raw.service_aggregation;

import twitter4j.HashtagEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 * 
 * 
 * @author Junta Yoshizaki
 *
 */
class EntityReplacement {

	//------------- Fields -------------------------------------
	public final int start;
	public final int end;
	public final String text;
	
	//------------- Constructors -------------------------------
	public EntityReplacement( URLEntity entity ) {
		
		String expandedUrl = entity.getExpandedURL().toString();
		String displayUrl = entity.getDisplayURL();
		String anchor = new TagBuilder( 30 ).putAnchor( expandedUrl, displayUrl ).toString();
		
		this.start = entity.getStart();
		this.end = entity.getEnd();
		this.text = anchor;
	}
	
	public EntityReplacement( UserMentionEntity entity ) {
		
		String screenName = entity.getScreenName();
		String at = "@" + screenName;
		String url = "https://twitter.com/" + screenName;
		String anchor = new TagBuilder( 30 ).putAnchor( url, at ).toString();
		
		this.start = entity.getStart();
		this.end = entity.getEnd();
		this.text = anchor;
	}
	
	public EntityReplacement( HashtagEntity entity ) {
		
		String text = entity.getText();
		String tag = '#' + text;
		String url = "https://twitter.com/search/?q=%23" + text;
		String anchor = new TagBuilder( 30 ).putAnchor( url, tag ).toString();
		
		this.start = entity.getStart();
		this.end = entity.getEnd();
		this.text = anchor;
	}
	
	//------------- Methods ------------------------------------
	public boolean contains( int index ) {
		
		return this.start <= index && index < this.end;
	}
	
}
