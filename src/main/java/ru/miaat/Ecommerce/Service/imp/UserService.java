package ru.miaat.Ecommerce.Service.imp;

import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Dto.LoginRequest;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Dto.UserDto;
import ru.miaat.Ecommerce.Entity.User;
import ru.miaat.Ecommerce.Enum.UserRole;
import ru.miaat.Ecommerce.Exception.InvalidCredentialsException;
import ru.miaat.Ecommerce.Exception.NotFoundException;
import ru.miaat.Ecommerce.Mapper.EntityDtoMapper;
import ru.miaat.Ecommerce.Repository.UserRepository;
import ru.miaat.Ecommerce.Security.JWTUtils;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements ru.miaat.Ecommerce.Service.interf.UserService {


    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;

    private final JWTUtils jwtUtils;

    private final EntityDtoMapper entityDtoMapper;

    @Override
    public Response registerUser(UserDto u) {
        UserRole role = UserRole.USER;
        if (u.getRole() != null && u.getRole().equalsIgnoreCase("admin")) {
            role = UserRole.ADMIN;
        }

        User user = User.builder()
                .email(u.getEmail())
                .name(u.getName())
                .password(passwordEncoder.encode(u.getPassword()))
                .password(u.getPassword())
                .role(role)
                .build();
        User savedUser = userRepository.save(user);
        UserDto userDto = entityDtoMapper.mapUserToUserDto(savedUser);
        return Response.builder()
                .status(200)
                .message("Success")
                .user(userDto)
                .build();
    }

    @Override
    public Response loginUser(LoginRequest loginRequest) {

        User user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                () ->new NotFoundException("User not found")
        );

        if(!passwordEncoder.matches(loginRequest.getPassword(), user.getPassword())) {
            throw new InvalidCredentialsException("Password does not match");
        }

        String token = jwtUtils.createToken(user);

        return Response.builder()
                .status(200)
                .message("Success")
                .token(token)
                .expiration("4 months")
                .role(user.getRole().name())
                .build();
    }

    @Override
    public Response getAllUsers() {
        List<UserDto> users = userRepository.findAll().stream()
                .map(entityDtoMapper::mapUserToUserDto)
                .toList();

        return Response.builder()
                .status(200)
                .message("Success")
                .userList(users)
                .build();
    }

    @Override
    public User getLogInUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        log.info("Username : {}", username);
        return userRepository.findByEmail(username).orElseThrow(
                () -> new NotFoundException("User not found")
        );
    }

    @Override
    public Response getUserInfoAndOrderHistory(User user) {

        UserDto userDto = entityDtoMapper.mapUserToUserDtoWithAddressAndOrders(getLogInUser());

        return Response.builder()
                .status(200)
                .message("Success")
                .user(userDto)
                .build();

    }
}
