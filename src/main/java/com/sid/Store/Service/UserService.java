package com.sid.Store.Service;
import com.sid.Store.ExceptionHandler.InvalidPasswordException;
import com.sid.Store.ExceptionHandler.UserAlreadyExistsException;
import com.sid.Store.ExceptionHandler.UserNotFoundException;
import com.sid.Store.Security.JwtUtil;
import com.sid.Store.dto.AuthResponse;
import com.sid.Store.dto.UserDTO;
import com.sid.Store.dto.UserData;
import com.sid.Store.entities.User;
import com.sid.Store.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @Autowired
    private UsersRepository repository;
    public User registerUser(UserData data) {
        if (repository.findByEmail(data.getEmail()).isPresent()) {
            System.out.println("User Already Exists ");
            throw new UserAlreadyExistsException("Email already registered");        }

        long userId = Math.abs(java.util.UUID.randomUUID().getMostSignificantBits());

        User user = new User();
        user.setEmail(data.getEmail());
        user.setName(data.getName());
        user.setPassword(passwordEncoder.encode(data.getPassword()));
        user.setId(userId);
        user.setUserName(user.getName());

        return repository.save(user);
    }


    @Autowired
    private JwtUtil jwtUtil;

    public AuthResponse loginUser(UserData data) {

        User user = repository.findByEmail(data.getEmail())
                .orElseThrow(() -> new UserNotFoundException("UserNotFound"));

        if (!passwordEncoder.matches(data.getPassword(), user.getPassword())) {
            throw new InvalidPasswordException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail());

        return new AuthResponse(token, new UserDTO(user)); //
    }

}