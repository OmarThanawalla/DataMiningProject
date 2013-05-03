import java.util.HashMap;
import java.util.Set;

//What does this class do?
//Represents a cluster of users 
//has a center
public class Cluster {
	
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
		boolean sameCenter = true;
		double[] newCenter = new double[UserVector.globalNumberOfRest];
		for(UserVector uv : users.values())
		{
			for(int i = 0; i < UserVector.globalNumberOfRest; i++ )
			{
				newCenter[i] += uv.getReview(i);
				
				//System.out.println(newCenter[1]);
			}
		}
		for(int i = 0; i < UserVector.globalNumberOfRest; i++)
		{
			if(users.size() != 0)
			{
				newCenter[i] /= users.size();
			}
		    if(center[i] != newCenter[i]) 
		    {
		    	sameCenter = false;
		    }
		    center[i] = newCenter[i];
		}
		return sameCenter;
	}
	
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
	}

}
