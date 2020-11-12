package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.ConverterService;
import it.sitissimo.validation.domain.Converter;
import it.sitissimo.validation.repository.ConverterRepository;
import it.sitissimo.validation.service.dto.ConverterDTO;
import it.sitissimo.validation.service.mapper.ConverterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Converter}.
 */
@Service
@Transactional
public class ConverterServiceImpl implements ConverterService {

    private final Logger log = LoggerFactory.getLogger(ConverterServiceImpl.class);

    private final ConverterRepository converterRepository;

    private final ConverterMapper converterMapper;

    public ConverterServiceImpl(ConverterRepository converterRepository, ConverterMapper converterMapper) {
        this.converterRepository = converterRepository;
        this.converterMapper = converterMapper;
    }

    @Override
    public ConverterDTO save(ConverterDTO converterDTO) {
        log.debug("Request to save Converter : {}", converterDTO);
        Converter converter = converterMapper.toEntity(converterDTO);
        converter = converterRepository.save(converter);
        return converterMapper.toDto(converter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ConverterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Converters");
        return converterRepository.findAll(pageable)
            .map(converterMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<ConverterDTO> findOne(Long id) {
        log.debug("Request to get Converter : {}", id);
        return converterRepository.findById(id)
            .map(converterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Converter : {}", id);
        converterRepository.deleteById(id);
    }
}
