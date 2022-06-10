package portfolio.server.utils;

import org.mindrot.jbcrypt.BCrypt;

public class Password {
    public static String getHashedPassword(String passwordToHash) {
        return BCrypt.hashpw(passwordToHash, BCrypt.gensalt(12));
    }
    public static boolean passwordsMatches(String password, String passwordFromDB){
        return BCrypt.checkpw(password, passwordFromDB);
    }

}