package ru.miaat.Ecommerce.Service.interf;

import org.springframework.data.domain.Page;
import ru.miaat.Ecommerce.Dto.LoginRequest;
import ru.miaat.Ecommerce.Dto.ProductDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Dto.UserDto;
import ru.miaat.Ecommerce.Entity.User;

public interface UserService {
    Response registerUser(UserDto user);
    Response loginUser(LoginRequest loginRequest);
    Response getAllUsers();
    User getLogInUser();
    Response getUserInfoAndOrderHistory();
}
