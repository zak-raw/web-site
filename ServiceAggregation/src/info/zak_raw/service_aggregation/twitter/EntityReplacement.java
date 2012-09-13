/**
 * 
 */
package info.zak_raw.service_aggregation.twitter;

import info.zak_raw.service_aggregation.util.markup.Element;
import info.zak_raw.service_aggregation.util.markup.Tag;
import info.zak_raw.service_aggregation.util.markup.Text;
import twitter4j.HashtagEntity;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 * 
 * 
 * @author Junta Yoshizaki
 *
 */
class EntityReplacement implements Comparable<EntityReplacement> {

	//------------- Fields -------------------------------------
	public final int start;
	public final int end;
	public final Element anchor;
	
	//------------- Constructors -------------------------------
	public EntityReplacement( URLEntity entity ) {
		
		String expandedUrl = entity.getExpandedURL().toString();
		String displayUrl = entity.getDisplayURL();
		
		this.start = entity.getStart();
		this.end = entity.getEnd();
		this.anchor = Tag.anchor( expandedUrl, new Text( displayUrl ) );
	}
	
	public EntityReplacement( UserMentionEntity entity ) {
		
		String screenName = entity.getScreenName();
		String at = "@" + screenName;
		String url = "https://twitter.com/" + screenName;
		
		this.start = entity.getStart();
		this.end = entity.getEnd();
		this.anchor = Tag.anchor( url, new Text( at ) );
	}
	
	public EntityReplacement( HashtagEntity entity ) {
		
		String text = entity.getText();
		String tag = '#' + text;
		String url = "https://twitter.com/search/?q=%23" + text;
		
		this.start = entity.getStart();
		this.end = entity.getEnd();
		this.anchor = Tag.anchor( url, new Text( tag ) );
	}
	
	//------------- Methods ------------------------------------
	@Override
	public int compareTo( EntityReplacement another ) {
		
		if ( this.start < another.start ) return -1;
		if ( another.start < this.start ) return 1;
		
		return 0;
	}
	
}
