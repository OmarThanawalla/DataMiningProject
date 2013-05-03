import java.util.HashMap;

// Cluster of Users 
public class Cluster {
	
<<<<<<< HEAD
	private HashMap<Integer, UserVector> users;
	private int clusterID;
	private double[] center;
	private static int nextFreeClusterID = 0;
	
	public Cluster(int dimensions) {
		clusterID = nextFreeClusterID++;
		users = new HashMap<Integer,UserVector>();
		center = new double[dimensions];
		
		// Generate Random Coordinates for the Center
		for(int i = 0; i < center.length; ++i) {
			center[i] = 5 * Math.random();
		}
	}
	
	public void addUser(Integer id, UserVector user) {users.put(id, user);}
	public void removeUser(Integer id) {users.remove(id);}
	public HashMap<Integer, UserVector> getUsers() {return users;}
	public void clearUsers() {users.clear();}
	
	// Returns true if Center does not Change 
	public boolean calculateCenter() {
=======
	HashMap<Integer, UserVector> users;
	static int clusterNumber = 0; //test this!
	public int clusterId;
	double[] center;
	
	public Cluster()
	{
		clusterId = clusterNumber;
		clusterNumber++;
		users = new HashMap<Integer,UserVector>();
		center = new double[UserVector.globalNumberOfRest];
		//center[0] = clusterNumber;
		for(int i = 0; i < center.length; ++i)
			center[i] = Math.random() * 5;
	}
	
	public void addUser(Integer id, UserVector aUser)
	{
		users.put(id, aUser);
	}
	
	public void removeUser(Integer id)
	{
		if(users.containsKey(id))
			users.remove(id);
	}
	
	public void empty()
	{
		users.clear();
	}
	
	public boolean calculateCenter()
	{
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
		boolean sameCenter = true;
		double[] newCenter = new double[center.length];
		
		for(UserVector user : users.values()) {
			for(int idx = 0; idx < center.length; ++idx) {
				Double value = user.getReview(idx);
				if(value != null) {newCenter[idx] += value;}
			}
		}
		
		for(int idx = 0; idx < center.length; ++idx) {
			if(users.size() != 0) {newCenter[idx] /= users.size();}
		    if(center[idx] != newCenter[idx]) {sameCenter = false;}
		    center[idx] = newCenter[idx];
		}
		
		return sameCenter;
	}
	
<<<<<<< HEAD
	public int getID() {return clusterID;}
	public double[] getCenter() {return center;}
	public int size() {return users.size();}
	
	public String toString() {
		String output = new String(); 
		output += "Cluster Numer: " + clusterID + "\nCenter: ";
		for(int i = 0; i < center.length; ++i) {output += center[i] + " ";}
		return output + "\n";			
=======
	public double[] getCenter()
	{
		return center;
	}
	
	public int size() {
		return users.size();
	}
	
	public String toString()
	{
		String output = "Cluster Numer: " + clusterId +"\n";
		output+= "Center: ";
		//iterate through center
		for(int i = 0; i < center.length; i++)
		{
			output+= center[i] + ", ";
		}
		output += "\n";
		
		Set<Integer> user_ids = users.keySet();
		
		for(Integer user: user_ids)
		{
			output+= user + ", ";
		}
		return output;			
>>>>>>> f5c79045ed2a01a6fd0501a7f9d2efd5ffdce148
	}
}
