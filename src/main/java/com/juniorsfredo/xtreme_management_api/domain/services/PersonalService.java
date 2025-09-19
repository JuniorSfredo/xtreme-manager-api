package com.juniorsfredo.xtreme_management_api.domain.services;

import com.juniorsfredo.xtreme_management_api.domain.exceptions.EntityNotFoundException;
import com.juniorsfredo.xtreme_management_api.domain.models.Personal;
import com.juniorsfredo.xtreme_management_api.domain.repositories.PersonalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PersonalService {

    private PersonalRepository personalRepository;

    @Autowired
    public PersonalService(PersonalRepository personalRepository) {
        this.personalRepository = personalRepository;
    }

    public Personal getPersonalById(Long id) {
        return findPersonalById(id)
                .orElseThrow(() -> new EntityNotFoundException("Personal not found with id: " + id));
    }

    protected Optional<Personal> findPersonalById(Long id) {
        return personalRepository.findById(id);
    }
}
