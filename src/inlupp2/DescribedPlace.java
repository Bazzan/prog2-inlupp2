package inlupp2;

public class DescribedPlace extends Place{
	
	private String description;
	
	public DescribedPlace(String name, String description){
		super(name);
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
	
	@Override
	public String toString(){
		String str=getName() + System.getProperty("line.separator") + description;
		return str;
		
	}

}
