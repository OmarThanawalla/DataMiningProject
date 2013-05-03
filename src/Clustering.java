import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

public class Clustering 
{
	public ArrayList<Cluster> clusters;
	public HashMap<Integer, UserVector> users;
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
			}
		}	
		
		int num_outliers = 0;
		for(int idx = 0; idx < length; ++idx)
			if(num_close_neighbours[idx] <= N) {
				users.remove(idx);
				++num_outliers;
		}
		
		System.out.print("Removed " + num_outliers + " outliers!");
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
			
			for(int idx = 0; idx < users.size(); ++idx) {
				UserVector user = users.get(idx);
				Cluster closestCluster = null;
				double minDistance = 1000000.0;
				
				for(Cluster myCluster : clusters) {
					double[] myClusterCenter = myCluster.getCenter();
					double[] userVect = user.toArray(dimensions);
					double dist = getDistance(userVect, myClusterCenter);
					
					if(dist < minDistance) {
						minDistance = dist;
						closestCluster = myCluster;
					}
				}
				
				closestCluster.addUser(new Integer(idx), user);
			}
			
			for(Cluster c : clusters) {
				done = done & c.calculateCenter();
			}
		}
	}
	
	public double getDistance(double[] v1, double[] v2) {
		double dist = 0.0; // Euclidean distance
		int smc = 1; // Simple matching coefficient
		
		for(int i = 0; i < v1.length; i++)
		  if(v1[i] != 0.0 && v2[i] != 0.0) {
			// User Rating Exists
			dist += Math.pow(v1[i] - v2[i], 2);
			++smc; // For normalization
		}
		
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
		String result = "";
		for(int i = 0; i < clusters.size(); i++) {
			System.out.println(clusters.get(i));
			result += clusters.get(i) + " \n";
		} 
		return result;
	}
	
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
		}
		
		return ratings;
	}
}
