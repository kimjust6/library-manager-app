package junit;

import static org.junit.Assert.assertEquals;

import java.sql.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import classes.BorrowedBooksTableLine;
import classes.Librarian;
import classes.LibraryObjects;
import classes.Student;

class TestClasses {

	@Test
	@DisplayName("Testing Class borrowedBooksTableLine")
	void testBorrowedBooksTableLine() {
		
		BorrowedBooksTableLine b = new BorrowedBooksTableLine(1,"Harry","Potter",3,"Advanced Potion Making","Libatius Borage","A Publisher","book");
		assertEquals(1,b.getStudentno());
		assertEquals("Harry",b.getFname());
		assertEquals("Potter",b.getLname());
		assertEquals(3,b.getLibid());
		assertEquals("Advanced Potion Making",b.getTitle());
		assertEquals("Libatius Borage",b.getAuthor());
		assertEquals("A Publisher",b.getPublisher());
		assertEquals("book",b.getMedia_type());
		
		b.setFname("Ron");
		b.setLname("Weasley");
		assertEquals("Ron",b.getFname());
		assertEquals("Weasley",b.getLname());
	}
	
	@Test
	@DisplayName("Testing Class Librarian")
	void testLibrarian() {
		
		Librarian l = new Librarian("askywalker","Anakin","Skywalker","askywalker@gmail.com", "1234567890",new Date(1981,4,19));
		assertEquals("askywalker",l.getUsername());
		assertEquals("Anakin",l.getFirstname());
		assertEquals("Skywalker",l.getLastname());
		assertEquals("askywalker@gmail.com",l.getEmail());
		assertEquals("1234567890",l.getPhone());

		l.setFirstname("Darth");;
		l.setLastname("Vader");
		assertEquals("Darth",l.getFirstname());
		assertEquals("Vader",l.getLastname());
	}
	
	@Test
	@DisplayName("Testing Class LibraryObjects")
	void testLibraryObjects() {
		
	
		LibraryObjects lo = new LibraryObjects("Harry Potter and the Prisoner of Azkaban","J.K. Rowling","Scholastic","book", 3,0,1);
		assertEquals("Harry Potter and the Prisoner of Azkaban",lo.getTitle());
		assertEquals("J.K. Rowling",lo.getAuthor());
		assertEquals("Scholastic",lo.getPublisher());
		assertEquals("book",lo.getMediaType());
		assertEquals(3,lo.getQtyAvailable());
		assertEquals(0,lo.getQtyBorrowed());
		assertEquals(1,lo.getLibid());

		lo.setTitle("A Song of Ice and Fire");;
		assertEquals("A Song of Ice and Fire",lo.getTitle());
		lo.setAuthor("George R. R. Martin");
		assertEquals("George R. R. Martin",lo.getAuthor());
	}
	
	@Test
	@DisplayName("Testing Class Student")
	void testStudentObjects() {
		
		
		Student lo = new Student(1,"Jon","Snow", "current", 2);
		assertEquals("Jon",lo.getFName());
		assertEquals("Snow",lo.getLName());
		assertEquals("current",lo.getStudentLvl());
		assertEquals(2,lo.getStudentNo());
		assertEquals(1,lo.getWaitid());


		lo.setfName("Azor");;
		assertEquals("Azor",lo.getFName());
		lo.setlName("Ahai");
		assertEquals("Ahai",lo.getLName());
		lo.setStudentNo(4);
		assertEquals(4,lo.getStudentNo());
	}

}
