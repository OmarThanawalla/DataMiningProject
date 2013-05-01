//What does this class do?
//Represents a review of a user for a business
public class Reviews {

	String user_id;
	String business_id;
	double rating;
	String text;
	
	
	public Reviews(String id, String businessId, double myRating, String myText)
	{	
		user_id = id;
		business_id = businessId;
		rating = myRating;
		text = myText;
	}
	
	public Reviews(YelpReview y) {
		user_id = y.user_id;
		business_id = y.business_id;
		rating = y.stars;
		text = y.text;
	}
	
	public String toString()
	{
		return ("user id: "+ user_id + " business id: " + business_id + " rating: " + rating + "\n text: " + text);
	}
	
}
