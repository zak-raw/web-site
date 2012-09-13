/**
 * 
 */
package info.zak_raw.service_aggregation.twitter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import twitter4j.HashtagEntity;
import twitter4j.Status;
import twitter4j.URLEntity;
import twitter4j.UserMentionEntity;

/**
 * @author Junta Yoshizaki
 *
 */
class EntityReplacements {
	
	//------------- Fields -------------------------------------
	private final List<EntityReplacement> replacements;
	
	//------------- Constructors -------------------------------
	public EntityReplacements( Status status ) {
		
		this.replacements = new ArrayList<EntityReplacement>();
		this.replacements.addAll( createUrlReplacements( status ) );
		this.replacements.addAll( createMentionReplacements( status ) );
		this.replacements.addAll( createHashtagReplacements( status ) );
		Collections.sort( this.replacements );
	}
	
	private static Collection<EntityReplacement> createUrlReplacements( Status status ) {
		
		URLEntity[] entities = status.getURLEntities();
		
		if ( entities == null ) return Collections.emptyList();
		
		Collection<EntityReplacement> replacements =
				new ArrayList<EntityReplacement>( entities.length );
		for ( URLEntity entity : entities ) {
			replacements.add( new EntityReplacement( entity ) );
		}
		
		return replacements;
	}
	
	private static Collection<EntityReplacement> createMentionReplacements( Status status ) {
		
		UserMentionEntity[] entities = status.getUserMentionEntities();
		
		if ( entities == null ) return Collections.emptyList();
		
		Collection<EntityReplacement> replacements =
				new ArrayList<EntityReplacement>( entities.length );
		for ( UserMentionEntity entity : entities ) {
			replacements.add( new EntityReplacement( entity ) );
		}
		
		return replacements;
	}
	
	private static Collection<EntityReplacement> createHashtagReplacements( Status status ) {
		
		HashtagEntity[] entities = status.getHashtagEntities();
		
		if ( entities == null ) return Collections.emptyList();
		
		Collection<EntityReplacement> replacements =
				new ArrayList<EntityReplacement>( entities.length );
		for ( HashtagEntity entity : entities ) {
			replacements.add( new EntityReplacement( entity ) );
		}
		
		return replacements;
	}
	
	//------------- Methods ------------------------------------
	public CharSequence replace( CharSequence rawText ) {
		
		if ( this.replacements.isEmpty() ) return rawText;
		
		StringBuilder text = new StringBuilder( 140 );
		
		int start = 0;
		for ( EntityReplacement replacement : this.replacements ) {
			if ( start < replacement.start ) {
				text.append( rawText.subSequence( start, replacement.start ) );
			}
			
			replacement.anchor.put( text );
			start = replacement.end;
		}
		
		if ( start < rawText.length() ) {
			text.append( rawText.subSequence( start, rawText.length() ) );
		}
		
		return text;
	}
	
}
