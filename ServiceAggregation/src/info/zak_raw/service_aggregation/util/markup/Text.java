package info.zak_raw.service_aggregation.util.markup;

/**
 * 
 * @author Junta Yoshizaki
 *
 */
public class Text implements Element {

	//------------- Fields -------------------------------------
	private final CharSequence text;
	
	//------------- Constructors -------------------------------
	public Text( CharSequence text ) {
		
		this.text = text;
	}
	
	//------------- Methods ------------------------------------
	@Override
	public void put( StringBuilder builder ) {
		
		builder.append( this.text );
	}

}
