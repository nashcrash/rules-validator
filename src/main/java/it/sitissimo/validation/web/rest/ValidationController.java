package it.sitissimo.validation.web.rest;

import it.sitissimo.validation.service.ValidationService;
import it.sitissimo.validation.service.dto.RvValidationRequestDTO;
import it.sitissimo.validation.service.dto.RvValidationResultDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.net.URISyntaxException;

@Controller
@RequestMapping("/api")
public class ValidationController {

    @Autowired
    private ValidationService validationService;

    @PostMapping("/validate")
    public ResponseEntity<RvValidationResultDTO> validate(@Valid @RequestBody RvValidationRequestDTO validationRequestDTO) throws URISyntaxException {
        RvValidationResultDTO result=validationService.validate(validationRequestDTO);
        return ResponseEntity.ok().body(result);
    }
}
