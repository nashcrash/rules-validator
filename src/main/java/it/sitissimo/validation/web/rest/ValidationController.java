package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.domain.enumeration.EFormat;
import it.sitissimo.validation.service.ValidationService;
import it.sitissimo.validation.service.dto.RvValidationRequestDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDTO;
import it.sitissimo.validation.service.errors.ValidationException;
import java.net.URISyntaxException;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class ValidationController {
    @Autowired
    private ValidationService validationService;

    @PutMapping("/validate")
    public ResponseEntity<RvValidationResultDTO> validate(@Valid @RequestBody RvValidationRequestDTO validationRequestDTO)
        throws URISyntaxException, ValidationException {
        RvValidationResultDTO result = validationService.validate(EFormat.JSON, validationRequestDTO);
        return ResponseEntity.ok().body(result);
    }

    @PutMapping("/validate/{format}")
    public ResponseEntity<RvValidationResultDTO> validateFormat(
        @PathVariable("format") EFormat format,
        @Valid @RequestBody RvValidationRequestDTO validationRequestDTO
    )
        throws URISyntaxException, ValidationException {
        RvValidationResultDTO result = validationService.validate(format, validationRequestDTO);
        return ResponseEntity.ok().body(result);
    }
}
