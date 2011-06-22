package timeline;

import java.io.Serializable;

public class DoubleString implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public String a;
	public String b;
	
	public DoubleString( String a, String b )
	{
		this.a = a;
		this.b = b;
	}
	
	public DoubleString()
	{
		
	}
}
