package ru.miaat.Ecommerce.Controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import ru.miaat.Ecommerce.Dto.AddressDto;
import ru.miaat.Ecommerce.Dto.Response;
import ru.miaat.Ecommerce.Entity.Address;
import ru.miaat.Ecommerce.Service.interf.AddressService;

@RestController
@RequestMapping("/address")
@RequiredArgsConstructor
public class AddressController {

    private final AddressService addressService;

    @PostMapping("/save")
    public ResponseEntity<Response> saveAndUpdateAddress(@RequestBody AddressDto addressDto, @RequestParam(required = false) Long id) {
        return ResponseEntity.ok(addressService.saveAndUpdateAddress(addressDto, id));
    }

}
