package com.reece.branchmanager.addressbook.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.reece.branchmanager.addressbook.model.Contact;

public interface ContactRepository extends JpaRepository<Contact, Long> {
	
	
	List<Contact> findByAddressBookId(Long id);
}

