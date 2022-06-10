package portfolio.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import portfolio.server.model.User;
import portfolio.server.service.UserService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.util.Objects;

@RestController
public class UserController {
    @Autowired
    private UserService userService;
    private final String url = "https://erikna.com";

    @CrossOrigin(origins = url)
    @PostMapping("/user/login")
    @ResponseBody
    public ResponseEntity<String> findOneUser(HttpServletResponse response, @RequestBody User user) {
        try {
            final String serviceAnswer = userService.login(user.getEmail(), user.getPassword());

            if(Objects.equals(serviceAnswer, "406")) return ResponseEntity.status(406).body("Email or password not matching");
            if(Objects.equals(serviceAnswer, "400")) return ResponseEntity.status(406).body("Email invalid");

            return ResponseEntity.status(200).body(serviceAnswer);
        } catch (Exception e){
            return ResponseEntity.status(500).body("Error recovering the user.");
        }
    }

    @CrossOrigin(origins = url)
    @PostMapping("/user/register")
    @ResponseBody
    public ResponseEntity<String> createUser(@RequestBody User user){
        try{
            final int serviceAnswer = userService.createUser(user);

            if(serviceAnswer == 400) return ResponseEntity.status(serviceAnswer).body("Passwords doesn't match.");
            if(serviceAnswer == 406) return ResponseEntity.status(serviceAnswer).body("Credentials too short;");


            return ResponseEntity.status(serviceAnswer).body(null);
        }
        catch (Exception e){
            return ResponseEntity.status(500).body(null);
        }
    }
}