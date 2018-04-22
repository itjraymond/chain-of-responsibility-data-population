package ca.jent.prepopulation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Storage simulation for our Entities.
 */
public class Stores {

    // Building our User storage...
    public static Map<Long, Optional<User>> userStoreById = new HashMap<>();
    private static Map<String, Optional<User>> userStoreByFirstname = new HashMap<>();
    static {
        Optional<User> john = createUser("John", "Deschamps");
        userStoreById.put(john.get().getId(), john);
        userStoreByFirstname.put(john.get().getFirstname(), john);

        Optional<User> bob = createUser("Bob", "Smith");
        userStoreById.put(bob.get().getId(), bob);
        userStoreByFirstname.put(bob.get().getFirstname(), bob);

        Optional<User> jim = createUser("Jim", "Hudon");
        userStoreById.put(jim.get().getId(), jim);
        userStoreByFirstname.put(jim.get().getFirstname(), jim);

        Optional<User> bill = createUser("Bill", "Cosby");
        userStoreById.put(bill.get().getId(), bill);
        userStoreByFirstname.put(bill.get().getFirstname(), bill);
    }

    /**
     * Get User by their firstname from storage.
     * @param firstname
     * @return A User wrapped into an Optional if exist; otherwise an empty Optional
     */
    public static Optional<User> getUserByFirstname(String firstname) {
        return userStoreByFirstname.getOrDefault(firstname, Optional.empty());
    }

    /**
     * Get User by their id from storage.
     * @param id
     * @return A User wrapped into an Optional if exist; otherwise an empty Optional
     */
    public static Optional<User> getUserById(Long id) {
        return userStoreById.getOrDefault(id, Optional.empty());
    }


    // Building our source A storage...
    private static Map<Long, Optional<SourceADto>> sourceAStore = new HashMap<>();
    static {
        // only some user are in the sourceA store
        userStoreByFirstname.get("John").ifPresent( john -> {
            sourceAStore.put(john.getId(), createSourceADto(john));
        });
    }

    // Building our sourceB  storage...
    private static Map<Long, Optional<SourceBDto>> sourceBStore = new HashMap<>();
    static {
        // only some user are in sourceB store
        userStoreByFirstname.get("Bob").ifPresent( bob -> {
            sourceBStore.put(bob.getId(), createSourceBDto(bob));
        });
    }

    // Building our sourceC storage...
    private static Map<Long, Optional<SourceCDto>> sourceCStore = new HashMap<>();
    static {
        // only some user are in sourceC store
        userStoreByFirstname.get("Jim").ifPresent( jim -> {
            sourceCStore.put(jim.getId(), createSourceCDto(jim));
        });
    }

    /**
     * Get Source A data for a user
     * @param id (User Id)
     * @return Optional containing a SourceADto object or empty.
     */
    public static Optional<SourceADto> getSourceADataByUserId(Long id) {
        return sourceAStore.getOrDefault(id, Optional.empty());
    }

    /**
     * Get Source B data for a user
     * @param id (User Id)
     * @return Optional containing a SourceBDto object or empty.
     */
    public static Optional<SourceBDto> getSourceBDataByUserId(Long id) {
        return sourceBStore.getOrDefault(id, Optional.empty());
    }

    /**
     * Get Source C data for a user
     * @param id (User Id)
     * @return Optional containing a SourceCDto object or empty.
     */
    public static Optional<SourceCDto> getSourceCDataByUserId(Long id) {
        return sourceCStore.getOrDefault(id, Optional.empty());
    }


    private static Optional<User> createUser(String firstname, String lastname) {
        User user = new User();
        user.setFirstname(firstname);
        user.setLastname(lastname);
        user.setUsername(firstname.toLowerCase() + lastname.toLowerCase());
        return Optional.of(user);
    }

    private static Optional<SourceADto> createSourceADto(User user) {
        SourceADto sourceADto = new SourceADto();
        sourceADto.setFirstname(user.getFirstname());
        sourceADto.setLastname(user.getLastname());
        sourceADto.setDob(getRandomDob());
        sourceADto.setSalary(30000D);
        return Optional.of(sourceADto);
    }

    private static Optional<SourceBDto> createSourceBDto(User user) {
        SourceBDto sourceBDto = new SourceBDto();
        sourceBDto.setDob(getRandomDob());
        sourceBDto.setFirstname(user.getFirstname());
        sourceBDto.setLastname(user.getLastname());
        return Optional.of(sourceBDto);
    }

    private static Optional<SourceCDto> createSourceCDto(User user) {
        SourceCDto sourceCDto = new SourceCDto();
        sourceCDto.setFirstname(user.getFirstname());
        sourceCDto.setLastname(user.getLastname());
        sourceCDto.setDob(getRandomDob());
        sourceCDto.setEmail(user.getFirstname().toLowerCase() + "." + user.getLastname().toLowerCase() + "@gmail.com");
        sourceCDto.setSalary(40000D);
        return Optional.of(sourceCDto);
    }


    //private static Random random = new Random();

    private static LocalDate getRandomDob() {
        Random random = new Random();
        LocalDate today = LocalDate.now();
        LocalDate randomDob = today.minusYears(40);
        int rmonths = random.nextInt(36);
        int rdays = random.nextInt(16);

        return randomDob.plusMonths(rmonths).minusDays(rdays);
    }
}
