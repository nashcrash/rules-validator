package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.RvOperatorService;
import it.sitissimo.validation.domain.RvOperator;
import it.sitissimo.validation.repository.RvOperatorRepository;
import it.sitissimo.validation.service.dto.RvOperatorDTO;
import it.sitissimo.validation.service.mapper.RvOperatorMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RvOperator}.
 */
@Service
@Transactional
public class RvOperatorServiceImpl implements RvOperatorService {

    private final Logger log = LoggerFactory.getLogger(RvOperatorServiceImpl.class);

    private final RvOperatorRepository rvOperatorRepository;

    private final RvOperatorMapper rvOperatorMapper;

    public RvOperatorServiceImpl(RvOperatorRepository rvOperatorRepository, RvOperatorMapper rvOperatorMapper) {
        this.rvOperatorRepository = rvOperatorRepository;
        this.rvOperatorMapper = rvOperatorMapper;
    }

    @Override
    public RvOperatorDTO save(RvOperatorDTO rvOperatorDTO) {
        log.debug("Request to save RvOperator : {}", rvOperatorDTO);
        RvOperator rvOperator = rvOperatorMapper.toEntity(rvOperatorDTO);
        rvOperator = rvOperatorRepository.save(rvOperator);
        return rvOperatorMapper.toDto(rvOperator);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RvOperatorDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RvOperators");
        return rvOperatorRepository.findAll(pageable)
            .map(rvOperatorMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RvOperatorDTO> findOne(Long id) {
        log.debug("Request to get RvOperator : {}", id);
        return rvOperatorRepository.findById(id)
            .map(rvOperatorMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RvOperator : {}", id);
        rvOperatorRepository.deleteById(id);
    }
}
