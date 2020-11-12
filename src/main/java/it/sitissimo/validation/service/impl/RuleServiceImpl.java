package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.domain.Rule;
import it.sitissimo.validation.repository.RuleRepository;
import it.sitissimo.validation.service.RuleService;
import it.sitissimo.validation.service.dto.RuleDTO;
import it.sitissimo.validation.service.mapper.RuleMapper;
import java.util.Optional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Service Implementation for managing {@link Rule}.
 */
@Service
@Transactional
public class RuleServiceImpl implements RuleService {
    private final Logger log = LoggerFactory.getLogger(RuleServiceImpl.class);

    private final RuleRepository ruleRepository;

    private final RuleMapper ruleMapper;

    public RuleServiceImpl(RuleRepository ruleRepository, RuleMapper ruleMapper) {
        this.ruleRepository = ruleRepository;
        this.ruleMapper = ruleMapper;
    }

    @Override
    public RuleDTO save(RuleDTO ruleDTO) {
        log.debug("Request to save Rule : {}", ruleDTO);
        Rule rule = ruleMapper.toEntity(ruleDTO);
        rule = ruleRepository.save(rule);
        return ruleMapper.toDto(rule);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RuleDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Rules");
        return ruleRepository.findAll(pageable).map(ruleMapper::toDto);
    }

    public Page<RuleDTO> findAllWithEagerRelationships(Pageable pageable) {
        return ruleRepository.findAllWithEagerRelationships(pageable).map(ruleMapper::toDto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<RuleDTO> findOne(Long id) {
        log.debug("Request to get Rule : {}", id);
        return ruleRepository.findOneWithEagerRelationships(id).map(ruleMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete Rule : {}", id);
        ruleRepository.deleteById(id);
    }
}
