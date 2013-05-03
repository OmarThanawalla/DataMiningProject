<<<<<<< HEAD
import java.util.HashMap;
import java.util.Map.Entry;

// User's Ratings of Businesses
=======
// Represent a user's ranking of restaurants
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
public class UserVector {
	private HashMap<Integer, Double> userVector;
	
<<<<<<< HEAD
	public UserVector() {
		userVector = new HashMap<Integer, Double>();
=======
	public static int globalNumberOfRest;
	public double[] userVector;
	
	public UserVector()
	{
		globalNumberOfRest = PersonalizedReviews.getNumBusinesses();
		userVector = new double[globalNumberOfRest];
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	}

	public HashMap<Integer, Double> getVector() {
		return userVector;
	}

	public void addReview(Integer idx, Double rating) {
		userVector.put(idx, rating);
	}
	
	public Double getReview(Integer idx) {
		return userVector.get(idx);
	}
	
	public double[] toArray(int dimensions) {
		double[] arr = new double[dimensions];
		for(Entry<Integer, Double> e : userVector.entrySet()) {
			arr[e.getKey()] = e.getValue();
		}
		return arr;
	}
}
