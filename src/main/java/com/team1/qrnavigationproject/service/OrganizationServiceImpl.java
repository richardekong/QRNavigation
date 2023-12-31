package com.team1.qrnavigationproject.service;

import com.team1.qrnavigationproject.model.Event;
import com.team1.qrnavigationproject.model.Organization;
import com.team1.qrnavigationproject.model.Space;
import com.team1.qrnavigationproject.repository.OrganizationRepo;
import com.team1.qrnavigationproject.response.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.Supplier;

@Service
public class OrganizationServiceImpl implements OrganizationService {

    private OrganizationRepo organizationRepo;

    @Autowired
    public void setOrganizationRepo(OrganizationRepo organizationRepo) {
        this.organizationRepo = organizationRepo;
    }

    @Override
    public Organization save(Organization organization) {
        AtomicReference<Organization> savedOrganization = new AtomicReference<>(null);
        organizationRepo.findOrganizationByName(organization.getName()).ifPresentOrElse(org -> {
            throw new CustomException(
                    "%s already exists".formatted(org.getName()),
                    HttpStatus.CONFLICT
            );
        }, () -> savedOrganization.set(organizationRepo.save(organization)));
        return savedOrganization.get();
    }

    @Override
    public Organization findById(int id) {
        return organizationRepo.findOrganizationById(id)
                .orElseThrow(handleNotFoundException());
    }

    @Override
    public Organization findByName(String name) {
        return organizationRepo.findOrganizationByName(name)
                .orElseThrow(handleNotFoundException());
    }

    @Override
    public List<Organization> findAll() {
        return Optional.of(organizationRepo.findAll())
                .orElseThrow(handleNotFoundException());
    }

    @Override
    public List<Space> findAllSpacesById(int id) {
        return organizationRepo.findAllSpacesById(id)
                .orElseThrow(handleNotFoundException());
    }

    @Override
    public List<Event> findAllEventsById(int id) {
        return organizationRepo.findAllEventsById(id)
                .orElseThrow(handleNotFoundException());
    }

    @Override
    public Organization update(Organization organization) {
        if (!organizationRepo.existsById(organization.getId())) {
            throw new CustomException(
                    HttpStatus.NOT_FOUND.getReasonPhrase(),
                    HttpStatus.NOT_FOUND
            );
        }
        return organizationRepo.save(organization);
    }

    private Supplier<CustomException> handleNotFoundException() {
        return () -> new CustomException(
                HttpStatus.NOT_FOUND.getReasonPhrase(),
                HttpStatus.NOT_FOUND
        );
    }


}
