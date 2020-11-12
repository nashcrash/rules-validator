package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.RvParamService;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import it.sitissimo.validation.service.dto.RvParamDTO;

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

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link it.sitissimo.validation.domain.RvParam}.
 */
@RestController
@RequestMapping("/api")
public class RvParamResource {

    private final Logger log = LoggerFactory.getLogger(RvParamResource.class);

    private static final String ENTITY_NAME = "rvParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvParamService rvParamService;

    public RvParamResource(RvParamService rvParamService) {
        this.rvParamService = rvParamService;
    }

    /**
     * {@code POST  /rv-params} : Create a new rvParam.
     *
     * @param rvParamDTO the rvParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvParamDTO, or with status {@code 400 (Bad Request)} if the rvParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rv-params")
    public ResponseEntity<RvParamDTO> createRvParam(@RequestBody RvParamDTO rvParamDTO) throws URISyntaxException {
        log.debug("REST request to save RvParam : {}", rvParamDTO);
        if (rvParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new rvParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RvParamDTO result = rvParamService.save(rvParamDTO);
        return ResponseEntity.created(new URI("/api/rv-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rv-params} : Updates an existing rvParam.
     *
     * @param rvParamDTO the rvParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvParamDTO,
     * or with status {@code 400 (Bad Request)} if the rvParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rv-params")
    public ResponseEntity<RvParamDTO> updateRvParam(@RequestBody RvParamDTO rvParamDTO) throws URISyntaxException {
        log.debug("REST request to update RvParam : {}", rvParamDTO);
        if (rvParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RvParamDTO result = rvParamService.save(rvParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rv-params} : get all the rvParams.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvParams in body.
     */
    @GetMapping("/rv-params")
    public ResponseEntity<List<RvParamDTO>> getAllRvParams(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of RvParams");
        Page<RvParamDTO> page;
        if (eagerload) {
            page = rvParamService.findAllWithEagerRelationships(pageable);
        } else {
            page = rvParamService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rv-params/:id} : get the "id" rvParam.
     *
     * @param id the id of the rvParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rv-params/{id}")
    public ResponseEntity<RvParamDTO> getRvParam(@PathVariable Long id) {
        log.debug("REST request to get RvParam : {}", id);
        Optional<RvParamDTO> rvParamDTO = rvParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rvParamDTO);
    }

    /**
     * {@code DELETE  /rv-params/:id} : delete the "id" rvParam.
     *
     * @param id the id of the rvParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rv-params/{id}")
    public ResponseEntity<Void> deleteRvParam(@PathVariable Long id) {
        log.debug("REST request to delete RvParam : {}", id);
        rvParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
