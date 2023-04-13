package Utility;

import java.sql.Connection;
import java.sql.DriverManager;

public class Connect {
 private  Connection con;
 private static Connect c;
 
 private Connect() {
	 try {
		con=DriverManager.getConnection("jdbc:mysql://localhost:3306/g.bibliotheque","root","");
	} catch (Exception e) {
		// TODO: handle exception
	}
 }
 public static Connect getConnect() {
	 if(c==null)
		 c=new Connect();
	 
	 return c;
 }
 public  Connection getCon() {
	 return con;
 }
 
}
