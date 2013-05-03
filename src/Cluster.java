import java.util.HashMap;

// Cluster of Users 
public class Cluster {
	
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
	
	public int getID() {return clusterID;}
	public double[] getCenter() {return center;}
	public int size() {return users.size();}
	
	public String toString() {
		String output = new String(); 
		output += "Cluster Numer: " + clusterID + "\nCenter: ";
		for(int i = 0; i < center.length; ++i) {output += center[i] + " ";}
		return output + "\n";			
	}
}
