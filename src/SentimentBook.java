import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;




//STEP 3


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
		// || pause now write the Clustering Class Code
		
		
		System.out.println("Finished calling main");
	}

}



class Book
{
	private HashMap<String, ArrayList<Double>> sentiBook;
	
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
