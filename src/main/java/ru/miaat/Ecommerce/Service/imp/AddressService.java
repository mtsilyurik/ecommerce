package ru.miaat.Ecommerce.Service.imp;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.miaat.Ecommerce.Dto.AddressDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Address;
import ru.miaat.Ecommerce.Entity.User;
import ru.miaat.Ecommerce.Exception.NotFoundException;
import ru.miaat.Ecommerce.Repository.AddressRepository;

@Service
@RequiredArgsConstructor
public class AddressService implements ru.miaat.Ecommerce.Service.interf.AddressService {

    private final AddressRepository addressRepository;

    private final UserService userService;

    @Override
    public Response saveAndUpdateAddress(AddressDto address, Long id) {
        User user = userService.getLogInUser();
        Address userAddress = user.getAddress();

        if(userAddress == null) {
            userAddress = new Address();
            userAddress.setUser(user);
            user.setAddress(userAddress);
        }

        if(address.getStreet() != null) userAddress.setStreet(address.getStreet());
        if(address.getCity() != null) userAddress.setCity(address.getCity());
        if(address.getState() != null) userAddress.setState(address.getState());
        if(address.getZip() != null) userAddress.setZip(address.getZip());
        if(address.getCountry() != null) userAddress.setCountry(address.getCountry());


        addressRepository.save(userAddress);

        String message = (user.getAddress() == null ? "Address successfully saved" : "Address successfully updated");

        return Response.builder()
                .status(200)
                .message(message)
                .build();
    }


    @Override
    public Response deleteAddress(Long id) {
        return null;
    }
}
