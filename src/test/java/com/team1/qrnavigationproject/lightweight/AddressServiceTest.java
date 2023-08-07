package com.team1.qrnavigationproject.lightweight;

import com.team1.qrnavigationproject.model.Address;
import com.team1.qrnavigationproject.repository.AddressRepo;
import com.team1.qrnavigationproject.service.AddressServiceImpl;
import com.team1.qrnavigationproject.stub.TestData;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class AddressServiceTest {

    @Mock
    private AddressRepo addressRepo;

    @InjectMocks
    private AddressServiceImpl addressService;

    private Address address;

    @BeforeEach
    public void init() {
        this.address = TestData.createAddress();
    }

    @DisplayName("Create a address with test data")
    @Test
    public void testCreateAddress() {
        Address addressToSave = this.address;

        given(this.addressRepo.save(addressToSave)).willReturn(addressToSave);

        Address resultAddress = this.addressService.saveAddress(addressToSave);

        verify(this.addressRepo, times(1)).save(addressToSave);

        assertNotNull(resultAddress);
        assertEquals(addressToSave, resultAddress);
    }

    @DisplayName("Get all address available in the table")
    @Test
    public void testGetAllAddress() {
        List<Address> addressesToFetch = new ArrayList<>();
        addressesToFetch.add(this.address);

        given(this.addressRepo.findAll()).willReturn(addressesToFetch);

        List<Address> resultAddresses = this.addressService.getAllAddresses();

        verify(this.addressRepo, times(resultAddresses.size())).findAll();

        assertNotNull(resultAddresses);
        assertFalse(resultAddresses.isEmpty());
    }

    @DisplayName("Fetch address by using id number")
    @Test
    public void testFetchAddressByIdNumber() {
        Address addressToFetch = this.address;

        given(this.addressRepo.findById(addressToFetch.getId())).willReturn(Optional.of(addressToFetch));

        Optional<Address> address = this.addressService.getAddressById(addressToFetch.getId());

        verify(this.addressRepo, times(1)).findById(addressToFetch.getId());

        assertTrue(address.isPresent());
        assertNotNull(address.get());
    }

    @DisplayName("Delete address by using id number")
    @Test
    public void testDeleteAddressByIdNumber() {
        Address addressToDelete = this.address;

        this.addressService.deleteAddress(addressToDelete.getId());

        verify(this.addressRepo, times(1)).deleteById(addressToDelete.getId());
    }
}
