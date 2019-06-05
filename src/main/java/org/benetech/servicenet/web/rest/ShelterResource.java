package org.benetech.servicenet.web.rest;

import java.util.UUID;
import org.benetech.servicenet.service.ShelterService;
import org.benetech.servicenet.service.dto.ShelterDTO;
import org.benetech.servicenet.web.rest.errors.BadRequestAlertException;
import org.benetech.servicenet.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST controller for managing Shelter.
 */
@RestController
@RequestMapping("/api")
public class ShelterResource {

    private final Logger log = LoggerFactory.getLogger(ShelterResource.class);

    private static final String ENTITY_NAME = "shelter";

    private final ShelterService shelterService;

    public ShelterResource(ShelterService shelterService) {
        this.shelterService = shelterService;
    }

    /**
     * POST  /shelters : Create a new shelter.
     *
     * @param shelter the shelter to create
     * @return the ResponseEntity with status 201 (Created) and with body the new shelter,
     * or with status 400 (Bad Request) if the shelter has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/shelters")
    public ResponseEntity<ShelterDTO> createShelter(@RequestBody ShelterDTO shelter) throws URISyntaxException {
        log.debug("REST request to save Shelter : {}", shelter);
        if (shelter.getId() != null) {
            throw new BadRequestAlertException("A new shelter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ShelterDTO result = shelterService.save(shelter);
        return ResponseEntity.created(new URI("/api/shelters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /shelters : Updates an existing shelter.
     *
     * @param shelter the shelter to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated shelter,
     * or with status 400 (Bad Request) if the shelter is not valid,
     * or with status 500 (Internal Server Error) if the shelter couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/shelters")
    public ResponseEntity<ShelterDTO> updateShelter(@RequestBody ShelterDTO shelter) throws URISyntaxException {
        log.debug("REST request to update Shelter : {}", shelter);
        if (shelter.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ShelterDTO result = shelterService.save(shelter);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, shelter.getId().toString()))
            .body(result);
    }

    /**
     * GET  /shelters : get all the shelters.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of shelters in body
     */
    @GetMapping("/shelters")
    public List<ShelterDTO> getAllShelters() {
        log.debug("REST request to get all Shelters");
        return shelterService.findAll();
    }

    /**
     * GET  /shelters/:id : get the "id" shelter.
     *
     * @param id the id of the shelter to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the shelter, or with status 404 (Not Found)
     */
    @GetMapping("/shelters/{id}")
    public ResponseEntity<ShelterDTO> getShelter(@PathVariable UUID id) {
        log.debug("REST request to get Shelter : {}", id);
        Optional<ShelterDTO> shelter = shelterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(shelter);
    }

    /**
     * DELETE  /shelters/:id : delete the "id" shelter.
     *
     * @param id the id of the shelter to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/shelters/{id}")
    public ResponseEntity<Void> deleteShelter(@PathVariable UUID id) {
        log.debug("REST request to delete Shelter : {}", id);
        shelterService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
