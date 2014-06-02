package draft_network_web;

import java.util.Vector;

import com.google.gson.Gson;

public class subforumjASON  {

	
	private String name; 
	private Vector<Integer> intlist ;
	public subforumjASON(String name)
	{
		this.name=name;
		intlist = new Vector<Integer>();
		intlist.add(new Integer(5));
		intlist.add(new Integer(6));
		intlist.add(new Integer(7));
		intlist.add(new Integer(8));

		
	}
	
	
	public static void main(String[] args){
		Gson gson = new Gson();
		String obj = gson.toJson(new subforumjASON("every tear drop is a waterfall"));
		System.out.println(obj);
		
		subforumjASON newsub = gson.fromJson(obj, subforumjASON.class);	
		System.out.println(newsub);
	
	}


	@Override
	public String toString() {
		return "subforumjASON [name=" + name + ", intlist=" + intlist + "]";
	}	


}


