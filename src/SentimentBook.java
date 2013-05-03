import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

<<<<<<< HEAD
// Dictionary for Semantic Analysis
class SentimentBook {
=======



//STEP 3
//What does this class do?
//Represents the senti book object


public class SentimentBook {
	
	/*public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("You called main method");
		
		//test sentibook
//		Book myBook = new Book();
//		myBook.addReview("This restaurant at Kona grill is really good. I had the sushi and it was amazing!", 5.0);
//		myBook.addReview("This restaurant sucks and is bad. Sushi was terrible and not amazing. sushi", 1.0);
//		System.out.println(myBook);
		
		//test Cluster
		System.out.println("Begin test 1 \n");
		Cluster aCluster = new Cluster();
		Cluster bCluster = new Cluster();
		System.out.println(aCluster);
		System.out.println(bCluster);
		
		//test number 2 - create UserVector
		System.out.println("Begin test 2 \n");
		UserVector aUser = new UserVector();
		aUser.addReview(1,5.0);
		aCluster.addUser("Omar", aUser);
		
		UserVector bUser = new UserVector();
		bUser.addReview(2,1.0);
		aCluster.addUser("Slavcho", bUser);
		
		System.out.println(aCluster);

		//test number 3 calculateCenter
		System.out.println("\n Begin test 3");
		aCluster.calculateCenter();
		System.out.println(aCluster);
		
		//	//////////////////////////////////////////////////////////////////
		// test number 4 create a new review object
		System.out.println("\n Begin test number 4 \n");
		Reviews myReview = new Reviews("myUserID1", "myBusinessID1", 5.0, "I love");
		Reviews yourReview = new Reviews("myUserID2", "myBusinessID2", 1.0, "I hate");
		Reviews theirReview = new Reviews("myUserID3","myBusinessID2", 1.0, "I love");
		System.out.println(myReview);
		
		//Load all the Yelp Data
		System.out.println("\n Begin loading all the Yelp data: \n");
		Reviews [] someReviews;
				
		ArrayList<Reviews> mydata = JSON_Parser.parseYelpData();
		int size = mydata.size();
		someReviews = new Reviews[size+3];
		for(int i = 0; i < size; ++i)
		  someReviews[i+3] = mydata.get(i);	
		
		someReviews[0] = myReview;
		someReviews[1] = yourReview;
		someReviews[2] = theirReview;
		
		//test number 5, loadData into Clustering object
		System.out.println("\n Begin test number 5 \n");

		//create Clustering object
		
		Clustering clustering1 = new Clustering();
		clustering1.loadData(someReviews);
		//System.out.println(clustering1);
		clustering1.printBusinesses();
		clustering1.printUsers();
		
		//test number 5.5, test loading of userReviews 
		System.out.println("\nBeging test number 5.5: test loading of userReviews hashmap through load method call \n");
		clustering1.printUserReviews();
		
		//test number 6, test performKmeans
		System.out.println("\n Begin test number 6: test performKmeans \n");
		
		clustering1.performKmeans(1);
		for(Cluster c : clustering1.clusters)
		{
			System.out.println();
			System.out.println(c);
		}
		
		
		//test number 7, locateUser
		System.out.println("\n Begin test number 7: locate User \n");
		Cluster rslt = clustering1.locateUser("myUserID1");
		System.out.println("myBusinessID1 is in cluster: " + rslt.clusterId +"\n");
		
		//test number 7.5, status of sentibook
		System.out.println("\n Begin test number 7.5: status of senti book \n");
		System.out.println(clustering1.mySenti);
		
		//test number 8, reweightReviews
		System.out.println("\nBegin test number 8: reweightReviews \n");
		double [][] myMatrix = clustering1.reweightReviews("myUserID2");
		
		for(int i = 0; i < myMatrix.length; i++)
		{
			for(int j = 0; j < myMatrix[0].length; j++)
			{
				System.out.print(myMatrix[i][j] + ", ");
			}
			System.out.println();
		}
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	
	public HashMap<String, ArrayList<Double>> sentiBook;
	private static String delimiter = ":.,' ?!\n\t";
	
	public SentimentBook() {
		// Store word Average Rating (0) and Frequency (1)
		sentiBook = new HashMap<String, ArrayList<Double>>();
	}
	
	public void addReviews(Review[] reviews) {
		for(Review review : reviews) {addReview(review);}
	}
	
	public void addReview(Review review) {
		String text = review.text;
		double rating = review.rating;
		StringTokenizer tkn = new StringTokenizer(text, delimiter);
		
		while(tkn.hasMoreTokens()) {
			String word = tkn.nextToken().toLowerCase();
			
			// Ignore small words
			if(word.length() > 3) {
				ArrayList<Double> value = sentiBook.get(word);
				
				if(value == null) {
					// Word not in SentiBook
					value = new ArrayList<Double>();
					value.add(0, 1.0);
					value.add(1, rating);
				}
				else {
					Double frequency = value.get(0);
					Double avgRating = value.get(1);
					
					// Compute new average
					Double newAvg = ((frequency * avgRating + rating) / (frequency+1));
					
					value.set(0, frequency + 1);
					value.set(1, newAvg);	
				}
				
				sentiBook.put(word, value);
			}
		}
	}
	
	// Re-Score Review based on SentiBook
	public void reweightReviews(Review[] reviews, double d) {	
		for(Review review : reviews) {
			StringTokenizer tkn = new StringTokenizer(review.text, delimiter);
			int num_words = 0;
			double total_score = 0.0;
			
			while(tkn.hasMoreTokens()) {
				ArrayList<Double> values = sentiBook.get(tkn.nextToken());
				if(values != null) {
					total_score = total_score + values.get(1);
					num_words = num_words + 1;
				}
			}
			
			if(num_words != 0) {total_score = total_score / num_words;}
			review.rating = (d * review.rating) + ((1-d) * total_score);
		}
	}
			
	public String toString() {
		String output = "";
		for(Entry<String, ArrayList<Double>> e : sentiBook.entrySet()) {
			output += e.getKey() + " ";
			output +=  e.getValue().get(0) +" ";
			output +=  e.getValue().get(1) + "\n";
		}
		return output;
	}
}
