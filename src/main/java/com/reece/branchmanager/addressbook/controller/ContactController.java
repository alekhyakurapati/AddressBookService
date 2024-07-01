package com.reece.branchmanager.addressbook.controller;

import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.reece.branchmanager.addressbook.model.BaseResponse;
import com.reece.branchmanager.addressbook.model.Contact;
import com.reece.branchmanager.addressbook.model.ResponseMapper;
import com.reece.branchmanager.addressbook.service.ContactService;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class ContactController {
	
	@Autowired
	private ContactService contactService;
	
	@GetMapping("/contacts")
	public BaseResponse<List<Contact>> getAllContacts(final HttpServletRequest httpServletRequest) {

		List<Contact> contacts = contactService.getAllContacts();
		if (contacts.isEmpty()) {
			return ResponseMapper.createResponse(httpServletRequest, contacts, HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), "Contacts list is empty");
		}

		return ResponseMapper.createResponse(httpServletRequest, contacts, HttpStatus.OK, HttpStatus.OK.value(), "Contacts list returned successfully");
	}


	@GetMapping("/contacts/unique")
	public BaseResponse<Set<Contact>> getUniqueContacts(final HttpServletRequest httpServletRequest) {

		Set<Contact> uniqueContacts =  contactService.getUniqueContacts();
		if (uniqueContacts.isEmpty()) {
			return ResponseMapper.createResponse(httpServletRequest, uniqueContacts, HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), "Contacts list is empty");
		}

		return ResponseMapper.createResponse(httpServletRequest, uniqueContacts, HttpStatus.OK, HttpStatus.OK.value(), "Unique Contacts list returned successfully");
	}

	
	@GetMapping("/contacts/{id}")
	public BaseResponse<Contact> getContact(final HttpServletRequest httpServletRequest, @PathVariable("id") Long id) {

		Contact contact = contactService.getContact(id);
		return ResponseMapper.createResponse(httpServletRequest, contact, HttpStatus.OK, HttpStatus.OK.value(), "Contact retrieved successfully");
	}
	
	@DeleteMapping("/contacts/{id}")
	public BaseResponse<Contact> deleteContact(final HttpServletRequest httpServletRequest, @PathVariable("id") Long id) {

		contactService.deleteContact(id);
		return ResponseMapper.createResponse(httpServletRequest, null, HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), "Contact deleted successfully");
	}


}
