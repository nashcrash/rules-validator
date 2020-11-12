package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.RvOperatorService;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import it.sitissimo.validation.service.dto.RvOperatorDTO;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.sitissimo.validation.domain.RvOperator}.
 */
@RestController
@RequestMapping("/api")
public class RvOperatorResource {

    private final Logger log = LoggerFactory.getLogger(RvOperatorResource.class);

    private static final String ENTITY_NAME = "rvOperator";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvOperatorService rvOperatorService;

    public RvOperatorResource(RvOperatorService rvOperatorService) {
        this.rvOperatorService = rvOperatorService;
    }

    /**
     * {@code POST  /rv-operators} : Create a new rvOperator.
     *
     * @param rvOperatorDTO the rvOperatorDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvOperatorDTO, or with status {@code 400 (Bad Request)} if the rvOperator has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rv-operators")
    public ResponseEntity<RvOperatorDTO> createRvOperator(@Valid @RequestBody RvOperatorDTO rvOperatorDTO) throws URISyntaxException {
        log.debug("REST request to save RvOperator : {}", rvOperatorDTO);
        if (rvOperatorDTO.getId() != null) {
            throw new BadRequestAlertException("A new rvOperator cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RvOperatorDTO result = rvOperatorService.save(rvOperatorDTO);
        return ResponseEntity.created(new URI("/api/rv-operators/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rv-operators} : Updates an existing rvOperator.
     *
     * @param rvOperatorDTO the rvOperatorDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvOperatorDTO,
     * or with status {@code 400 (Bad Request)} if the rvOperatorDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvOperatorDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rv-operators")
    public ResponseEntity<RvOperatorDTO> updateRvOperator(@Valid @RequestBody RvOperatorDTO rvOperatorDTO) throws URISyntaxException {
        log.debug("REST request to update RvOperator : {}", rvOperatorDTO);
        if (rvOperatorDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RvOperatorDTO result = rvOperatorService.save(rvOperatorDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvOperatorDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rv-operators} : get all the rvOperators.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvOperators in body.
     */
    @GetMapping("/rv-operators")
    public ResponseEntity<List<RvOperatorDTO>> getAllRvOperators(Pageable pageable) {
        log.debug("REST request to get a page of RvOperators");
        Page<RvOperatorDTO> page = rvOperatorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rv-operators/:id} : get the "id" rvOperator.
     *
     * @param id the id of the rvOperatorDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvOperatorDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rv-operators/{id}")
    public ResponseEntity<RvOperatorDTO> getRvOperator(@PathVariable Long id) {
        log.debug("REST request to get RvOperator : {}", id);
        Optional<RvOperatorDTO> rvOperatorDTO = rvOperatorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rvOperatorDTO);
    }

    /**
     * {@code DELETE  /rv-operators/:id} : delete the "id" rvOperator.
     *
     * @param id the id of the rvOperatorDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rv-operators/{id}")
    public ResponseEntity<Void> deleteRvOperator(@PathVariable Long id) {
        log.debug("REST request to delete RvOperator : {}", id);
        rvOperatorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
