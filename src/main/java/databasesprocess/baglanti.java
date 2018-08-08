package databasesprocess;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class baglanti {
    Connection con = null;
    PreparedStatement ps;
    ResultSet rs;
    
    /*
    private final String url = "jdbc:mysql://localhost:3306/";
    private final String driver = "com.mysql.jdbc.Driver";
    private final String db_name = "espark";
    private final String user_name = "root";
    private final String password = "2472";
    
    public void baglan()
    {
        try
        {
            Class.forName(driver).newInstance();
            con = DriverManager.getConnection(url+db_name,user_name,password);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
    }
    */
    public void baglan() {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            String query = "jdbc:sqlserver://ASUS-PC\\SQLSERVER;databaseName=Espark1;user=sa;password=2472;sendTimeAsDateTime=false";
            //String query = "jdbc:sqlserver://ASUS-PC\\SQLSERVER;databaseName=Espark1;integratedSecurity=true";
            con = DriverManager.getConnection(query);
        }
        catch(ClassNotFoundException | SQLException e)
        {
            System.out.println(e.getMessage());
        }
    }
}