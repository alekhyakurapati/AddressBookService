package com.reece.branchmanager.addressbook.model;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
@Setter
@Table(name = "CONTACTS")
public class Contact implements Serializable {

		private static final long serialVersionUID = 1606532629484460884L;
   
		@Id
		@Column(name = "id", nullable = false)
		@GeneratedValue(strategy = GenerationType.IDENTITY)
		private Long id;
	    
		@Column(name = "name", nullable = false)
		private String name;
		@Column(name = "phoneNumber", nullable = false)
		private String phoneNumber;
	    
	    
	    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.PERSIST}, fetch=FetchType.EAGER)
	    @JoinColumn(name = "addressBookId", referencedColumnName="addressBookId")
	    private AddressBook addressBook;


		@Override
		public int hashCode() {
			return Objects.hash(name, phoneNumber);
		}


		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Contact other = (Contact) obj;
			return  Objects.equals(name, other.name) && Objects.equals(phoneNumber, other.phoneNumber);
		}
	

	
}
