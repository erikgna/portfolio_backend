package portfolio.server.service;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import portfolio.server.database.UserRepository;
import portfolio.server.model.User;
import portfolio.server.utils.Password;
import portfolio.server.utils.Token;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.Objects;

@Service

public class UserService {
    private final UserRepository userRepository = new UserRepository();
    public String login(String email, String password) throws SQLException {
        if(email.length() < 6 || password.length() < 6) return "400";

        final User dbUser = userRepository.oneUser(email);
        if(dbUser == null) return "406";

        if(Password.passwordsMatches(password, dbUser.getPassword())){
            JSONObject jsonToken = new JSONObject();
            jsonToken.put("userID", dbUser.getId());
            jsonToken.put("email", dbUser.getEmail());
            jsonToken.put("name", dbUser.getName());
            final String token = Token.getToken(jsonToken.toString());
            jsonToken.put("Authorization", token);
            jsonToken.remove("email");
            dbUser.setAccessToken(token);
            userRepository.editUser(dbUser);
            return jsonToken.toString();
        }
        return "406";
    }

    public int createUser(User user) throws SQLException {
        if(user.getEmail().length() <= 6
                || user.getPassword().length() < 6
                || user.getConfirmPassword().length() < 6) return 406;

        if(Objects.equals(user.getPassword(), user.getConfirmPassword())){
            user.setPassword(Password.getHashedPassword(user.getPassword()));
            userRepository.createUser(user);

            return 200;
        }

        return 400;
    }

    public void editUser(User user) throws NoSuchAlgorithmException, SQLException {
        final String hashedPassword = Password.getHashedPassword(user.getPassword());
        final User oldUser = userRepository.oneUser(user.getEmail());
        if (Objects.equals(hashedPassword, oldUser.getPassword())) {
            userRepository.editUser(user);
        }
    }
}