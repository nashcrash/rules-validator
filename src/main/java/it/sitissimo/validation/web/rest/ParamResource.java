package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.ParamService;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import it.sitissimo.validation.service.dto.ParamDTO;

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
 * REST controller for managing {@link it.sitissimo.validation.domain.Param}.
 */
@RestController
@RequestMapping("/api")
public class ParamResource {

    private final Logger log = LoggerFactory.getLogger(ParamResource.class);

    private static final String ENTITY_NAME = "param";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ParamService paramService;

    public ParamResource(ParamService paramService) {
        this.paramService = paramService;
    }

    /**
     * {@code POST  /params} : Create a new param.
     *
     * @param paramDTO the paramDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new paramDTO, or with status {@code 400 (Bad Request)} if the param has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/params")
    public ResponseEntity<ParamDTO> createParam(@RequestBody ParamDTO paramDTO) throws URISyntaxException {
        log.debug("REST request to save Param : {}", paramDTO);
        if (paramDTO.getId() != null) {
            throw new BadRequestAlertException("A new param cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ParamDTO result = paramService.save(paramDTO);
        return ResponseEntity.created(new URI("/api/params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /params} : Updates an existing param.
     *
     * @param paramDTO the paramDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated paramDTO,
     * or with status {@code 400 (Bad Request)} if the paramDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the paramDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/params")
    public ResponseEntity<ParamDTO> updateParam(@RequestBody ParamDTO paramDTO) throws URISyntaxException {
        log.debug("REST request to update Param : {}", paramDTO);
        if (paramDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ParamDTO result = paramService.save(paramDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, paramDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /params} : get all the params.
     *
     * @param pageable the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of params in body.
     */
    @GetMapping("/params")
    public ResponseEntity<List<ParamDTO>> getAllParams(Pageable pageable, @RequestParam(required = false, defaultValue = "false") boolean eagerload) {
        log.debug("REST request to get a page of Params");
        Page<ParamDTO> page;
        if (eagerload) {
            page = paramService.findAllWithEagerRelationships(pageable);
        } else {
            page = paramService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /params/:id} : get the "id" param.
     *
     * @param id the id of the paramDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the paramDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/params/{id}")
    public ResponseEntity<ParamDTO> getParam(@PathVariable Long id) {
        log.debug("REST request to get Param : {}", id);
        Optional<ParamDTO> paramDTO = paramService.findOne(id);
        return ResponseUtil.wrapOrNotFound(paramDTO);
    }

    /**
     * {@code DELETE  /params/:id} : delete the "id" param.
     *
     * @param id the id of the paramDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/params/{id}")
    public ResponseEntity<Void> deleteParam(@PathVariable Long id) {
        log.debug("REST request to delete Param : {}", id);
        paramService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
