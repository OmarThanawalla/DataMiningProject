import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class PersonalizedReviews {
	
	// Reviews DataSet
	private Review[] reviews = null;
	private static int num_users = 0;
	private static int num_businesses = 0;
	private static int num_reviews = 0;
	
	// Data Mapping
	private HashMap<String, Integer> userToIdx;
	private ArrayList<String> idxToUser;
	private HashMap<String, Integer> businessToIdx;
	private ArrayList<String> idxToBusiness;
	private int nextFreeUserIdx = 0;
	private int nextFreeBusinessIdx = 0;
	
	public static int clustering_coeff = 1;
	double[] yelpRatings;
	
	public static void main(String[] args) throws IOException {
		PersonalizedReviews sys = new PersonalizedReviews();
		System.out.println("Executing PersonalizedReviews...");
		sys.execute();
	}
	
	// Generate Personalized Reviews
	public void execute() throws IOException {
	
		System.out.println("Loading data...");
		ArrayList<YelpReview> yelpReviews = JSON_Parser.parseYelpReviews();
		ArrayList<YelpBusiness> yelpBusinesses = JSON_Parser.parseYelpBusinesses();
		loadData(yelpReviews, yelpBusinesses); // Load Data
		EvaluateModel.loadData(reviews); // For Evaluation
		yelpReviews = null; yelpBusinesses = null; // Free Resources
		System.out.println("Loading data Completed!");
		datasetInfo();
		
		setClusteringCoeff(2);
		System.out.println("Clustering coefficient set to " + clustering_coeff);
		
		System.out.println("Performing Sentiment Analysis...");
		SentimentBook dict = new SentimentBook();
		dict.addReviews(reviews);
		dict.reweightReviews(reviews, 0.5);
		System.out.println("Sentiment Analysis Done!");
		
		System.out.println("Running K-Means clustering...");
		Clustering clustering1 = new Clustering(num_businesses);
		clustering1.loadData(reviews);
		clustering1.performKmeans(clustering_coeff);
		System.out.println("K-Means clustering Completed!");
		
		File file = new File("C:\\Users\\Slav\\Desktop\\Result3.txt");
		if (!file.exists()) file.createNewFile();
		FileWriter fw = new FileWriter(file.getAbsoluteFile());
		BufferedWriter bw = new BufferedWriter(fw);
		System.out.println("Generating Personalized ratings...");
		
		// Personalized Ratings...
		
		System.out.println("Personalized reviews Generated!");
		System.out.println("Calculating Performance Gain...");
		double gain = EvaluateModel.calculateError(clustering1, yelpRatings);
		System.out.println("Gain Factor = " + gain);
	}
	
	public PersonalizedReviews() {
		userToIdx = new HashMap<String, Integer>();
		idxToUser  = new ArrayList<String>();
		businessToIdx = new HashMap<String, Integer>();
		idxToBusiness = new ArrayList<String>();
	}
	
	// Load Yelp Data
	public void loadData(ArrayList<YelpReview> yr, ArrayList<YelpBusiness> yb) {
		
		// Load Yelp Reviews Data
		int yelpReviewSize = yr.size();
		reviews = new Review[yelpReviewSize];
		
		for(int i = 0; i < yelpReviewSize; ++i) {
			YelpReview yelp = yr.get(i);
			
			// Map User_id to Index
			String user_id = yelp.user_id;
			Integer UserID = userToIdx.get(user_id);
			if(UserID == null) {
				UserID = new Integer(nextFreeUserIdx);
				userToIdx.put(user_id, nextFreeUserIdx);
				idxToUser.add(nextFreeUserIdx, user_id);
				++nextFreeUserIdx;
			}
			
			// Map Business_id to Index
			String business_id = yelp.business_id;
			Integer BusinessID = businessToIdx.get(business_id);
			if(BusinessID == null) {
				BusinessID = new Integer(nextFreeBusinessIdx);
				businessToIdx.put(business_id, nextFreeBusinessIdx);
				idxToBusiness.add(nextFreeBusinessIdx, business_id);
				++nextFreeBusinessIdx;
			}
			
			// Add Review
			reviews[i] = new Review(UserID, BusinessID, yelp.stars, yelp.text);
		}
		
		num_reviews = yelpReviewSize;
		num_users = userToIdx.size();
		num_businesses = businessToIdx.size();
		
		// Load Yelp Businesses Data
		yelpRatings = new double[num_businesses];
		for(YelpBusiness business : yb) {
			Integer idx = businessToIdx.get(business.business_id);
			if(idx != null) {yelpRatings[idx] = business.stars;}
		}
	}
	
	// DataSet Details
	public void datasetInfo() {
		String info = "The dataset contains: ";
		info += num_users + " users, ";
		info += num_businesses + " businesses and ";
		info += num_reviews + " reviews\n";
		System.out.print(info);
	}
	
	// Getters
	public static int getNumUsers() {return num_users;}
	public static int getNumBusinesses() {return num_businesses;}
	public static int getNumReviews() {return num_reviews;}
	
	// Specify Clustering Coefficient
	public void setClusteringCoeff(int coeff) {
		clustering_coeff = coeff;
	}
}