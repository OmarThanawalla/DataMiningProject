<<<<<<< HEAD
=======
// Review Object
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
public class Review {
	public int user_id;
	public int business_id;
	public double rating;
	public String text;
	
<<<<<<< HEAD
=======
	// Constructor
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	public Review(int userID, int businessID, double myRating, String myText) {	
		user_id = userID;
		business_id = businessID;
		rating = myRating;
		text = myText;
	}
	
<<<<<<< HEAD
=======
	// Print Review
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	public String toString()
	{
		return ("user id: "+ user_id + "; business id: " + business_id + 
				"; rating: " + rating + "\n text: " + text);
	}
	
}
