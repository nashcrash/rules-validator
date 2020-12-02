package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.RvConverterService;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import it.sitissimo.validation.service.dto.RvConverterDTO;

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
 * REST controller for managing {@link it.sitissimo.validation.domain.RvConverter}.
 */
@RestController
@RequestMapping("/api")
public class RvConverterResource {

    private final Logger log = LoggerFactory.getLogger(RvConverterResource.class);

    private static final String ENTITY_NAME = "rvConverter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvConverterService rvConverterService;

    public RvConverterResource(RvConverterService rvConverterService) {
        this.rvConverterService = rvConverterService;
    }

    /**
     * {@code POST  /rv-converters} : Create a new rvConverter.
     *
     * @param rvConverterDTO the rvConverterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvConverterDTO, or with status {@code 400 (Bad Request)} if the rvConverter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rv-converters")
    public ResponseEntity<RvConverterDTO> createRvConverter(@Valid @RequestBody RvConverterDTO rvConverterDTO) throws URISyntaxException {
        log.debug("REST request to save RvConverter : {}", rvConverterDTO);
        if (rvConverterDTO.getId() != null) {
            throw new BadRequestAlertException("A new rvConverter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RvConverterDTO result = rvConverterService.save(rvConverterDTO);
        return ResponseEntity.created(new URI("/api/rv-converters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rv-converters} : Updates an existing rvConverter.
     *
     * @param rvConverterDTO the rvConverterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvConverterDTO,
     * or with status {@code 400 (Bad Request)} if the rvConverterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvConverterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rv-converters")
    public ResponseEntity<RvConverterDTO> updateRvConverter(@Valid @RequestBody RvConverterDTO rvConverterDTO) throws URISyntaxException {
        log.debug("REST request to update RvConverter : {}", rvConverterDTO);
        if (rvConverterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RvConverterDTO result = rvConverterService.save(rvConverterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvConverterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rv-converters} : get all the rvConverters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvConverters in body.
     */
    @GetMapping("/rv-converters")
    public ResponseEntity<List<RvConverterDTO>> getAllRvConverters(Pageable pageable) {
        log.debug("REST request to get a page of RvConverters");
        Page<RvConverterDTO> page = rvConverterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rv-converters/:id} : get the "id" rvConverter.
     *
     * @param id the id of the rvConverterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvConverterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rv-converters/{id}")
    public ResponseEntity<RvConverterDTO> getRvConverter(@PathVariable Long id) {
        log.debug("REST request to get RvConverter : {}", id);
        Optional<RvConverterDTO> rvConverterDTO = rvConverterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rvConverterDTO);
    }

    /**
     * {@code DELETE  /rv-converters/:id} : delete the "id" rvConverter.
     *
     * @param id the id of the rvConverterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rv-converters/{id}")
    public ResponseEntity<Void> deleteRvConverter(@PathVariable Long id) {
        log.debug("REST request to delete RvConverter : {}", id);
        rvConverterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
