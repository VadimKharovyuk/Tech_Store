//package com.example.webservice.repository;
//
//import com.example.webservice.config.FeignConfig;
//import com.example.webservice.dto.UserDTO;
//import org.springframework.cloud.openfeign.FeignClient;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@FeignClient(name = "SECURITYSERVIS", configuration = FeignConfig.class)
//public interface UserFeignClient {
//
//
//    @GetMapping("/api/users")
//    List<UserDTO> getAllUsers();
//
//    @PostMapping("/api/users/register")
//    UserDTO registerUser(@RequestBody UserDTO userDTO);
//
//    @PutMapping("/api/users/change-password")
//    String changePassword(
//            @RequestParam String username,
//            @RequestParam String currentPassword,
//            @RequestParam String newPassword);
//
//    @PostMapping("/api/users/block/{userId}")
//    String blockUser(@PathVariable Long userId);
//
//    @PostMapping("/api/users/unblock/{userId}")
//    String unblockUser(@PathVariable Long userId);
//
//    @GetMapping("/api/users/is-blocked")
//    Boolean isBlocked(@RequestParam String username);
//
//    @PostMapping("/api/users/login")
//    ResponseEntity<UserDTO> login(@RequestParam String username, @RequestParam String password);
//
//
//    @PostMapping("/api/password/reset-password")
//    ResponseEntity<String> sendEmailPassword(@RequestParam String email, String newPassword);
//
//
//}
//
//

package com.example.webservice.repository;

import com.example.webservice.config.FeignConfig;
import com.example.webservice.dto.UserDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "SECURITY", configuration = FeignConfig.class)
public interface UserFeignClient {

    @GetMapping("/api/users")
    List<UserDTO> getAllUsers();

    @PostMapping("/api/users/register")
    UserDTO registerUser(@RequestBody UserDTO userDTO);

    @PutMapping("/api/users/change-password")
    String changePassword(
            @RequestParam String username,
            @RequestParam String currentPassword,
            @RequestParam String newPassword);

    @PostMapping("/api/users/block/{userId}")
    String blockUser(@PathVariable Long userId);

    @PostMapping("/api/users/unblock/{userId}")
    String unblockUser(@PathVariable Long userId);

    @GetMapping("/api/users/is-blocked")
    Boolean isBlocked(@RequestParam String username);

    @PostMapping("/api/users/login")
    ResponseEntity<UserDTO> login(@RequestParam String username,
                                  @RequestParam String password);

    @PostMapping("/api/password/reset")
    ResponseEntity<String> sendEmailPassword(
            @RequestParam String email,
            @RequestParam String newPassword);

    @DeleteMapping("/api/users/delete/{id}")
    ResponseEntity<Void>deleteUserById(@PathVariable long id);

    @GetMapping("/api/users/{username}")
    UserDTO getUserByUsername(@PathVariable String username);
}
