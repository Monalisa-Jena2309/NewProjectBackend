package com.example.farm.login;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;

    // ✅ Update Profile
    @PutMapping("/update-profile/{username}")
    public ResponseEntity<String> updateProfile(
            @PathVariable String username,
            @RequestBody UpdateProfileRequest updateProfileRequest) {

        userService.updateProfile(username, updateProfileRequest);
        return ResponseEntity.ok("Profile updated successfully!");
    }

    // ✅ Update Password
    @PutMapping("/update-password/{username}")
    public ResponseEntity<String> updatePassword(
            @PathVariable String username,
            @RequestBody UpdatePasswordRequest updatePasswordRequest) {

        userService.updatePassword(username, updatePasswordRequest);
        return ResponseEntity.ok("Password updated successfully!");
    }

    // ✅ Get user by username
    @GetMapping("/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User user = userService.getProfile(username); // ✅ FIXED
        return ResponseEntity.ok(user);
    }
}
