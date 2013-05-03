import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.StringTokenizer;

// Dictionary for Semantic Analysis
class SentimentBook {
	
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
