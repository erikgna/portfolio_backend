package portfolio.server.database;

import portfolio.server.model.User;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserRepository {
    Connection conn = StartConnection.getConnection();
    public User oneUser(String email) throws SQLException {
        String sql = "SELECT * FROM users WHERE email = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, email);
        ResultSet resultSet = statement.executeQuery();

        User user = new User();

        if(resultSet.next()){
            user.setId(resultSet.getLong("userID"));
            user.setName(resultSet.getString("name"));
            user.setEmail(resultSet.getString("email"));
            user.setPassword(resultSet.getString("password"));
            user.setAccessToken(resultSet.getString("access_token"));
        }
        return user;
    }

    public void createUser(User user) throws SQLException {
        String sql = "INSERT INTO users(name, email, password) VALUES (?,?,?)";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getEmail());
        statement.setString(3, user.getPassword());

        statement.executeUpdate();
    }

    public void editUser(User user) throws SQLException {
        String sql = "UPDATE users SET name = ?, access_token = ? WHERE email = ?";

        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, user.getName());
        statement.setString(2, user.getAccessToken());
        statement.setString(3, user.getEmail());

        statement.executeUpdate();
    }
}