package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.ParameterService;
import it.sitissimo.validation.domain.Parameter;
import it.sitissimo.validation.repository.ParameterRepository;
import it.sitissimo.validation.service.dto.ParameterDTO;
import it.sitissimo.validation.service.mapper.ParameterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Parameter}.
 */
@Service
@Transactional
public class ParameterServiceImpl implements ParameterService {

    private final Logger log = LoggerFactory.getLogger(ParameterServiceImpl.class);

    private final ParameterRepository parameterRepository;

    private final ParameterMapper parameterMapper;

    public ParameterServiceImpl(ParameterRepository parameterRepository, ParameterMapper parameterMapper) {
        this.parameterRepository = parameterRepository;
        this.parameterMapper = parameterMapper;
    }

    @Override
    public ParameterDTO save(ParameterDTO parameterDTO) {
        log.debug("Request to save Parameter : {}", parameterDTO);
        Parameter parameter = parameterMapper.toEntity(parameterDTO);
        parameter = parameterRepository.save(parameter);
        return parameterMapper.toDto(parameter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParameterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Parameters");
        return parameterRepository.findAll(pageable)
            .map(parameterMapper::toDto);
    }


    public Page<ParameterDTO> findAllWithEagerRelationships(Pageable pageable) {
        return parameterRepository.findAllWithEagerRelationships(pageable).map(parameterMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParameterDTO> findOne(Long id) {
        log.debug("Request to get Parameter : {}", id);
        return parameterRepository.findOneWithEagerRelationships(id)
            .map(parameterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Parameter : {}", id);
        parameterRepository.deleteById(id);
    }
}
