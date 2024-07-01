package com.reece.branchmanager.addressbook.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reece.branchmanager.addressbook.exception.ResourceNotFoundException;
import com.reece.branchmanager.addressbook.model.AddressBook;
import com.reece.branchmanager.addressbook.repository.AddressBookRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class AddressBookService {

	@Autowired
	private AddressBookRepository addressBookRepository;

	public List<AddressBook> getAllAddressBooks() {

		return addressBookRepository.findAll();

	}

	public AddressBook getAddressBookById(Long id) {

		Optional<AddressBook> addressBook = addressBookRepository.findById(id);

		if (addressBook.isPresent()) {
			return addressBook.get();
		} else {
			log.error("Contacts not available for the addressbook id: " + id);
			throw new ResourceNotFoundException("Contacts not available for the addressbook id: " + id);
		}

	}

	public AddressBook getAddressBookByName(String name) {

		Optional<AddressBook> addressBook = addressBookRepository.findByName(name);
		if (addressBook.isPresent()) {
			return addressBook.get();
		} else {
			log.error("Contacts not available for the addressbook name: " + name);
			throw new ResourceNotFoundException("Contacts not available for the addressbook name: " + name);
		}
	}

	public AddressBook saveAddressBook(AddressBook addressBook) {
		return addressBookRepository.save(addressBook);
	}

}
