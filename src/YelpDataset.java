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
