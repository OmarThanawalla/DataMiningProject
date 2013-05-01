//What does this class do?
//represents a user's ranking of restuarants
public class UserVector {
	
	public static int globalNumberOfRest = 500; //assign this!!!
	public double[] userVector;
	
	public UserVector()
	{
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
