package portfolio.server.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Post {
    private int id;
    private String image;
    private String title;
    private String description;
    private Timestamp createdAt;
}