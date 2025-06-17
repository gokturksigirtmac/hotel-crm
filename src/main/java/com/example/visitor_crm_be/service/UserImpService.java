package com.example.visitor_crm_be.service;

import com.example.visitor_crm_be.dto.UserCreateDTO;
import com.example.visitor_crm_be.dto.UserResponseDTO;
import com.example.visitor_crm_be.error.UserAlreadyExistsException;
import com.example.visitor_crm_be.model.User;
import com.example.visitor_crm_be.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserImpService implements UserDetailsService, UserService {

    @Autowired
    private UserRepository userRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByEmail(username).orElseThrow(()->new RuntimeException("User not found"));
        return user;
    }

    public List<UserResponseDTO> getAllUser() {
        List<User> userEnitiys = userRepo.findAll();
        List<UserResponseDTO> userResponseDtoList = userEnitiys.stream().map(user->this.userEntityToUserRespDto(user)).collect(Collectors.toList());
        return userResponseDtoList;
    }

    public UserResponseDTO createUser(UserCreateDTO userCreateDTO) {
        Optional<User> foundUser = this.userRepo.findByEmail(userCreateDTO.getEmail());
        if (foundUser.isEmpty()) {
            User user = this.userReqDtoToUserEntity(userCreateDTO);
            user.setPassword(user.getPassword());
            User createdUser = userRepo.save(user);
            return this.userEntityToUserRespDto(createdUser);
        } else {
            // User already exists, throw an exception
            throw new UserAlreadyExistsException("User with email " + userCreateDTO.getEmail() + " already exists");
        }
    }

    public User userReqDtoToUserEntity(UserCreateDTO userReqDto){
        User user = this.modelMapper.map(userReqDto, User.class);
        return user;
    }
    public UserResponseDTO userEntityToUserRespDto(User user){
        UserResponseDTO userRespDto = this.modelMapper.map(user,UserResponseDTO.class);
        return userRespDto;
    }
}
