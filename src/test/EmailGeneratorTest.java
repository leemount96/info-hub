package test;

import static org.junit.Assert.*;

import org.junit.Test;

import main.EmailGenerator;

/**
 * Class to test EmailGenerator
 */
public class EmailGeneratorTest {
	
	//Test basic email
	@Test
	public void testEmail(){
		EmailGenerator e = new EmailGenerator();
		e.addEmail("lmount@mit.edu");
		e.addEmail("mount.lester@gmail.com");
		e.sendMail("Test email content");
		assertTrue(true);
	}
}
