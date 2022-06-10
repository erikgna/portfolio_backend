package portfolio.server.database;

import java.sql.Connection;
import java.sql.DriverManager;

public class StartConnection {
    public static Connection getConnection() {
        String user = "admin";
        String password = "Erik2202*";

        Connection connection = null;

        try{
            connection = DriverManager.getConnection("jdbc:mysql://portfolio-database.ck2lneoorlhn.us-east-1.rds.amazonaws.com/portfolio", user, password);
            System.out.println("SUCESSO ao se conectar ao banco MySQL!");
        } catch (Exception e) {
            System.out.println("FALHA ao se conectar ao banco MySQL!");
            e.printStackTrace();
        }
        return connection;
    }
}