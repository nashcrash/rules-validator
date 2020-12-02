package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.RvOperatorParamService;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import it.sitissimo.validation.service.dto.RvOperatorParamDTO;

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
 * REST controller for managing {@link it.sitissimo.validation.domain.RvOperatorParam}.
 */
@RestController
@RequestMapping("/api")
public class RvOperatorParamResource {

    private final Logger log = LoggerFactory.getLogger(RvOperatorParamResource.class);

    private static final String ENTITY_NAME = "rvOperatorParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvOperatorParamService rvOperatorParamService;

    public RvOperatorParamResource(RvOperatorParamService rvOperatorParamService) {
        this.rvOperatorParamService = rvOperatorParamService;
    }

    /**
     * {@code POST  /rv-operator-params} : Create a new rvOperatorParam.
     *
     * @param rvOperatorParamDTO the rvOperatorParamDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvOperatorParamDTO, or with status {@code 400 (Bad Request)} if the rvOperatorParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rv-operator-params")
    public ResponseEntity<RvOperatorParamDTO> createRvOperatorParam(@Valid @RequestBody RvOperatorParamDTO rvOperatorParamDTO) throws URISyntaxException {
        log.debug("REST request to save RvOperatorParam : {}", rvOperatorParamDTO);
        if (rvOperatorParamDTO.getId() != null) {
            throw new BadRequestAlertException("A new rvOperatorParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RvOperatorParamDTO result = rvOperatorParamService.save(rvOperatorParamDTO);
        return ResponseEntity.created(new URI("/api/rv-operator-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rv-operator-params} : Updates an existing rvOperatorParam.
     *
     * @param rvOperatorParamDTO the rvOperatorParamDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvOperatorParamDTO,
     * or with status {@code 400 (Bad Request)} if the rvOperatorParamDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvOperatorParamDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rv-operator-params")
    public ResponseEntity<RvOperatorParamDTO> updateRvOperatorParam(@Valid @RequestBody RvOperatorParamDTO rvOperatorParamDTO) throws URISyntaxException {
        log.debug("REST request to update RvOperatorParam : {}", rvOperatorParamDTO);
        if (rvOperatorParamDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RvOperatorParamDTO result = rvOperatorParamService.save(rvOperatorParamDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvOperatorParamDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rv-operator-params} : get all the rvOperatorParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvOperatorParams in body.
     */
    @GetMapping("/rv-operator-params")
    public ResponseEntity<List<RvOperatorParamDTO>> getAllRvOperatorParams(Pageable pageable) {
        log.debug("REST request to get a page of RvOperatorParams");
        Page<RvOperatorParamDTO> page = rvOperatorParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rv-operator-params/:id} : get the "id" rvOperatorParam.
     *
     * @param id the id of the rvOperatorParamDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvOperatorParamDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rv-operator-params/{id}")
    public ResponseEntity<RvOperatorParamDTO> getRvOperatorParam(@PathVariable Long id) {
        log.debug("REST request to get RvOperatorParam : {}", id);
        Optional<RvOperatorParamDTO> rvOperatorParamDTO = rvOperatorParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rvOperatorParamDTO);
    }

    /**
     * {@code DELETE  /rv-operator-params/:id} : delete the "id" rvOperatorParam.
     *
     * @param id the id of the rvOperatorParamDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rv-operator-params/{id}")
    public ResponseEntity<Void> deleteRvOperatorParam(@PathVariable Long id) {
        log.debug("REST request to delete RvOperatorParam : {}", id);
        rvOperatorParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
