/**
 * 
 */
package info.zak_raw.service_aggregation.util.markup;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author Junta Yoshizaki
 *
 */
public class Tag implements Element {

	//------------- Statics ------------------------------------
	public static Tag withClass( String name, String className ) {
		
		return new Tag( name, new Attribute( "class", className ) );
	}
	
	public static Tag anchor( String href, Element content ) {
		
		Tag tag = new Tag( "a", new Attribute( "href", href ) );
		tag.add( content );
		
		return tag;
	}
	
	public static Tag img( String src, String alt ) {
		
		return new Tag( "img", new Attribute( "src", src ), new Attribute( "alt", alt ) );
	}
	
	//------------- Fields -------------------------------------
	private String name;
	private Attribute[] attributes;
	private List<Element> children;
	
	//------------- Constructors -------------------------------
	public Tag( String name, Attribute... attributes ) {
		
		this.name = name;
		this.attributes = attributes;
		
		this.children = new ArrayList<Element>();
	}
	
	//------------- Methods ------------------------------------
	/* (non-Javadoc)
	 * @see info.zak_raw.service_aggregation.util.markup.Element#put(info.zak_raw.service_aggregation.util.markup.TagBuilder)
	 */
	@Override
	public void serialize( StringBuilder builder ) {
		
		builder.append( '<' ).append( this.name );
		for ( Attribute attribute : this.attributes ) {
			builder.append( ' ' ).append( attribute.name );
			builder.append( '=' );
			builder.append( '"' ).append( attribute.value ).append( '"' );
		}
		builder.append( '>' );
		
		for ( Element child : this.children ) child.serialize( builder );
		
		builder.append( "</" ).append( this.name ).append( '>' );
	}
	
	public void add( Collection<Element> children ) {
		
		this.children.addAll( children );
	}
	
	public void add( Element child ) {
		
		this.children.add( child );
	}
	
	public int getChildCount() {
		
		return this.children.size();
	}

}
