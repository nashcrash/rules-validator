package it.sitissimo.validation.service.impl;

import it.sitissimo.validation.service.RvRuleGroupService;
import it.sitissimo.validation.domain.RvRuleGroup;
import it.sitissimo.validation.repository.RvRuleGroupRepository;
import it.sitissimo.validation.service.dto.RvRuleGroupDTO;
import it.sitissimo.validation.service.mapper.RvRuleGroupMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing {@link RvRuleGroup}.
 */
@Service
@Transactional
public class RvRuleGroupServiceImpl implements RvRuleGroupService {

    private final Logger log = LoggerFactory.getLogger(RvRuleGroupServiceImpl.class);

    private final RvRuleGroupRepository rvRuleGroupRepository;

    private final RvRuleGroupMapper rvRuleGroupMapper;

    public RvRuleGroupServiceImpl(RvRuleGroupRepository rvRuleGroupRepository, RvRuleGroupMapper rvRuleGroupMapper) {
        this.rvRuleGroupRepository = rvRuleGroupRepository;
        this.rvRuleGroupMapper = rvRuleGroupMapper;
    }

    @Override
    public RvRuleGroupDTO save(RvRuleGroupDTO rvRuleGroupDTO) {
        log.debug("Request to save RvRuleGroup : {}", rvRuleGroupDTO);
        RvRuleGroup rvRuleGroup = rvRuleGroupMapper.toEntity(rvRuleGroupDTO);
        rvRuleGroup = rvRuleGroupRepository.save(rvRuleGroup);
        return rvRuleGroupMapper.toDto(rvRuleGroup);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<RvRuleGroupDTO> findAll(Pageable pageable) {
        log.debug("Request to get all RvRuleGroups");
        return rvRuleGroupRepository.findAll(pageable)
            .map(rvRuleGroupMapper::toDto);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<RvRuleGroupDTO> findOne(Long id) {
        log.debug("Request to get RvRuleGroup : {}", id);
        return rvRuleGroupRepository.findById(id)
            .map(rvRuleGroupMapper::toDto);
    }

    @Override
    public void delete(Long id) {
        log.debug("Request to delete RvRuleGroup : {}", id);
        rvRuleGroupRepository.deleteById(id);
    }
}
