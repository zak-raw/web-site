/**
 * 
 */
package info.zak_raw.service_aggregation;

/**
 * @author Junta Yoshizaki
 *
 */
class TagBuilder {

	//------------- Fields -------------------------------------
	private StringBuilder builder;
	
	//------------- Constructors -------------------------------
	public TagBuilder( int initCapacity ) {
		
		this.builder = new StringBuilder( initCapacity );
	}
	
	//------------- Methods ------------------------------------
	@Override
	public String toString() {
		
		return this.builder.toString();
	}
	
	public void clear() {
		
		this.builder.setLength( 0 );
	}
	
	public TagBuilder putText( Object value ) {
		
		String text = ( value == null ) ? "" : value.toString();
		this.builder.append( text );
		
		return this;
	}
	
	public TagBuilder startTag( String tagName ) {
		
		this.builder.append( '<' ).append( tagName ).append( '>' );
		
		return this;
	}
	
	public TagBuilder startTag( String tagName, Attribute... attributes ) {
		
		this.builder.append( '<' ).append( tagName );
		
		for ( Attribute attribute : attributes ) {
			this.builder.append( ' ' ).append( attribute.name );
			this.builder.append( '=' );
			this.builder.append( '"' ).append( attribute.value ).append( '"' );
		}
		
		this.builder.append( '>' );
		
		return this;
	}
	
	public TagBuilder endTag( String tagName ) {
		
		this.builder.append( "</" ).append( tagName ).append( '>' );
		
		return this;
	}
	
	public TagBuilder putTag( String tagName, Object value ) {
		
		return this.startTag( tagName ).putText( value ).endTag( tagName );
	}
	
	public TagBuilder putTag( String tagName, Object value, Attribute... attributes ) {
		
		return this.startTag( tagName, attributes ).putText( value ).endTag( tagName );
	}
	
	public TagBuilder startTagWithClass( String tagName, String className ) {
		
		return this.startTag( tagName, new Attribute( "class", className ) );
	}
	
	public TagBuilder putTagWithClass( String tagName, String className, Object value ) {
		
		return this.putTag( tagName, value, new Attribute( "class", className ) );
	}
	
	public TagBuilder startAnchor( String href ) {
		
		return this.startTag( "a", new Attribute( "href", href ) );
	}
	
	public TagBuilder putAnchor( String href, Object value ) {
		
		return this.putTag( "a", value, new Attribute( "href", href ) );
	}

}
