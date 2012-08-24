/**
 * 
 */
package info.zak_raw.service_aggregation;

/**
 * マークアップタグの属性です．
 * 
 * @author Junta Yoshizaki
 *
 */
class Attribute {
	
	//------------- Fields -------------------------------------
	public final String name;
	public final String value;
	
	//------------- Constructors -------------------------------
	public Attribute( String name, Object value ) {
		
		this.name = name;
		this.value = ( value == null ) ? "" : value.toString();
	}
}
