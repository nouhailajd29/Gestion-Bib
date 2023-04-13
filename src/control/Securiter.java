package control;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

import Utility.Connect;

public class Securiter {
 
	public boolean authenticate(String login,String mdps) {
		try {
			String query="SELECT `Login`, `password` FROM `login` WHERE login=? and password=?;";
			PreparedStatement statement =Connect.getConnect().getCon().prepareStatement(query);
			statement.setString(1,login);
			statement.setString(2, mdps);
			ResultSet rs=statement.executeQuery();
			if(rs.next())
				return true;
			return false;
		
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e.getMessage());
			
			return false;
		}
		
	}
}
