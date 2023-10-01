import java.sql.Driver;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.ArrayList;

public class DBManager {
	
	String url="jdbc:mysql://localhost:3306/reactionLeaderboard";
	String username = "root";
	String password = "";
	ArrayList<String> outString = new ArrayList<String>();
	
	public ArrayList<String> getLeaderboard() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			
			Connection connection = DriverManager.getConnection(url, username, password);
			
			Statement statement = connection.createStatement();
			
			ResultSet resultSet=statement.executeQuery("select * from leaderboard order by time ASC");
			
			while (resultSet.next()) {
				outString.add(resultSet.getString(2)+" "+resultSet.getInt(3));
			}
			
			connection.close();
			
			return outString;
		}
		catch (Exception e) {
			System.out.println(e);
			return outString;
		}
	}
	
	public void appendItem(String u, long t) {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection connection = DriverManager.getConnection(url, username, password);
			Statement statement = connection.createStatement();
			System.out.println("INSERT INTO leaderboard (id, user, time) VALUES (4, '"+u+"','"+ t + "'");
			statement.executeUpdate("INSERT INTO leaderboard (id, user, time) VALUES (NULL, '"+u+"','"+ t + "')");
			connection.close();		
		}
		catch (Exception e) {
			System.out.println(e);
		}
	}
	

}
