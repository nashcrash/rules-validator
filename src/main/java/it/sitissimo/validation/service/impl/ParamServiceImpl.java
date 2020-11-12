package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.ParamService;
import it.sitissimo.validation.domain.Param;
import it.sitissimo.validation.repository.ParamRepository;
import it.sitissimo.validation.service.dto.ParamDTO;
import it.sitissimo.validation.service.mapper.ParamMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Param}.
 */
@Service
@Transactional
public class ParamServiceImpl implements ParamService {

    private final Logger log = LoggerFactory.getLogger(ParamServiceImpl.class);

    private final ParamRepository paramRepository;

    private final ParamMapper paramMapper;

    public ParamServiceImpl(ParamRepository paramRepository, ParamMapper paramMapper) {
        this.paramRepository = paramRepository;
        this.paramMapper = paramMapper;
    }

    @Override
    public ParamDTO save(ParamDTO paramDTO) {
        log.debug("Request to save Param : {}", paramDTO);
        Param param = paramMapper.toEntity(paramDTO);
        param = paramRepository.save(param);
        return paramMapper.toDto(param);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<ParamDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Params");
        return paramRepository.findAll(pageable)
            .map(paramMapper::toDto);
    }


    public Page<ParamDTO> findAllWithEagerRelationships(Pageable pageable) {
        return paramRepository.findAllWithEagerRelationships(pageable).map(paramMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<ParamDTO> findOne(Long id) {
        log.debug("Request to get Param : {}", id);
        return paramRepository.findOneWithEagerRelationships(id)
            .map(paramMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Param : {}", id);
        paramRepository.deleteById(id);
    }
}
