package ru.miaat.Ecommerce.Service.interf;

import ru.miaat.Ecommerce.Dto.AddressDto;
import ru.miaat.Ecommerce.Dto.Response;

public interface AddressService {
    Response saveAndUpdateAddress(AddressDto address, Long id);

    Response deleteAddress(Long id);
}
