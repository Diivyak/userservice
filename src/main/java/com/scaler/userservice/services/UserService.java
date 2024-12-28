package com.scaler.userservice.services;

import com.scaler.userservice.models.Token;
import com.scaler.userservice.models.User;
import com.scaler.userservice.repositories.TokenRepository;
import com.scaler.userservice.repositories.UserRepository;
import com.scaler.userservice.util.JwtUtil;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private TokenRepository tokenRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public UserService(UserRepository userRepository,
                       BCryptPasswordEncoder bCryptPasswordEncoder,
                       TokenRepository tokenRepository){
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.tokenRepository = tokenRepository;

    }
    public User signUp(String fullName,
                       String email,
                       String password) {
        User user = new User();
        user.setName(fullName);
        user.setEmail(email);
        user.setHashedPassword(bCryptPasswordEncoder.encode(password));

        userRepository.save(user);
        return user;
    }

    public Token login(String email, String password) {
        Optional<User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            return null;
        }

        User user = userOptional.get();

        if(!bCryptPasswordEncoder.matches(password, user.getHashedPassword())){
            return null;
        }
//        if (isValidUser(user.getName(), user.getHashedPassword())) {
//            return JwtUtil.generateToken(user.getName());
//        } else {
//            throw new RuntimeException("Invalid credentials");
//        }

        Token token = getToken(user);

        // TODO 1: Change the above token to a JWT Token

        Token savedToken = tokenRepository.save(token);

        return savedToken;
    }
    private static Token getToken(User user) {
        LocalDate today = LocalDate.now();
        LocalDate thirtyDaysLater = today.plus(30, ChronoUnit.DAYS);

        // Convert LocalDate to Date
        Date expiryDate = Date.from(thirtyDaysLater.atStartOfDay(ZoneId.systemDefault()).toInstant());

        Token token = new Token();
        token.setUser(user);
        token.setExpiryAt(expiryDate);
        token.setValue(RandomStringUtils.randomAlphanumeric(128));

        return token;
    }
    public void logout(String token) {
        Optional<Token> token1 = tokenRepository.findByValueAndDeletedEquals(token, false);

        if (token1.isEmpty()) {
            // throw TokenNotExistsOrAlreadyExpiredException();
            return;
        }

        Token tkn = token1.get();

        tkn.setDeleted(true);
        tokenRepository.save(tkn);

        return;
    }

    public User validateToken(String token) {
        Optional<Token> tkn = tokenRepository.
                findByValueAndDeletedEqualsAndExpiryAtGreaterThan(token, false, new Date());

        if (tkn.isEmpty()) {
            return null;
        }

        // TODO 2: Instead of validating via the DB, as the token is now a JWT
        // token, validate using JWT

        return tkn.get().getUser();
    }

    private boolean isValidUser(String username, String password) {
        // Implement your user validation logic here
        return "user".equals(username) && "password".equals(password);
    }
}
