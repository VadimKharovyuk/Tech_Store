package com.example.webservice.service;

import com.example.webservice.dto.UserDTO;
import com.example.webservice.repository.UserFeignClient;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private UserFeignClient userFeignClient;

    public List<UserDTO> userDTOList(){
        return userFeignClient.getAllUsers();
    }

    public UserDTO getUserRole(String username) {
        return userFeignClient.getUserByUsername(username);
    }
}
