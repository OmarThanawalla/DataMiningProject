import java.util.HashMap;
import java.util.Map.Entry;

// Calculate Performance Gain
public class EvaluateModel {
	
	public static HashMap<Integer, UserVector> users = new HashMap<Integer, UserVector>();
	
	public static double calculateError(Clustering clustering, double[] yelpRatings) {
		double oldErr = 0.0, newErr = 0.0;
	    
		// Calculate old and new Mean Square Error
		for(Cluster c : clustering.clusters) if(c.size() > 0) { 
		  
			// Calculate Review Ratings for the cluster
			double[] ratings = clustering.getAvgRatings(c);
		  
			// Update ResultMatrix
			for(Entry<Integer, UserVector> e : c.getUsers().entrySet()) {
				Integer user_id = e.getKey(); // User Id
				UserVector user = e.getValue(); // User Ratings
			
				for(Entry<Integer, Double> vect : user.getVector().entrySet()) {
					int j = vect.getKey();
					double predictedRating = adjust(ratings[j]);
					double actualRating = adjust(users.get(user_id).getVector().get(j));
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
	
	public static void loadData(Review[] reviews) {
		for(Review review : reviews) {
			UserVector user = users.get(review.user_id);
			if(user == null) {user = new UserVector();}
			user.addReview(review.business_id, review.rating);
			users.put(review.user_id, user);
		}
	}
}
