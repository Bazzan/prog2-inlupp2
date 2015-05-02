package inlupp2;

import javax.swing.*;
import java.awt.*;
import java.io.*;

public class DescribedPlace extends Place implements Serializable{
	
	private String description;
	
	public DescribedPlace(String name, String description, Position position, Color c){
		super(name, position, c);
		this.description=description;
	}
	
	public String getDescription(){
		return description;
	}
	
	@Override
	public String toString(){
		String str=getName() + "\n" + description;
		return str;
		
	}

}
