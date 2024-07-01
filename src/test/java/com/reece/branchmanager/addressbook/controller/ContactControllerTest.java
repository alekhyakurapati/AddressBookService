package com.reece.branchmanager.addressbook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reece.branchmanager.addressbook.model.AddressBook;
import com.reece.branchmanager.addressbook.model.BaseResponse;
import com.reece.branchmanager.addressbook.model.Contact;
import com.reece.branchmanager.addressbook.service.AddressBookService;
import com.reece.branchmanager.addressbook.service.ContactService;

@SpringBootTest
@AutoConfigureMockMvc
public class ContactControllerTest {
	
	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressBookService addressBookService;

	@MockBean
	private ContactService contactService;
	
	@Test
	void getContacts() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		List<Contact> contacts = new ArrayList<>();
		Contact contact = new Contact();
		contact.setId((long) 1);
		contact.setName("book1");
		contact.setPhoneNumber("0423771678");

		contact.setAddressBook(book);
		contacts.add(contact);

		Mockito.when(contactService.getAllContacts()).thenReturn(contacts);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/contacts")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}

	@Test
	void getUniqueContacts() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		Set<Contact> contacts = new HashSet<>();
		Contact contact = new Contact();
		contact.setId((long) 1);
		contact.setName("book1");
		contact.setPhoneNumber("0423771678");

		contact.setAddressBook(book);
		contacts.add(contact);

		Mockito.when(contactService.getUniqueContacts()).thenReturn(contacts);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/contacts/unique")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}
	
	@Test
	void getContactsById() throws Exception {

		Contact contact = new Contact();
		contact.setId((long) 1);
		contact.setName("book1");
		contact.setPhoneNumber("0423771678");

		Mockito.when(contactService.getContact(Mockito.anyLong())).thenReturn(contact);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/contacts/{id}", "1")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}
}
