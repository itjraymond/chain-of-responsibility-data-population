package ca.jent;

import ca.jent.prepopulation.*;

import java.util.Optional;

public class App
{
    public static void main( String[] args ) {

        Optional<User> john = Stores.getUserByFirstname("John");
        PrePopulationService service = new PrePopulationServiceImpl();

        Optional<UiForm> uiForm = service.populate(john.get());

        uiForm.ifPresent( form -> {
            System.out.println("Source: " + form.getSource());
        });

        Optional<User> jim = Stores.getUserByFirstname("Jim");
        uiForm = service.populate(jim.get());

        uiForm.ifPresent( form -> {
            System.out.println("Source: " + form.getSource());
        });

        Optional<User> bob = Stores.getUserByFirstname("Bob");
        uiForm = service.populate(bob.get());

        uiForm.ifPresent( form -> {
            System.out.println("Source: " + form.getSource());
        });

        Optional<User> bill = Stores.getUserByFirstname("Bill");
        uiForm = service.populate(bill.get());

        uiForm.ifPresent( form -> {
            System.out.println("Source: " + form.getSource());
        });
    }
}
