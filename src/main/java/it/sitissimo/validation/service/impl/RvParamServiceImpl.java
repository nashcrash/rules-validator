package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.RvParamService;
import it.sitissimo.validation.domain.RvParam;
import it.sitissimo.validation.repository.RvParamRepository;
import it.sitissimo.validation.service.dto.RvParamDTO;
import it.sitissimo.validation.service.mapper.RvParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RvParam}.
 */
@Service
@Transactional
public class RvParamServiceImpl implements RvParamService {

    private final Logger log = LoggerFactory.getLogger(RvParamServiceImpl.class);

    private final RvParamRepository rvParamRepository;

    private final RvParamMapper rvParamMapper;

    public RvParamServiceImpl(RvParamRepository rvParamRepository, RvParamMapper rvParamMapper) {
        this.rvParamRepository = rvParamRepository;
        this.rvParamMapper = rvParamMapper;
    }

    @Override
    public RvParamDTO save(RvParamDTO rvParamDTO) {
        log.debug("Request to save RvParam : {}", rvParamDTO);
        RvParam rvParam = rvParamMapper.toEntity(rvParamDTO);
        rvParam = rvParamRepository.save(rvParam);
        return rvParamMapper.toDto(rvParam);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RvParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RvParams");
        return rvParamRepository.findAll(pageable)
            .map(rvParamMapper::toDto);
    }


    public Page<RvParamDTO> findAllWithEagerRelationships(Pageable pageable) {
        return rvParamRepository.findAllWithEagerRelationships(pageable).map(rvParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RvParamDTO> findOne(Long id) {
        log.debug("Request to get RvParam : {}", id);
        return rvParamRepository.findOneWithEagerRelationships(id)
            .map(rvParamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RvParam : {}", id);
        rvParamRepository.deleteById(id);
    }
}
