package com.reece.branchmanager.addressbook.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.reece.branchmanager.addressbook.exception.ResourceNotFoundException;
import com.reece.branchmanager.addressbook.model.AddressBook;
import com.reece.branchmanager.addressbook.model.Contact;
import com.reece.branchmanager.addressbook.repository.AddressBookRepository;
import com.reece.branchmanager.addressbook.repository.ContactRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContactService {

	@Autowired
	private AddressBookRepository addressBookRepository;

	@Autowired
	private ContactRepository contactRepository;

	public Contact saveContactToAddressBook(final Long addressBookId, final Contact contactRequest) {

		Contact contact = addressBookRepository.findById(addressBookId).map(addressBook -> {
			contactRequest.setAddressBook(addressBook);
			return contactRepository.save(contactRequest);
		}).orElseThrow(
				() -> new ResourceNotFoundException("Addressbook not found with the addressbook id: " + addressBookId));

		return contact;

	}

	public Contact saveContactToAddressBook(final String addressBookName, final Contact contact) {

		Optional<AddressBook> addressBook = addressBookRepository.findByName(addressBookName);

		if (addressBook.isPresent()) {
			contact.setAddressBook(addressBook.get());
		} else {
			AddressBook book = new AddressBook();
			book.setName(addressBookName);
			contact.setAddressBook(book);
		}

		return contactRepository.save(contact);

	}

	public List<Contact> getAllContactsFromAddressBook(final Long addressBookId) {

		Optional<AddressBook> addressBook = addressBookRepository.findById(addressBookId);

		if (addressBook.isPresent()) {
			log.info(addressBook.toString());
			List<Contact> contacts = contactRepository.findByAddressBookId(addressBookId);
			if (!contacts.isEmpty()) {
				return contacts;
			} else {
				log.error("Contacts not available for the addressbook id: " + addressBookId);
				throw new ResourceNotFoundException("Contacts not available for the addressbook id: " + addressBookId);
			}

		} else {
			log.error("Addressbook not found with id:" + addressBookId);
			throw new ResourceNotFoundException("Addressbook not found with id: " + addressBookId);
		}

	}

	public void deleteContact(final Long id) {

		contactRepository.deleteById(id);

	}

	public List<Contact> getAllContacts() {

		return contactRepository.findAll();

	}

	public Set<Contact> getUniqueContacts() {

		Set<Contact> contacts = new HashSet<Contact>();
		contactRepository.findAll().forEach(contacts::add);
		return contacts;

	}

	public Contact getContact(Long id) {
		Optional<Contact> contact = contactRepository.findById(id);
		if (contact.isPresent()) {
			return contact.get();
		} else {
			log.error("Contacts not available for the contact id:  id=" + id);
			throw new ResourceNotFoundException("Contacts not available for the contact id:  id=" + id);
		}

	}

}
