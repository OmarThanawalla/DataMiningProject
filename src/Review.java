// Review Object
public class Review {
	public int user_id;
	public int business_id;
	public double rating;
	public String text;
	
	// Constructor
	public Review(int userID, int businessID, double myRating, String myText) {	
		user_id = userID;
		business_id = businessID;
		rating = myRating;
		text = myText;
	}
	
	// Print Review
	public String toString()
	{
		return ("user id: "+ user_id + "; business id: " + business_id + 
				"; rating: " + rating + "\n text: " + text);
	}
	
}
