package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.domain.RvRule;
import it.sitissimo.validation.repository.RvRuleRepository;
import it.sitissimo.validation.service.RvRuleService;
import it.sitissimo.validation.service.dto.RvRuleDTO;
import it.sitissimo.validation.service.mapper.RvRuleMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RvRule}.
 */
@Service
@Transactional
public class RvRuleServiceImpl implements RvRuleService {

    private final Logger log = LoggerFactory.getLogger(RvRuleServiceImpl.class);

    private final RvRuleRepository rvRuleRepository;

    private final RvRuleMapper rvRuleMapper;

    public RvRuleServiceImpl(RvRuleRepository rvRuleRepository, RvRuleMapper rvRuleMapper) {
        this.rvRuleRepository = rvRuleRepository;
        this.rvRuleMapper = rvRuleMapper;
    }

    @Override
    public RvRuleDTO save(RvRuleDTO rvRuleDTO) {
        log.debug("Request to save RvRule : {}", rvRuleDTO);
        RvRule rvRule = rvRuleMapper.toEntity(rvRuleDTO);
        rvRule = rvRuleRepository.save(rvRule);
        return rvRuleMapper.toDto(rvRule);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RvRuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RvRules");
        return rvRuleRepository.findAll(pageable)
            .map(rvRuleMapper::toDto);
    }


    public Page<RvRuleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return rvRuleRepository.findAllWithEagerRelationships(pageable).map(rvRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RvRuleDTO> findOne(Long id) {
        log.debug("Request to get RvRule : {}", id);
        return rvRuleRepository.findOneWithEagerRelationships(id)
            .map(rvRuleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RvRuleDTO> findOne(String ruleCode) {
        log.debug("Request to get RvRule : {}", ruleCode);
        return rvRuleRepository.findOneWithEagerRelationships(ruleCode)
            .map(rvRuleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RvRule : {}", id);
        rvRuleRepository.deleteById(id);
    }
}
