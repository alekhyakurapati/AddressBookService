Branch manager Address Book API using Spring Boot data JPA using an in memory H2 database

Below are the REST end points to create , retrieve and delete address book data and contacts of the address book

1. GET -- /api/addressBook -- Returns list of address books
2. POST -- /api/addressBook -- Creates a new address book
3. GET --  /api/addressBook/{addressBookId} -- Returns the address book with the given ID.
4. GET -- /api/addressBook/name/{addressBookName} -- Returns the address book with the given name. 
			This is created if the user doesn't know the address book id.
5. POST -- /api/addressBook/{addressBookId}/contact -- Create contact for the given address book ID.
6. POST -- /api//addressBook/name/{addressBookName}/contact -- Create contact for the given address book name, 
			if the address book doesn't exists with this name , an address book gets created with the given name and the contact. 
7. GET -- /api//addressBook/{addressBookId}/contacts -- Returns list of contacts for the given address book ID.	
8. GET -- /api/contacts-- Returns all the contacts available.
9. GET -- /api/contacts/unique -- Returns all the unique contacts.	
10. GET --  /api/contacts/{id} -- Returns a contact with the given contact Id.
11. DELETE -- /api/contacts/{id} -- Deletes contact for the given contact id.


Sample response 
================

{
    "statusCode": 201,
    "timestamp": "2024-06-26T15:42:45.852+00:00",
    "message": "Address book created successfully",
    "description": "Created",
    "data": {
        "id": 1,
        "name": "book1"
    },
    "method": "POST",
    "path": "/api/addressBook"
}