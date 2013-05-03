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
	public String name;
	public String city;
	public Double stars;
	
	public String toString() {
        String result = new String();
        result += "Business: " + business_id + "\n";
        result += "Name: " + name + "\n";
        result += "City: " + city + "\n";
        result += "Stars: " + stars + "\n";
        return result;
    }
}