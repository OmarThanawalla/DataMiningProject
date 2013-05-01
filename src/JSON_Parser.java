import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

public class JSON_Parser {
    
    public static ArrayList<Reviews> parseYelpData() throws IOException{
    	File file = new File("/Users/omarthanawalla/Documents/workspace/SentimentBook/src/yelp_academic_dataset_review.json");
    	FileReader fr = new FileReader(file);
    	BufferedReader bfr = new BufferedReader(fr);
    	
    	ArrayList<Reviews> data = new ArrayList<Reviews>();  
    	String json = new String();
    	int i = 0;
    	while ((json = bfr.readLine()) != null && (i < 500)) {
    		// Parse the JSON review
    		
    		YelpReview yelp_rev = new Gson().fromJson(json, YelpReview.class);
    		System.out.println(i++);
    		data.add(new Reviews(yelp_rev));
    	}
        
        bfr.close();
        fr.close();
        return data;
    }
    
   /* public static void main(String[] args) throws Exception {  
    	ArrayList<Reviews> reviews = parseYelpData();
    	//System.out.println("Number Reviews: " + reviews.size() + "\n");
    	//for(Reviews review : reviews) {
    	//	System.out.println(review);
    	//}
    }*/
}