import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

//What does this class do?
//Holds clusters, businesses, users 
public class Clustering 
{
	public ArrayList<Cluster> clusters;
	public HashMap<Integer, UserVector> users;
	public HashMap<Integer, ArrayList<Review>> userReviews;
	
	public static double P = 0.5;
	public Book mySenti;
	
	// Constructor
	public Clustering()
	{
		clusters = new ArrayList<Cluster>();
		users = new HashMap<Integer, UserVector>();
		userReviews = new HashMap<Integer, ArrayList<Review>>();
		mySenti = new Book();
	}
	
	//loading the data
	public void loadData(Review[] reviews) 
	{
		//int num_users = 0;
		for(Review AReview : reviews)
		{
			UserVector uv = users.get(AReview.user_id);
			if(uv == null)
			{
				//System.out.println(num_users++);
				uv = new UserVector(); //create new user
			}
			uv.addReview(AReview.business_id,AReview.rating);
			users.put(AReview.user_id, uv);
			ArrayList<Review> listOfReviews = userReviews.get(AReview.user_id);
			if(listOfReviews == null)
			{
				listOfReviews = new ArrayList<Review>();
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
			for(int idx = 0; idx < users.size(); ++idx)
			{
				UserVector e = users.get(idx);
				Cluster closestCluster = null;
				double minDistance = 1000000.0;
				for(Cluster myCluster : clusters)
				{
					double [] myClusterCenter = myCluster.getCenter();
					double dist = getDistance(e.userVector,myClusterCenter);
					if(dist < minDistance)
					{
						minDistance = dist;
						closestCluster = myCluster;
					}
				}
			closestCluster.addUser(new Integer(idx), e);
			}
			for(Cluster c : clusters)
			{
				done = done & c.calculateCenter();
			}
		}
	}
	
	public double getDistance(double[] v1, double [] v2)
	{
		double dist = 0.0; // Euclidean distance
		int smc = 1; // Simple matching coefficient
		
		for(int i = 0; i < v1.length; i++)
		  if(v1[i] != 0.0 && v2[i] != 0.0) {
			// User Rating Exists
			dist += Math.pow(v1[i] - v2[i], 2);
			++smc; // For normalization
		}
		
		if(smc == 0) return 10.0; // infinite
		else return Math.sqrt(dist) / smc;
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
	
	public void printUsers()
	{
		System.out.println(users);
	}
	public void printUserReviews()
	{
		System.out.println(userReviews);
	}
	
	public Cluster locateUser(Integer id)
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
	
	public double[][] reweightReviews(Integer id)
	{
		Cluster targetCluster = locateUser(id);
		double[][] Matrix = new double [targetCluster.users.size()][UserVector.globalNumberOfRest];
		int i = 0;
		for(Entry<Integer, UserVector> e : targetCluster.users.entrySet())
		{
			ArrayList<Review> userRev = userReviews.get(e.getKey());
			for(Review r : userRev)
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
				Matrix[i][r.business_id] = (P * r.rating) + ((1-P)*total_score);
			}
			i++;
		}
		return Matrix;
	}
	
	public static double[] getAvgRatings(double[][] m) {
		double[] ans = new double[m[0].length];
		double[] cnt = new double[m[0].length];
		for(int i = 0; i < m.length; ++i) {
			for(int j = 0; j < m[0].length; ++j) {
				ans[j] += m[i][j]; 
				if(m[i][j] != 0) cnt[j]++;
			}
		}
		for(int i = 0; i < m[0].length; i++) {
			if(cnt[i] != 0) ans[i] /= cnt[i];
		}
		return ans;
	}
}
