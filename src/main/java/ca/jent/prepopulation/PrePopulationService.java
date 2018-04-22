package ca.jent.prepopulation;

import java.util.Optional;

public interface PrePopulationService {

    Optional<UiForm> populate(User user);
}
