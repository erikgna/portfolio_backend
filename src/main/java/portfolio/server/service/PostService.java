package portfolio.server.service;

import org.springframework.stereotype.Service;
import portfolio.server.database.PostRepository;
import portfolio.server.model.Post;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Objects;

@Service
public class PostService{
    private final PostRepository postRepository = new PostRepository();

    public int postsPages() throws SQLException {
        return postRepository.postsPages();
    }
    public ArrayList<Post> allPosts(int page) throws SQLException {
        return postRepository.allPosts(page);
    }

    public ArrayList<Post> userPosts(int userID) throws SQLException {
        return postRepository.userPosts(userID);
    }

    public Post onePost(int id) throws SQLException {
        return postRepository.onePost(id);
    }

    public int savePost(Post post, int userID) throws SQLException {
        return postRepository.savePost(post, userID);
    }

    public int editPost(Post post, int id) throws SQLException {
        Post oldPost = postRepository.onePost(id);

        if(oldPost == null) return 406;
        if(Objects.equals(post.getTitle(), oldPost.getTitle()) && Objects.equals(post.getDescription(), oldPost.getDescription())) return 400;

        oldPost.setTitle(post.getTitle());
        oldPost.setDescription(post.getDescription());

        postRepository.editPost(oldPost, id);
        return 200;
    }

    public int deletePost(int id) throws SQLException {
        if(postRepository.onePost(id) == null) return 406;
        postRepository.deletePost(id);
        return 200;
    }
}
