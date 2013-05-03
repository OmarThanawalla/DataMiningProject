import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Clustering 
{
	public ArrayList<Cluster> clusters;
	public HashMap<Integer, UserVector> users;
<<<<<<< HEAD
	public int dimensions;
	
	public Clustering(int dim) {
		clusters = new ArrayList<Cluster>();
		users = new HashMap<Integer, UserVector>();
		dimensions = dim;
	}
	
	public void loadData(Review[] reviews) {
		for(Review aReview : reviews) {
			UserVector uv = users.get(aReview.user_id);
			if(uv == null) {uv = new UserVector();}
			uv.addReview(aReview.business_id, aReview.rating);
			users.put(aReview.user_id, uv);
		}
	}
	
	public void eliminateOutliers(double D, int N) {
		int length = users.size();
		int[] num_close_neighbours = new int[length];
		
		for(Entry<Integer, UserVector> u1 : users.entrySet()) {
			for(Entry<Integer, UserVector> u2 : users.entrySet()) {
				double[] v1 = u1.getValue().toArray(dimensions);
				double[] v2 = u2.getValue().toArray(dimensions);
				
				if(getDistance(v1, v2) <= D) {
					++num_close_neighbours[u1.getKey()];
					++num_close_neighbours[u1.getKey()];
				}
=======
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
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
			}
		}	
		
		int num_outliers = 0;
		for(int idx = 0; idx < length; ++idx)
			if(num_close_neighbours[idx] <= N) {
				users.remove(idx);
				++num_outliers;
		}
<<<<<<< HEAD
		
		System.out.print("Removed " + num_outliers + " outliers!");
=======
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	}
	
	public void performKmeans(int K) {
		for(int i = 0; i < K; i++) {
			Cluster aCluster = new Cluster(dimensions);
			clusters.add(aCluster);
		}
		
		boolean done = false;
		int numberOfSteps = 0;
		
		while(!done && (numberOfSteps < 20))
		{
			System.out.println("Step #" + (++numberOfSteps));
			for(Cluster c : clusters) {c.clearUsers();}
			done = true;
<<<<<<< HEAD
			
			for(int idx = 0; idx < users.size(); ++idx) {
				UserVector user = users.get(idx);
				Cluster closestCluster = null;
				double minDistance = 1000000.0;
				
				for(Cluster myCluster : clusters) {
					double[] myClusterCenter = myCluster.getCenter();
					double[] userVect = user.toArray(dimensions);
					double dist = getDistance(userVect, myClusterCenter);
					
					if(dist < minDistance) {
=======
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
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
						minDistance = dist;
						closestCluster = myCluster;
					}
				}
<<<<<<< HEAD
				
				closestCluster.addUser(new Integer(idx), user);
=======
			closestCluster.addUser(new Integer(idx), e);
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
			}
			
			for(Cluster c : clusters) {
				done = done & c.calculateCenter();
			}
		}
	}
	
<<<<<<< HEAD
	public double getDistance(double[] v1, double[] v2) {
=======
	public double getDistance(double[] v1, double [] v2)
	{
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
		double dist = 0.0; // Euclidean distance
		int smc = 1; // Simple matching coefficient
		
		for(int i = 0; i < v1.length; i++)
		  if(v1[i] != 0.0 && v2[i] != 0.0) {
			// User Rating Exists
			dist += Math.pow(v1[i] - v2[i], 2);
			++smc; // For normalization
		}
		
<<<<<<< HEAD
		if(smc == 0) return 1000000.0;
		else return Math.sqrt(dist) / smc;
	}

	public Cluster locateUser(Integer id) {
		for(Cluster myCluster : clusters) {
			if(myCluster.getUsers().containsKey(id)) {
				return myCluster;
			}
		}
		return null;
	}
	
	public void printUsers() {System.out.println(users);}
	
	public String toString() {
=======
		if(smc == 0) return 10.0; // infinite
		else return Math.sqrt(dist) / smc;
	}
	
	//write toString method to test Clustering class
	public String toString()
	{
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
		String result = "";
		for(int i = 0; i < clusters.size(); i++) {
			System.out.println(clusters.get(i));
			result += clusters.get(i) + " \n";
		} 
		return result;
	}
	
<<<<<<< HEAD
	public double[] getAvgRatings(Cluster c) {
		double[] ratings = new double[dimensions];
		double[] counter = new double[dimensions];
		
		for(UserVector user : c.getUsers().values()) {
			HashMap<Integer, Double> vect = user.getVector();
			for(Entry<Integer, Double> e : vect.entrySet()) {
				ratings[e.getKey()] += e.getValue();
				++counter[e.getKey()];
			}
		}
		
		// Average Total Ratings
		for(int idx = 0; idx < dimensions; ++idx) {
			if(counter[idx] != 0) {ratings[idx] /= counter[idx];}
=======
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
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
		}
		
		return ratings;
	}
<<<<<<< HEAD
=======
	
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
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
}
