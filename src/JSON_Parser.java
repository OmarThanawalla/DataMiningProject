import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

// Yelp Data JSON Parser
public class JSON_Parser {
	
    private static String file_path1 = "C:\\Users\\Slav\\Desktop\\DataMining\\DataMiningProject\\src\\yelp_academic_dataset_review.json";
    private static String file_path2 = "C:\\Users\\Slav\\Desktop\\DataMining\\DataMiningProject\\src\\yelp_academic_dataset_business.json";
    private static int limit = 15000;
    
	// Yelp Review Parsing
    public static ArrayList<YelpReview> parseYelpReviews() throws IOException {
    	File file = new File(file_path1);
    	FileReader fr = new FileReader(file);
    	BufferedReader bfr = new BufferedReader(fr);
    	
    	ArrayList<YelpReview> data = new ArrayList<YelpReview>();  
    	String json = new String();
    	int record_number = 0;
    	while ((json = bfr.readLine()) != null && (record_number < limit)) {
    		// Parse the JSON review
    		YelpReview review = new Gson().fromJson(json, YelpReview.class);
    		data.add(review); ++record_number;
    	}
        
        bfr.close(); fr.close();
        return data;
    }
    
    // Yelp Business Parsing
    public static ArrayList<YelpBusiness> parseYelpBusinesses() throws IOException {
    	File file = new File(file_path2);
    	FileReader fr = new FileReader(file);
    	BufferedReader bfr = new BufferedReader(fr);
    	
    	ArrayList<YelpBusiness> data = new ArrayList<YelpBusiness>();  
    	String json = new String();
    	while ((json = bfr.readLine()) != null) {
    		// Parse the JSON business
    		YelpBusiness business = new Gson().fromJson(json, YelpBusiness.class);
    		data.add(business);
    	}
        
        bfr.close(); fr.close();
        return data;
    }
    
    // For Testing
    public static void main(String[] args) throws Exception {  
    	//ArrayList<YelpReview> reviews = parseYelpReviews();
    	//System.out.println("Number Reviews: " + reviews.size() + "\n");
    	//for(YelpReview review : reviews) System.out.println(review);
    	
    	//ArrayList<YelpBusiness> businesses = parseYelpBusinesses();
    	//System.out.println("Number Reviews: " + businesses.size() + "\n");
    	//for(YelpBusiness business : businesses) System.out.println(business);
    }
}