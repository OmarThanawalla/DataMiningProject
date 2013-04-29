import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;




//STEP 3
//What does this class do?
//Represents the senti book object


public class SentimentBook {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
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
		
		Reviews [] someReviews = new Reviews[3];
		someReviews[0] = myReview;
		someReviews[1] = yourReview;
		someReviews[2] = theirReview;
		
		//test number 5, loadData into Clustering object
		System.out.println("\n Begin test number 5 \n");
		/*||pause just created someReview array and put a review object in there
		 * now create Clustering object and pass in someReviews to the loadData method		 * 
		 * */
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
		
		clustering1.performKmeans(2);
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
		
		System.out.println("Finished calling main");
	}

}



class Book
{
	public HashMap<String, ArrayList<Double>> sentiBook;
	
	//initialize the sentiBook
	public Book()
	{
		//initialize sentibook 
		sentiBook = new HashMap<String,ArrayList<Double>>();
		
	}
	
	//pass in a single review
	public void addReview(String review, double rating)
	{
		//All symbols we want to exclude
		String delimiters = ":.,' ?!\n\t";
		//create StringTokenizer
		StringTokenizer tkn = new StringTokenizer(review,delimiters);
		
		//iterate through the tokens
		while(tkn.hasMoreTokens())
		{
			String word = tkn.nextToken().toLowerCase();
			if(word.length() > 3)
			{
				//check if the token(word) is already in sentiBook
				if(!sentiBook.containsKey(word))
				{
					//create arrayList
					ArrayList<Double> value = new ArrayList<Double>();
					//put 1 in index 0
					value.add(0, 1.0);
					//put the rating in index 1
					value.add(1, rating);
					//append value inside sentiBook
					sentiBook.put(word,value);
				}
				else//the word is already in sentibook
				{
					ArrayList<Double> value = sentiBook.get(word);
					Double frequency = value.get(0);
					Double avgRating = value.get(1);
					
					//Compute new average
					Double newAvg = ((frequency * avgRating + rating) / (frequency+1) );
					//update frequency and the new average
					value.set(0, frequency+1);
					value.set(1, newAvg);	
					//put the value back in for the word
					sentiBook.put(word,value);
				}
			}
		}
	}
	
	
	
	//show a representation of sentibook
	public String toString()
	{
		String output = "";
		//iterate through keys in sentibook
		for( Entry<String, ArrayList<Double>> entry:sentiBook.entrySet())
		{
			//print out the key
			output += entry.getKey() + " ";
			//print out the value(array) at that key
			output +=  entry.getValue().get(0) +" ";
			output +=  entry.getValue().get(1) + "\n";
		}
		return output;
	}
	
	
	
	
	
	
	
	
}
