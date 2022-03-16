package UML.Diagrammer.desktop;

import org.javalite.activejdbc.Base;

public class DatabaseConnection {

    public void openConnection(){
        String databaseURL = "jdbc:mysql://ls-a9db0e6496e5430883b43e690a26b7676cf9d7af.cuirr4jp1g1o.us-west-2.rds.amazonaws.com/test?useSSL=false";
        String databaseUser = "root";
        String databasePassword = "TeamOverZero";

        Base.open("com.mysql.cj.jdbc.Driver", databaseURL, databaseUser, databasePassword);
    }
    
    public void closeConnection(){
        Base.close();
    }
}
