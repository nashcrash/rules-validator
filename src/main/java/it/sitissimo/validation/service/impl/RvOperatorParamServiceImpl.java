package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.domain.RvOperatorParam;
import it.sitissimo.validation.repository.RvOperatorParamRepository;
import it.sitissimo.validation.service.RvOperatorParamService;
import it.sitissimo.validation.service.dto.RvOperatorParamDTO;
import it.sitissimo.validation.service.mapper.RvOperatorParamMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link RvOperatorParam}.
 */
@Service
@Transactional
public class RvOperatorParamServiceImpl implements RvOperatorParamService {
    private final Logger log = LoggerFactory.getLogger(RvOperatorParamServiceImpl.class);

    private final RvOperatorParamRepository rvOperatorParamRepository;

    private final RvOperatorParamMapper rvOperatorParamMapper;

    public RvOperatorParamServiceImpl(RvOperatorParamRepository rvOperatorParamRepository, RvOperatorParamMapper rvOperatorParamMapper) {
        this.rvOperatorParamRepository = rvOperatorParamRepository;
        this.rvOperatorParamMapper = rvOperatorParamMapper;
    }

    @Override
    public RvOperatorParamDTO save(RvOperatorParamDTO rvOperatorParamDTO) {
        log.debug("Request to save RvOperatorParam : {}", rvOperatorParamDTO);
        RvOperatorParam rvOperatorParam = rvOperatorParamMapper.toEntity(rvOperatorParamDTO);
        rvOperatorParam = rvOperatorParamRepository.save(rvOperatorParam);
        return rvOperatorParamMapper.toDto(rvOperatorParam);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RvOperatorParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RvOperatorParams");
        return rvOperatorParamRepository.findAll(pageable).map(rvOperatorParamMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RvOperatorParamDTO> findOne(Long id) {
        log.debug("Request to get RvOperatorParam : {}", id);
        return rvOperatorParamRepository.findById(id).map(rvOperatorParamMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RvOperatorParam : {}", id);
        rvOperatorParamRepository.deleteById(id);
    }
}
