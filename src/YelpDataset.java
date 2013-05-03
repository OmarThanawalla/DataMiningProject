<<<<<<< HEAD
=======
import java.util.ArrayList;

>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
// Yelp Review Structure
class YelpReview {
	public String review_id;
	public String user_id;
	public String business_id;
	public Double stars;
	public String text;

    public String toString() {
        String result = new String();
        result += "Review: " + review_id + "\n";
        result += "User: " + user_id + "\n";
        result += "Business: " + business_id + "\n";
        result += "Rating: " + stars + "\n";
        result += "Text: " + text + "\n";
        return result;
    }
}

// Yelp Businesses Structure
class YelpBusiness {
	public String business_id;
<<<<<<< HEAD
	public String name;
	public String city;
	public Double stars;
=======
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
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	
	public String toString() {
        String result = new String();
        result += "Business: " + business_id + "\n";
        result += "Name: " + name + "\n";
<<<<<<< HEAD
        result += "City: " + city + "\n";
=======
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
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
        result += "Stars: " + stars + "\n";
        return result;
    }
}