import java.util.Map.Entry;

// Calculate Performance Gain
public class EvaluateModel {
	
	public static double calculateError(Clustering clustering, double[] yelpRatings) {
		int num_businesses = PersonalizedReviews.getNumBusinesses();
		double oldErr = 0.0, newErr = 0.0;
	    
		// Calculate old and new Mean Square Error
		for(Cluster c : clustering.clusters) if(c.size() > 0) { 
		  
			// Calculate Review Ratings for the cluster
			Integer user_id = c.users.keySet().iterator().next();
			double [][] myMatrix = clustering.reweightReviews(user_id);
			double[] ratings = Clustering.getAvgRatings(myMatrix);
		  
			// Update ResultMatrix
			for(Entry<Integer, UserVector> e : c.users.entrySet()) {
				UserVector uv = e.getValue(); // User Ratings
			
				for(int j = 0; j < num_businesses; ++j) {
					double predictedRating = adjust(ratings[j]);
					double actualRating = adjust(uv.userVector[j]);
					double givenRating = yelpRatings[j];
					
					if(actualRating != 0) {
						oldErr += Math.pow(givenRating - actualRating, 2);
						newErr += Math.pow(predictedRating - actualRating, 2);
					}
				}
			}
		} 
		
		oldErr = Math.sqrt(oldErr);
		newErr = Math.sqrt(newErr);
		
		// Return Performance Gain
		if(oldErr == 0) return -1.0;
		else if(newErr == 0) return -2.0;
		else return (oldErr / newErr);
	}
	
	// Yelp ratings are *.0 or *.5
	public static double adjust(double x) {
		double rem = x % 1;
		if(x < 0.25) return (x - rem);
		else if(x < 0.5) return (x - rem + 0.5);
		else if(x < 0.75) return (x - rem + 0.75);
		else return x;
	}
}
