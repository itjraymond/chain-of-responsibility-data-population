package ca.jent.prepopulation;

import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

public class PrePopulationServiceImpl implements PrePopulationService {

    /**
     * Chain-of-Responsibility pattern.
     * A User can have existing data from three different sources.
     * Try to populate from source A first, then if that does not work,
     * Try to populate from source B, then if that does not work,
     * Try ot populate from source C, then if that does not work
     * return un-populated UiForm
     * @param user
     * @return
     */
    public Optional<UiForm> populate(User user) {
        return Stream.<Function<User, Optional<UiForm>>>of(
                PrePopulationServiceImpl::populateFromSourceA,
                PrePopulationServiceImpl::populateFromSourceC,
                PrePopulationServiceImpl::populateFromSourceB,
                PrePopulationServiceImpl::populateFromNothing)
                .map(fn -> fn.apply(user))
                .filter(Optional::isPresent)
                .findFirst()
                .flatMap(Function.identity());
    }

    private static Optional<UiForm> populateFromSourceA(User user) {
        Optional<SourceADto> sourceADto = Stores.getSourceADataByUserId(user.getId());
        if (sourceADto.isPresent()) {
            return populateUiFormFromSourceA(sourceADto.get());
        }
        return Optional.empty();
    }

    private static Optional<UiForm> populateFromSourceB(User user) {
        Optional<SourceBDto> sourceBDto = Stores.getSourceBDataByUserId(user.getId());
        if (sourceBDto.isPresent()) {
            return populateUiFormFromSourceB(sourceBDto.get());
        }
        return Optional.empty();
    }

    private static Optional<UiForm> populateFromSourceC(User user) {
        Optional<SourceCDto> sourceCDto = Stores.getSourceCDataByUserId(user.getId());
        if (sourceCDto.isPresent()) {
            return populateUiFormFromSourceC(sourceCDto.get());
        }
        return Optional.empty();
    }

    private static Optional<UiForm> populateFromNothing(User user) {
        return Optional.of(new UiForm());
    }

    private static Optional<UiForm> populateUiFormFromSourceA(SourceADto sourceADto) {
        UiForm uiForm = new UiForm();
        uiForm.setSource("Source A");
        uiForm.setFirstname(sourceADto.getFirstname());
        uiForm.setLastname(sourceADto.getLastname());
        uiForm.setDob(sourceADto.getDob());
        uiForm.setSalary(sourceADto.getSalary());
        return Optional.of(uiForm);
    }

    private static Optional<UiForm> populateUiFormFromSourceB(SourceBDto sourceBDto) {
        UiForm uiForm = new UiForm();
        uiForm.setSource("Source B");
        uiForm.setFirstname(sourceBDto.getFirstname());
        uiForm.setLastname(sourceBDto.getLastname());
        uiForm.setDob(sourceBDto.getDob());
        return Optional.of(uiForm);
    }

    private static Optional<UiForm> populateUiFormFromSourceC(SourceCDto sourceCDto) {
        UiForm uiForm = new UiForm();
        uiForm.setSource("Source C");
        uiForm.setFirstname(sourceCDto.getFirstname());
        uiForm.setLastname(sourceCDto.getLastname());
        uiForm.setDob(sourceCDto.getDob());
        uiForm.setSalary(sourceCDto.getSalary());
        return Optional.of(uiForm);
    }
}
