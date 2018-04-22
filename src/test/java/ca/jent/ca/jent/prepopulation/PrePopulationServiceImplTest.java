package ca.jent.ca.jent.prepopulation;

import ca.jent.prepopulation.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Optional;

public class PrePopulationServiceImplTest extends TestCase  {

    public PrePopulationServiceImplTest( String testName )
    {
        super( testName );
    }

    public static Test suite()
    {
        return new TestSuite( PrePopulationServiceImplTest.class );
    }


    public void testPopulatingJohnShouldComeFromSourceA() {

        Optional<User> john = Stores.getUserByFirstname("John");
        PrePopulationService service = new PrePopulationServiceImpl();

        Optional<UiForm> uiForm = service.populate(john.get());

        assertTrue(uiForm.isPresent());
        assertEquals("Source A", uiForm.get().getSource());
    }

    public void testPopulatingBobShouldComeFromSourceB() {
        Optional<User> bob = Stores.getUserByFirstname("Bob");
        PrePopulationService service = new PrePopulationServiceImpl();

        Optional<UiForm> uiForm = service.populate(bob.get());

        assertTrue(uiForm.isPresent());
        assertEquals("Source B", uiForm.get().getSource());
    }

    public void testPopulatingJimShouldComeFromSourceC() {
        Optional<User> jim = Stores.getUserByFirstname("Jim");
        PrePopulationService service = new PrePopulationServiceImpl();

        Optional<UiForm> uiForm = service.populate(jim.get());

        assertTrue(uiForm.isPresent());
        assertEquals("Source C", uiForm.get().getSource());

    }

    public void testPopulateingBillShouldComeFromEmptyUiForm() {
        Optional<User> bill = Stores.getUserByFirstname("Bill");
        PrePopulationService service = new PrePopulationServiceImpl();

        Optional<UiForm> uiForm = service.populate(bill.get());

        assertTrue(uiForm.isPresent());
        assertEquals("None", uiForm.get().getSource());

    }

    public void testUserNotFound() {
        Optional<User> tom = Stores.getUserByFirstname("Tom");

        assertFalse(tom.isPresent());
    }
}
