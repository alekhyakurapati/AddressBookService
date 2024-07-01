package com.reece.branchmanager.addressbook.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reece.branchmanager.addressbook.model.AddressBook;
import com.reece.branchmanager.addressbook.model.BaseResponse;
import com.reece.branchmanager.addressbook.model.Contact;
import com.reece.branchmanager.addressbook.model.ResponseMapper;
import com.reece.branchmanager.addressbook.service.AddressBookService;
import com.reece.branchmanager.addressbook.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class AddressBookController {

	@Autowired
	private AddressBookService addressBookService;

	@Autowired
	private ContactService contactService;

	@PostMapping("/addressBook")
	public BaseResponse<AddressBook> CreateAddressBook(final HttpServletRequest httpServletRequest,
			@RequestBody final AddressBook addressBookRequest) {

		AddressBook addressBook = addressBookService.saveAddressBook(addressBookRequest);
		return ResponseMapper.createResponse(httpServletRequest, addressBook, HttpStatus.CREATED,
				HttpStatus.CREATED.value(), "Addressbook created successfully");

	}

	@GetMapping("/addressBook")
	public BaseResponse<List<AddressBook>> getAllAddressBooks(final HttpServletRequest httpServletRequest) {

		List<AddressBook> addressBooks = addressBookService.getAllAddressBooks();
		if (addressBooks.isEmpty()) {
			return ResponseMapper.createResponse(httpServletRequest, addressBooks, HttpStatus.NO_CONTENT,
					HttpStatus.NO_CONTENT.value(), "Address book list is empty");
		}

		return ResponseMapper.createResponse(httpServletRequest, addressBooks, HttpStatus.OK, HttpStatus.OK.value(),
				"Address book list returned successfully");
	}

	@GetMapping("/addressBook/{addressBookId}")
	public BaseResponse<AddressBook> getAddressBook(final HttpServletRequest httpServletRequest,
			@PathVariable final Long addressBookId) {

		AddressBook addressBook = addressBookService.getAddressBookById(addressBookId);

		return ResponseMapper.createResponse(httpServletRequest, addressBook, HttpStatus.OK, HttpStatus.OK.value(),
				"Address book details returned successfully");
	}

	@GetMapping("/addressBook/name/{addressBookName}")
	public BaseResponse<AddressBook> getAddressBookByName(final HttpServletRequest httpServletRequest,
			@PathVariable final String addressBookName) {

		AddressBook addressBook = addressBookService.getAddressBookByName(addressBookName);

		return ResponseMapper.createResponse(httpServletRequest, addressBook, HttpStatus.OK, HttpStatus.OK.value(),
				"Address book details returned successfully");
	}

	@PostMapping("/addressBook/{addressBookId}/contact")
	public BaseResponse<Contact> addContactToAddressBook(final HttpServletRequest httpServletRequest,
			@PathVariable final Long addressBookId, @RequestBody final Contact contactRequest) {

		Contact contact = contactService.saveContactToAddressBook(addressBookId, contactRequest);
		return ResponseMapper.createResponse(httpServletRequest, contact, HttpStatus.CREATED,
				HttpStatus.CREATED.value(), "Contact for the given address book id created successfully");
	}

	@PostMapping("/addressBook/name/{addressBookName}/contact")
	public BaseResponse<Contact> addContactToAddressBook(final HttpServletRequest httpServletRequest,
			@PathVariable final String addressBookName, @RequestBody final Contact contactRequest) {

		Contact contact = contactService.saveContactToAddressBook(addressBookName, contactRequest);

		return ResponseMapper.createResponse(httpServletRequest, contact, HttpStatus.CREATED,
				HttpStatus.CREATED.value(), "Contact for the given address book name created successfully");

	}

	@GetMapping("/addressBook/{addressBookId}/contacts")
	public BaseResponse<List<Contact>> getAllContactsFromAddressBook(final HttpServletRequest httpServletRequest,
			@PathVariable final Long addressBookId) {

		List<Contact> contacts = contactService.getAllContactsFromAddressBook(addressBookId);
		return ResponseMapper.createResponse(httpServletRequest, contacts, HttpStatus.OK, HttpStatus.OK.value(),
				"Contacts list for the given Address book id returned successfully");
	}

}
