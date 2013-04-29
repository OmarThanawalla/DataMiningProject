import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

//What does this class do?
//Holds clusters, businesses, users 
public class Clustering 
{
	HashMap<String, Integer> businesses;
	public ArrayList<Cluster> clusters;
	HashMap<String, UserVector> users;
	static int  nextInt = 0;
	
	
	public Clustering()
	{
		businesses = new HashMap<String, Integer>();
		clusters = new ArrayList<Cluster>();
		users = new HashMap<String,UserVector>();
		
	}
	
	//loading the data
	public void loadData(Reviews [] reviews) 
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
	
	//perform k means
	public void performKmeans(int k)
	{
		//initialize k clusters
		for(int i = 0; i < k; i++)
		{
			Cluster aCluster = new Cluster();
			//put cluster into Array Cluster
			clusters.add(aCluster);
		}
		boolean done = false;
		int numberOfSteps = 0;
		while(!done && (numberOfSteps < 100))
		{
			numberOfSteps++;
			for(Cluster cl : clusters)
			{
				cl.empty();
			}
			done = true;
			for(Entry<String, UserVector> e : users.entrySet())
			{
				Cluster closestCluster = null;
				double minDistance = 1000000.0;
				for(Cluster myCluster : clusters)
				{
					double [] myClusterCenter = myCluster.getCenter();
					double dist = getDistance(e.getValue().userVector,myClusterCenter);
					if(dist < minDistance)
					{
						minDistance = dist;
						closestCluster = myCluster;
					}
				}
			closestCluster.addUser(e.getKey(), e.getValue());
			}
			for(Cluster c : clusters)
			{
				done = done & c.calculateCenter();
			}
		}
	}
	
	public double getDistance(double[] v1, double [] v2)
	{
		double dist = 0.0;
		for(int i = 0; i < v1.length; i++)
		{
			dist += Math.pow(el_dist(v1[i],v2[i]),2);
		}
		return Math.sqrt(dist);
	}
	public double el_dist(double x, double y)
	{
		return (x-y); //euclideian distance
	}
	
	
	//write toString method to test Clustering class
	public String toString()
	{
		String result = "";
		//iterate through array of clusters and print them
		for(int i =0 ; i < clusters.size(); i++)
		{
			System.out.println("Can you see this!?");
			System.out.println(clusters.get(i));
			result += clusters.get(i) + " \n";
		}
		return result;
	}
	
	public void printBusinesses()
	{
		System.out.println(businesses);
	}
	public void printUsers()
	{
		System.out.println(users);
	}
	

}
