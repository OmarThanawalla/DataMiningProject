import java.util.ArrayList;

// Yelp Review Structure
class YelpReview {
	public YelpVote votes;
	public String user_id;
	public String review_id;
	public Integer stars;
	public String date;
	public String text;
	public String type;
	public String business_id;
	
	class YelpVote {
		public Integer funny;
		public Integer useful;
		public Integer cool;
		
		public String toString() {
			String result = "Votes: ";
			result += "funny: " + funny + ", ";
			result += "useful: " + useful + ", ";
			result += "cool: " + cool + "\n";
			return result;
		}
	}
	
    public String toString() {
        String result = new String();
        result += votes; 
        result += "User: " + user_id + "\n";
        result += "Review: " + review_id + "\n";
        result += "Stars: " + stars + "\n";
        result += "Date: " + date + "\n";
        result += "Text: " + text + "\n";
        result += "Type: " + type + "\n";
        result += "Business: " + business_id + "\n";
        return result;
    }
}

// Yelp Businesses Structure
class YelpBusiness {
	public String business_id;
	public String full_address;
	public Boolean open;
	public ArrayList<String> categories;
	public String city;
	public Integer review_count;
	public String name;
	public ArrayList<String> neighborhoods;
	public Double longitude;
	public String state;
	public Double stars;
	public Double latitude;
	public String type;
	
	public String toString() {
        String result = new String();
        result += "Business: " + business_id + "\n";
        result += "Name: " + name + "\n";
        result += "Type: " + type + "\n";
        result += "Address: " + full_address + "\n";
        result += "is Open: " + open + "\n";  
        result += "Categories: ";
        for(String str : categories) {result += str + " ";}
        result += "\n";
        result += "City: " + city + "\n";
        result += "Neighborhoods: ";
        for(String str : neighborhoods) {result += str + " ";}
        result += "\n";
        result += "Longitude: " + longitude + "\n";
        result += "Latitude: " + latitude + "\n";
        result += "State: " + state + "\n";
        result += "Review Count: " + review_count + "\n";
        result += "Stars: " + stars + "\n";
        return result;
    }
}