package portfolio.server.database;

import portfolio.server.model.Post;

import java.sql.*;
import java.util.ArrayList;

public class PostRepository {
    Connection conn = StartConnection.getConnection();
    public int postsPages() throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM posts";
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()) return resultSet.getInt("count");

        return 0;
    }
    public ArrayList<Post> allPosts(int page) throws SQLException {
        if(page == 1) page = 0;
        page = page*10;
        String sql = "SELECT * FROM posts LIMIT 20 OFFSET "+page;
        PreparedStatement statement = conn.prepareStatement(sql);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Post> posts = new ArrayList<>();

        while(resultSet.next()){
            posts.add(new Post(
                    resultSet.getInt("id"),
                    resultSet.getString("image"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("createdAt")
            ));
        }
        return posts;
    }

    public ArrayList<Post> userPosts(int userID) throws SQLException {
        String sql = "SELECT * FROM posts WHERE userID = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, userID);
        ResultSet resultSet = statement.executeQuery();
        ArrayList<Post> posts = new ArrayList<>();

        while(resultSet.next()){
            posts.add(new Post(
                    resultSet.getInt("id"),
                    resultSet.getString("image"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("createdAt")
            ));
        }
        return posts;
    }

    public Post onePost(int id) throws SQLException {
        String sql = "SELECT * FROM posts WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        ResultSet resultSet = statement.executeQuery();

        if(resultSet.next()){
            return new Post(
                    resultSet.getInt("id"),
                    resultSet.getString("image"),
                    resultSet.getString("title"),
                    resultSet.getString("description"),
                    resultSet.getTimestamp("createdAt")
            );
        }
        return null;
    }

    public int savePost(Post post, int userID) throws SQLException{
        String sql = "INSERT INTO posts(title, description, userID) VALUES (?,?,?)";
        PreparedStatement statement = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
        statement.setString(1, post.getTitle());
        statement.setString(2, post.getDescription());
        statement.setInt(3, userID);

        statement.executeUpdate();

        final ResultSet generatedKeys = statement.getGeneratedKeys();
        if(generatedKeys.next()){
            return generatedKeys.getInt(1);
        }
        return -1;
    }
    public void editPost(Post post, int id) throws SQLException {
        String sql = "UPDATE posts SET image = ?, title = ?, description = ? WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setString(1, post.getImage());
        statement.setString(2, post.getTitle());
        statement.setString(3, post.getDescription());
        statement.setInt(4, id);
        statement.executeUpdate();
    }

    public void deletePost(int id) throws SQLException {
        String sql = "DELETE FROM posts WHERE id = ?";
        PreparedStatement statement = conn.prepareStatement(sql);
        statement.setInt(1, id);
        statement.executeUpdate();
    }
}