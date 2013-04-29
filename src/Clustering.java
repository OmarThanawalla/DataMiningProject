import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

//What does this class do?
//Holds clusters, businesses, users 
public class Clustering 
{
	HashMap<String, Integer> businesses;
	public ArrayList<Cluster> clusters;
	HashMap<String, UserVector> users;
	HashMap<String, ArrayList<Reviews>> userReviews;
	static int  nextInt = 0;
	public static double P = 0.5;
	public Book mySenti;
	
	
	public Clustering()
	{
		businesses = new HashMap<String, Integer>();
		clusters = new ArrayList<Cluster>();
		users = new HashMap<String,UserVector>();
		userReviews = new HashMap<String, ArrayList<Reviews>>();
		mySenti = new Book(); 
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
			ArrayList<Reviews> listOfReviews = userReviews.get(AReview.user_id);
			if(listOfReviews == null)
			{
				listOfReviews = new ArrayList<Reviews>();
			}
			listOfReviews.add(AReview);
			mySenti.addReview(AReview.text, AReview.rating);
			userReviews.put(AReview.user_id, listOfReviews);
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
	public void printUserReviews()
	{
		System.out.println(userReviews);
	}
	
	public Cluster locateUser(String id)
	{
		for(Cluster myCluster : clusters)
		{
			if(myCluster.users.containsKey(id))
			{
				return myCluster;
			}
		}
		return null;
	}
	
	public double[][] reweightReviews(String id)
	{
		Cluster targetCluster = locateUser(id);
		double[][] Matrix = new double [targetCluster.users.size()][businesses.size()];
		int i = 0;
		for(Entry<String, UserVector> e : targetCluster.users.entrySet())
		{
			ArrayList<Reviews> userRev = userReviews.get(e.getKey());
			for(Reviews r : userRev)
			{
				StringTokenizer tkn = new StringTokenizer(r.text);
				int num_words = 0;
				double total_score = 0.0;
				
				while(tkn.hasMoreTokens())
				{
					ArrayList<Double> values = mySenti.sentiBook.get(tkn.nextToken());
					if(values != null) 
					{
						total_score = total_score +  values.get(1);
						num_words = num_words + 1 ;
					}
				}
				if(num_words != 0)
				{
					total_score = total_score / num_words;
				}
				Matrix[i][businesses.get(r.business_id)] = (P * r.rating) + ((1-P)*total_score);
			}
			i++;
		}
		return Matrix;
	}

}
