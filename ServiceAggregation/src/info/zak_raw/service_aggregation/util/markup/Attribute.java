/**
 * 
 */
package info.zak_raw.service_aggregation.util.markup;

/**
 * マークアップタグの属性です．
 * 
 * @author Junta Yoshizaki
 *
 */
public class Attribute {
	
	//------------- Fields -------------------------------------
	public final String name;
	public final String value;
	
	//------------- Constructors -------------------------------
	public Attribute( String name, Object value ) {
		
		this.name = name;
		this.value = ( value == null ) ? "" : value.toString();
	}
}
