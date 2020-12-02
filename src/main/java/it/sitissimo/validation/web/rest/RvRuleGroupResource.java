package it.sitissimo.validation.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import it.sitissimo.validation.service.RvRuleGroupService;
import it.sitissimo.validation.service.dto.RvRuleGroupDTO;
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
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * REST controller for managing {@link it.sitissimo.validation.domain.RvRuleGroup}.
 */
@RestController
@RequestMapping("/api")
public class RvRuleGroupResource {
    private final Logger log = LoggerFactory.getLogger(RvRuleGroupResource.class);

    private static final String ENTITY_NAME = "rvRuleGroup";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final RvRuleGroupService rvRuleGroupService;

    public RvRuleGroupResource(RvRuleGroupService rvRuleGroupService) {
        this.rvRuleGroupService = rvRuleGroupService;
    }

    /**
     * {@code POST  /rv-rule-groups} : Create a new rvRuleGroup.
     *
     * @param rvRuleGroupDTO the rvRuleGroupDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new rvRuleGroupDTO, or with status {@code 400 (Bad Request)} if the rvRuleGroup has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/rv-rule-groups")
    public ResponseEntity<RvRuleGroupDTO> createRvRuleGroup(@Valid @RequestBody RvRuleGroupDTO rvRuleGroupDTO) throws URISyntaxException {
        log.debug("REST request to save RvRuleGroup : {}", rvRuleGroupDTO);
        if (rvRuleGroupDTO.getId() != null) {
            throw new BadRequestAlertException("A new rvRuleGroup cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RvRuleGroupDTO result = rvRuleGroupService.save(rvRuleGroupDTO);
        return ResponseEntity
            .created(new URI("/api/rv-rule-groups/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /rv-rule-groups} : Updates an existing rvRuleGroup.
     *
     * @param rvRuleGroupDTO the rvRuleGroupDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated rvRuleGroupDTO,
     * or with status {@code 400 (Bad Request)} if the rvRuleGroupDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the rvRuleGroupDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/rv-rule-groups")
    public ResponseEntity<RvRuleGroupDTO> updateRvRuleGroup(@Valid @RequestBody RvRuleGroupDTO rvRuleGroupDTO) throws URISyntaxException {
        log.debug("REST request to update RvRuleGroup : {}", rvRuleGroupDTO);
        if (rvRuleGroupDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RvRuleGroupDTO result = rvRuleGroupService.save(rvRuleGroupDTO);
        return ResponseEntity
            .ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, rvRuleGroupDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /rv-rule-groups} : get all the rvRuleGroups.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of rvRuleGroups in body.
     */
    @GetMapping("/rv-rule-groups")
    public ResponseEntity<List<RvRuleGroupDTO>> getAllRvRuleGroups(Pageable pageable) {
        log.debug("REST request to get a page of RvRuleGroups");
        Page<RvRuleGroupDTO> page = rvRuleGroupService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /rv-rule-groups/:id} : get the "id" rvRuleGroup.
     *
     * @param id the id of the rvRuleGroupDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the rvRuleGroupDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/rv-rule-groups/{id}")
    public ResponseEntity<RvRuleGroupDTO> getRvRuleGroup(@PathVariable Long id) {
        log.debug("REST request to get RvRuleGroup : {}", id);
        Optional<RvRuleGroupDTO> rvRuleGroupDTO = rvRuleGroupService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rvRuleGroupDTO);
    }

    /**
     * {@code DELETE  /rv-rule-groups/:id} : delete the "id" rvRuleGroup.
     *
     * @param id the id of the rvRuleGroupDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/rv-rule-groups/{id}")
    public ResponseEntity<Void> deleteRvRuleGroup(@PathVariable Long id) {
        log.debug("REST request to delete RvRuleGroup : {}", id);
        rvRuleGroupService.delete(id);
        return ResponseEntity
            .noContent()
            .headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString()))
            .build();
    }
}
