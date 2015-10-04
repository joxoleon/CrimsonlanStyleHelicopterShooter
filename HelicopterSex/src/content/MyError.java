package content;

public class MyError extends Exception
{
	private static final long serialVersionUID = 1L;

	public String message;
	
	
	public MyError(String message)
	{
		this.message = message;
	}
	
	public String toString()
	{
		return message;
	}
}
