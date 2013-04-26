import java.util.ArrayList;
import java.util.HashMap;


public class Clustering 
{
	HashMap<String, Integer> businesses;
	ArrayList<Cluster> clusters;
	HashMap<String, UserVector> users;
	static int  nextInt = 0;
	
	
	public Clustering()
	{
		businesses = new HashMap<String, Integer>();
		clusters = new ArrayList<Cluster>();
		users = new HashMap<String,UserVector>();
		
	}
	
	//loading the data
	public void loadData(Reviews [] reviews) //Reviews class not written!
	{
		for(Reviews AReview : reviews)
		{
			
			if(!businesses.containsKey(AReview.business_id))
			  {
				//insert business into hashmap
			    businesses.put(AReview.business_id, nextInt);
			    nextInt++;
			  }
			UserVector uv = users.get(AReview.user_id);
			if(uv == null)
			{
				uv = new UserVector(); //create new user
			}
			uv.addReview(businesses.get(AReview.business_id),AReview.rating);
			users.put(AReview.user_id, uv);
		}
		
	}
	
	//write toString method to test Clustering class
	public String toString()
	{
		
	}
	

}
