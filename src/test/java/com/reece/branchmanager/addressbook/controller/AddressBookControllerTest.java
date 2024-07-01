package com.reece.branchmanager.addressbook.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.reece.branchmanager.addressbook.exception.ResourceNotFoundException;
import com.reece.branchmanager.addressbook.model.AddressBook;
import com.reece.branchmanager.addressbook.model.BaseResponse;
import com.reece.branchmanager.addressbook.model.Contact;
import com.reece.branchmanager.addressbook.service.AddressBookService;
import com.reece.branchmanager.addressbook.service.ContactService;

@SpringBootTest
@AutoConfigureMockMvc
public class AddressBookControllerTest {

	private static final ObjectMapper objectMapper = new ObjectMapper();

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private AddressBookService addressBookService;

	@MockBean
	private ContactService contactService;

	@Test
	void getAddressBooks() throws Exception {

		List<AddressBook> addressBooks = new ArrayList<>();
		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		addressBooks.add(book);

		Mockito.when(addressBookService.getAllAddressBooks()).thenReturn(addressBooks);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/addressBook")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}

	@Test
	void getAddressBooks_Empty() throws Exception {

		Mockito.when(addressBookService.getAllAddressBooks()).thenReturn(Collections.emptyList());

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/addressBook")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 204);
		Assertions.assertEquals(responseData.getDescription(), "No Content");
		Assertions.assertEquals(responseData.getData(), Collections.emptyList());

	}

	@Test
	void getAddressBookById() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		Mockito.when(addressBookService.getAddressBookById(Mockito.anyLong())).thenReturn(book);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/addressBook/{id}", "1")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}

	@Test
	void getAddressBookById_NotExists() throws Exception {

		Mockito.when(addressBookService.getAddressBookById(Mockito.anyLong()))
				.thenThrow(ResourceNotFoundException.class);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/addressBook/{id}", "1")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 404);
		Assertions.assertEquals(responseData.getDescription(), "Not Found");
		Assertions.assertNull(responseData.getData());

	}

	@Test
	void getAddressBookByName() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		Mockito.when(addressBookService.getAddressBookByName(Mockito.anyString())).thenReturn(book);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/addressBook/name/{addressBookName}", "Anu"))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}

	@Test
	void getAddressBookByName_NotExists() throws Exception {

		Mockito.when(addressBookService.getAddressBookByName(Mockito.anyString()))
				.thenThrow(ResourceNotFoundException.class);

		MockHttpServletResponse response = this.mockMvc
				.perform(get("/api/addressBook/name/{addressBookName}", "Alekhya")).andDo(print())
				.andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 404);
		Assertions.assertEquals(responseData.getDescription(), "Not Found");
		Assertions.assertNull(responseData.getData());

	}

	@Test
	void createAddressBook() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		Mockito.when(addressBookService.saveAddressBook(Mockito.any())).thenReturn(book);

		MockHttpServletResponse response = this.mockMvc
				.perform(post("/api/addressBook").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(book)))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 201);
		Assertions.assertEquals(responseData.getDescription(), "Created");
		Assertions.assertNotNull(responseData.getData());

	}

	@Test
	void createContactInAddressBook() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		Contact contact = new Contact();
		contact.setId((long) 1);
		contact.setName("book1");
		contact.setPhoneNumber("0423771678");

		contact.setAddressBook(book);

		Mockito.when(contactService.saveContactToAddressBook(Mockito.anyLong(), Mockito.any())).thenReturn(contact);

		MockHttpServletResponse response = this.mockMvc
				.perform(post("/api/addressBook/{addressBookId}/contact", "1").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(contact)))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 201);
		Assertions.assertEquals(responseData.getDescription(), "Created");
		Assertions.assertNotNull(responseData.getData());

	}

	@Test
	void createContactInAddressBook_NotExists() throws Exception {

		AddressBook book = new AddressBook();
		book.setId((long) 1);
		book.setName("anu");

		Contact contact = new Contact();
		contact.setId((long) 1);
		contact.setName("book1");
		contact.setPhoneNumber("0423771678");

		contact.setAddressBook(book);

		Mockito.when(contactService.saveContactToAddressBook(Mockito.anyLong(), Mockito.any()))
				.thenThrow(ResourceNotFoundException.class);

		MockHttpServletResponse response = this.mockMvc
				.perform(post("/api/addressBook/{addressBookId}/contact", "1").contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(contact)))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 404);
		Assertions.assertEquals(responseData.getDescription(), "Not Found");
		Assertions.assertNull(responseData.getData());

	}

	@Test
	void getAddressBookContacts() throws Exception {

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

		Mockito.when(contactService.getAllContactsFromAddressBook(Mockito.anyLong())).thenReturn(contacts);

		MockHttpServletResponse response = this.mockMvc.perform(get("/api/addressBook/{addressBookId}/contacts", "1"))
				.andDo(print()).andExpect(status().isOk()).andReturn().getResponse();

		BaseResponse responseData = objectMapper.readValue(response.getContentAsString(), BaseResponse.class);

		Assertions.assertNotNull(responseData);
		Assertions.assertEquals(responseData.getStatusCode(), 200);
		Assertions.assertEquals(responseData.getDescription(), "OK");
		Assertions.assertNotNull(responseData.getData());

	}

}
