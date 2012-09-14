/**
 * 
 */
package info.zak_raw.service_aggregation.util.markup;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Junta Yoshizaki
 *
 */
public class ElementList implements Element {

	//------------- Fields -------------------------------------
	private List<Element> elements;
	
	//------------- Constructors -------------------------------
	public ElementList() {
		
		this.elements = new ArrayList<Element>();
	}
	
	//------------- Methods ------------------------------------
	public void add( Element element ) {
		
		this.elements.add( element );
	}
	
	@Override
	public void serialize( StringBuilder builder ) {
		
		for ( Element element : this.elements ) element.serialize( builder );
	}

}
