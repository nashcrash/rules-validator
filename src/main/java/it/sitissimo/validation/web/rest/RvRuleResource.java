package it.sitissimo.validation.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.sitissimo.validation.service.RvRuleService;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link it.sitissimo.validation.domain.RvRule}.
 */
@RestController
@RequestMapping("/api")
public class RvRuleResource {
    private final Logger log = LoggerFactory.getLogger(RvRuleResource.class);

    private static final String ENTITY_NAME = "rvRule";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvRuleService rvRuleService;

    public RvRuleResource(RvRuleService rvRuleService) {
        this.rvRuleService = rvRuleService;
    }

    /**
     * {@code POST  /rv-rules} : Create a new rvRule.
     *
     * @param rvRuleDTO the rvRuleDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvRuleDTO, or with status {@code 400 (Bad Request)} if the rvRule has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rv-rules")
    public ResponseEntity<RvRuleDTO> createRvRule(@Valid @RequestBody RvRuleDTO rvRuleDTO) throws URISyntaxException {
        log.debug("REST request to save RvRule : {}", rvRuleDTO);
        if (rvRuleDTO.getId() != null) {
            throw new BadRequestAlertException("A new rvRule cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RvRuleDTO result = rvRuleService.save(rvRuleDTO);
        return ResponseEntity
            .created(new URI("/api/rv-rules/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rv-rules} : Updates an existing rvRule.
     *
     * @param rvRuleDTO the rvRuleDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvRuleDTO,
     * or with status {@code 400 (Bad Request)} if the rvRuleDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvRuleDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rv-rules")
    public ResponseEntity<RvRuleDTO> updateRvRule(@Valid @RequestBody RvRuleDTO rvRuleDTO) throws URISyntaxException {
        log.debug("REST request to update RvRule : {}", rvRuleDTO);
        if (rvRuleDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RvRuleDTO result = rvRuleService.save(rvRuleDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvRuleDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rv-rules} : get all the rvRules.
     *
     * @param pageable  the pagination information.
     * @param eagerload flag to eager load entities from relationships (This is applicable for many-to-many).
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvRules in body.
     */
    @GetMapping("/rv-rules")
    public ResponseEntity<List<RvRuleDTO>> getAllRvRules(
        Pageable pageable,
        @RequestParam(required = false, defaultValue = "false") boolean eagerload,
        @RequestParam(required = false) Long groupId
    ) {
        log.debug("REST request to get a page of RvRules");
        Page<RvRuleDTO> page;
        if (eagerload) {
            page = rvRuleService.findAllWithEagerRelationships(pageable);
        } else if (groupId != null) {
            page = new PageImpl<>(rvRuleService.findAll(groupId));
        } else {
            page = rvRuleService.findAll(pageable);
        }
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rv-rules/:id} : get the "id" rvRule.
     *
     * @param id the id of the rvRuleDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvRuleDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rv-rules/{id}")
    public ResponseEntity<RvRuleDTO> getRvRule(@PathVariable Long id) {
        log.debug("REST request to get RvRule : {}", id);
        Optional<RvRuleDTO> rvRuleDTO = rvRuleService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rvRuleDTO);
    }

    /**
     * {@code DELETE  /rv-rules/:id} : delete the "id" rvRule.
     *
     * @param id the id of the rvRuleDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rv-rules/{id}")
    public ResponseEntity<Void> deleteRvRule(@PathVariable Long id) {
        log.debug("REST request to delete RvRule : {}", id);
        rvRuleService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
