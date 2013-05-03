// Represent a user's ranking of restaurants
public class UserVector {
	
	public static int globalNumberOfRest;
	public double[] userVector;
	
	public UserVector()
	{
		globalNumberOfRest = PersonalizedReviews.getNumBusinesses();
		userVector = new double[globalNumberOfRest];
	}

	public void addReview(int idx, double score)
	{
		userVector[idx] = score;
	}
	
	public double getReview(int idx)
	{
		return userVector[idx];
	}
	
}
