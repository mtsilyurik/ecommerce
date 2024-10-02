package ru.miaat.Ecommerce.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.miaat.Ecommerce.Entity.Address;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
