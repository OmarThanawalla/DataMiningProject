import java.util.HashMap;
import java.util.Map.Entry;

// User's Ratings of Businesses
public class UserVector {
	private HashMap<Integer, Double> userVector;
	
	public UserVector() {
		userVector = new HashMap<Integer, Double>();
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
