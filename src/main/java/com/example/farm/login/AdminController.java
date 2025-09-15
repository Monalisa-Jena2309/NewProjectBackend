package com.example.farm.login;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "http://localhost:4200")
public class AdminController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminController(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // ✅ Create new user (by admin)
    @PostMapping("/users")
    public ResponseEntity<?> createUser(@RequestBody User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email is already taken");
        }

        // Encode password before saving
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        User savedUser = userRepository.save(user);
        return ResponseEntity.ok(savedUser);
    }

    // ✅ Get all users
    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

    // ✅ Get single user by ID
    @GetMapping("/users/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id) {
        Optional<User> user = userRepository.findById(id);
        return user.isPresent()
                ? ResponseEntity.ok(user.get())
                : ResponseEntity.notFound().build();
    }

    // ✅ Update user (username, email, role)
    @PutMapping("/users/{id}")
    public ResponseEntity<?> updateUser(
            @PathVariable Long id,
            @RequestBody User updatedUser) {

        Optional<User> dbUserOpt = userRepository.findById(id);
        if (dbUserOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User dbUser = dbUserOpt.get();
        dbUser.setUsername(updatedUser.getUsername());
        dbUser.setEmail(updatedUser.getEmail());
        dbUser.setRole(updatedUser.getRole());

        userRepository.save(dbUser);
        return ResponseEntity.ok(dbUser);
    }

    // ✅ Change only role
    @PatchMapping("/users/{id}/role")
    public ResponseEntity<?> changeUserRole(
            @PathVariable Long id,
            @RequestBody RoleChangeRequest roleRequest) {

        Optional<User> dbUserOpt = userRepository.findById(id);
        if (dbUserOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User dbUser = dbUserOpt.get();
        dbUser.setRole(roleRequest.getRole());

        userRepository.save(dbUser);
        return ResponseEntity.ok(dbUser);
    }

    // ✅ Delete user
    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.ok().body("User deleted successfully");
    }
}
