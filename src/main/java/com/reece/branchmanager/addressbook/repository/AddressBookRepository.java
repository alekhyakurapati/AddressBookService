package com.reece.branchmanager.addressbook.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import com.reece.branchmanager.addressbook.model.AddressBook;

public interface AddressBookRepository extends JpaRepository<AddressBook, Long>{

	Optional<AddressBook> findByName(String name);

	
}
