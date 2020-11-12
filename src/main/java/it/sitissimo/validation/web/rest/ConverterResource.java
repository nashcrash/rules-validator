package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.ConverterService;
import it.sitissimo.validation.web.rest.errors.BadRequestAlertException;
import it.sitissimo.validation.service.dto.ConverterDTO;

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
 * REST controller for managing {@link it.sitissimo.validation.domain.Converter}.
 */
@RestController
@RequestMapping("/api")
public class ConverterResource {

    private final Logger log = LoggerFactory.getLogger(ConverterResource.class);

    private static final String ENTITY_NAME = "converter";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ConverterService converterService;

    public ConverterResource(ConverterService converterService) {
        this.converterService = converterService;
    }

    /**
     * {@code POST  /converters} : Create a new converter.
     *
     * @param converterDTO the converterDTO to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new converterDTO, or with status {@code 400 (Bad Request)} if the converter has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/converters")
    public ResponseEntity<ConverterDTO> createConverter(@Valid @RequestBody ConverterDTO converterDTO) throws URISyntaxException {
        log.debug("REST request to save Converter : {}", converterDTO);
        if (converterDTO.getId() != null) {
            throw new BadRequestAlertException("A new converter cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ConverterDTO result = converterService.save(converterDTO);
        return ResponseEntity.created(new URI("/api/converters/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /converters} : Updates an existing converter.
     *
     * @param converterDTO the converterDTO to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated converterDTO,
     * or with status {@code 400 (Bad Request)} if the converterDTO is not valid,
     * or with status {@code 500 (Internal Server Error)} if the converterDTO couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/converters")
    public ResponseEntity<ConverterDTO> updateConverter(@Valid @RequestBody ConverterDTO converterDTO) throws URISyntaxException {
        log.debug("REST request to update Converter : {}", converterDTO);
        if (converterDTO.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ConverterDTO result = converterService.save(converterDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, converterDTO.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /converters} : get all the converters.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of converters in body.
     */
    @GetMapping("/converters")
    public ResponseEntity<List<ConverterDTO>> getAllConverters(Pageable pageable) {
        log.debug("REST request to get a page of Converters");
        Page<ConverterDTO> page = converterService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /converters/:id} : get the "id" converter.
     *
     * @param id the id of the converterDTO to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the converterDTO, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/converters/{id}")
    public ResponseEntity<ConverterDTO> getConverter(@PathVariable Long id) {
        log.debug("REST request to get Converter : {}", id);
        Optional<ConverterDTO> converterDTO = converterService.findOne(id);
        return ResponseUtil.wrapOrNotFound(converterDTO);
    }

    /**
     * {@code DELETE  /converters/:id} : delete the "id" converter.
     *
     * @param id the id of the converterDTO to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/converters/{id}")
    public ResponseEntity<Void> deleteConverter(@PathVariable Long id) {
        log.debug("REST request to delete Converter : {}", id);
        converterService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
