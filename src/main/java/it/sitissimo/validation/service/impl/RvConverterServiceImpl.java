package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.RvConverterService;
import it.sitissimo.validation.domain.RvConverter;
import it.sitissimo.validation.repository.RvConverterRepository;
import it.sitissimo.validation.service.dto.RvConverterDTO;
import it.sitissimo.validation.service.mapper.RvConverterMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RvConverter}.
 */
@Service
@Transactional
public class RvConverterServiceImpl implements RvConverterService {

    private final Logger log = LoggerFactory.getLogger(RvConverterServiceImpl.class);

    private final RvConverterRepository rvConverterRepository;

    private final RvConverterMapper rvConverterMapper;

    public RvConverterServiceImpl(RvConverterRepository rvConverterRepository, RvConverterMapper rvConverterMapper) {
        this.rvConverterRepository = rvConverterRepository;
        this.rvConverterMapper = rvConverterMapper;
    }

    @Override
    public RvConverterDTO save(RvConverterDTO rvConverterDTO) {
        log.debug("Request to save RvConverter : {}", rvConverterDTO);
        RvConverter rvConverter = rvConverterMapper.toEntity(rvConverterDTO);
        rvConverter = rvConverterRepository.save(rvConverter);
        return rvConverterMapper.toDto(rvConverter);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RvConverterDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RvConverters");
        return rvConverterRepository.findAll(pageable)
            .map(rvConverterMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RvConverterDTO> findOne(Long id) {
        log.debug("Request to get RvConverter : {}", id);
        return rvConverterRepository.findById(id)
            .map(rvConverterMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RvConverter : {}", id);
        rvConverterRepository.deleteById(id);
    }
}
