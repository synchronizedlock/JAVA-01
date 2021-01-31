package org.nami.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.google.common.collect.Lists;
import org.nami.dto.AppRuleDTO;
import org.nami.dto.ChangeStatusDTO;
import org.nami.dto.RuleDTO;
import org.nami.entity.App;
import org.nami.entity.AppInstance;
import org.nami.entity.RouteRule;
import org.nami.enums.EnabledEnum;
import org.nami.enums.MatchMethodEnum;
import org.nami.enums.MatchObjectEnum;
import org.nami.enums.NamiExceptionEnum;
import org.nami.event.RuleAddEvent;
import org.nami.event.RuleDeleteEvent;
import org.nami.exception.NamiException;
import org.nami.mapper.AppInstanceMapper;
import org.nami.mapper.AppMapper;
import org.nami.mapper.RouteRuleMapper;
import org.nami.mapstruct.AppRuleVOMapStruct;
import org.nami.mapstruct.RuleVOMapStruct;
import org.nami.service.RuleService;
import org.nami.vo.RuleVO;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * RuleServiceImpl
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Service
public class RuleServiceImpl implements RuleService {

    @Resource
    private RouteRuleMapper ruleMapper;

    @Resource
    private AppMapper appMapper;

    @Resource
    private AppInstanceMapper instanceMapper;

    @Resource
    private ApplicationEventPublisher eventPublisher;

    @Override
    public List<AppRuleDTO> getEnabledRule() {
        QueryWrapper<App> wrapper = Wrappers.query();
        wrapper.lambda().eq(App::getEnabled, EnabledEnum.ENABLE.getCode());
        List<App> apps = appMapper.selectList(wrapper);
        if (CollectionUtils.isEmpty(apps)) {
            return new ArrayList<>();
        }

        List<Integer> appIds = apps.stream().map(App::getId).collect(Collectors.toList());
        Map<Integer, String> nameMap = apps.stream().collect(Collectors.toMap(App::getId, App::getAppName));

        QueryWrapper<RouteRule> query = Wrappers.query();
        query.lambda().in(RouteRule::getAppId, appIds)
                .eq(RouteRule::getEnabled, EnabledEnum.ENABLE.getCode());
        List<RouteRule> routeRules = ruleMapper.selectList(query);

        List<AppRuleDTO> appRuleDTOS = AppRuleVOMapStruct.INSTANCE.mapToVOList(routeRules);
        appRuleDTOS.forEach(r -> r.setAppName(nameMap.get(r.getAppId())));
        return appRuleDTOS;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void add(RuleDTO ruleDTO) {
        checkRule(ruleDTO);

        RouteRule routeRule = new RouteRule();
        BeanUtils.copyProperties(ruleDTO, routeRule);
        routeRule.setCreateTime(LocalDateTime.now());
        ruleMapper.insert(routeRule);

        App app = appMapper.selectById(ruleDTO.getAppId());
        if (EnabledEnum.ENABLE.getCode().equals(ruleDTO.getEnabled())) {
            AppRuleDTO appRuleDTO = new AppRuleDTO();
            BeanUtils.copyProperties(routeRule, appRuleDTO);
            appRuleDTO.setAppName(app.getAppName());
            eventPublisher.publishEvent(new RuleAddEvent(this, appRuleDTO));
        }
    }

    private void checkRule(RuleDTO ruleDTO) {
        QueryWrapper<RouteRule> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(RouteRule::getName, ruleDTO.getName());
        if (!CollectionUtils.isEmpty(ruleMapper.selectList(wrapper))) {
            throw new NamiException("规则名称不能重复");
        }

        if (MatchObjectEnum.DEFAULT.getCode().equals(ruleDTO.getMatchObject())) {
            ruleDTO.setMatchKey(null);
            ruleDTO.setMatchMethod(null);
            ruleDTO.setMatchRule(null);
        } else {
            if (StringUtils.isEmpty(ruleDTO.getMatchKey()) || ruleDTO.getMatchMethod() == null
                    || StringUtils.isEmpty(ruleDTO.getMatchRule())) {
                throw new NamiException(NamiExceptionEnum.PARAM_ERROR);
            }
        }

        // check version
        QueryWrapper<AppInstance> query = Wrappers.query();
        query.lambda().eq(AppInstance::getAppId,ruleDTO.getAppId())
            .eq(AppInstance::getVersion,ruleDTO.getVersion());

        List<AppInstance> list = instanceMapper.selectList(query);
        if (CollectionUtils.isEmpty(list)){
            throw new NamiException("实例版本不存在");
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void delete(Integer id) {
        RouteRule routeRule = ruleMapper.selectById(id);
        if (routeRule == null) {
            throw new NamiException(NamiExceptionEnum.PARAM_ERROR);
        }

        AppRuleDTO appRuleDTO = new AppRuleDTO();
        BeanUtils.copyProperties(routeRule, appRuleDTO);
        App app = appMapper.selectById(appRuleDTO.getAppId());
        appRuleDTO.setAppName(app.getAppName());

        ruleMapper.deleteById(id);
        eventPublisher.publishEvent(new RuleDeleteEvent(this, appRuleDTO));
    }

    @Override
    public List<RuleVO> queryList(String appName) {
        Integer appId = null;
        if (!StringUtils.isEmpty(appName)) {
            App app = queryByAppName(appName);
            if (app == null) {
                return Lists.newArrayList();
            }
            appId = app.getId();
        }

        QueryWrapper<RouteRule> query = Wrappers.query();
        query.lambda().eq(Objects.nonNull(appId), RouteRule::getAppId, appId)
        .orderByDesc(RouteRule::getCreateTime);
        List<RouteRule> rules = ruleMapper.selectList(query);
        if (CollectionUtils.isEmpty(rules)) {
            return Lists.newArrayList();
        }

        List<RuleVO> ruleVOs = RuleVOMapStruct.INSTANCE.mapToVOList(rules);
        Map<Integer, String> nameMap = getAppNameMap(ruleVOs.stream().map(r -> r.getAppId()).collect(Collectors.toList()));
        ruleVOs.forEach(ruleVO -> {
            ruleVO.setAppName(nameMap.get(ruleVO.getAppId()));
            ruleVO.setMatchStr(buildMatchStr(ruleVO));
        });
        return ruleVOs;
    }

    private String buildMatchStr(RuleVO ruleVO) {
        if (MatchObjectEnum.DEFAULT.getCode().equals(ruleVO.getMatchObject())){
            return ruleVO.getMatchObject();
        }else {
            StringBuilder sb = new StringBuilder();
            sb.append("[").append(ruleVO.getMatchKey()).append("] ");
            sb.append(MatchMethodEnum.getByCode(ruleVO.getMatchMethod()).getDesc());
            sb.append(" ["+ruleVO.getMatchRule()+"]");
            return sb.toString();
        }
    }

    private Map<Integer, String> getAppNameMap(List<Integer> appIdList) {
        QueryWrapper<App> query = Wrappers.query();
        query.lambda().in(App::getId, appIdList);

        List<App> apps = appMapper.selectList(query);
        return apps.stream().collect(Collectors.toMap(App::getId, App::getAppName));
    }

    private App queryByAppName(String appName) {
        QueryWrapper<App> wrapper = new QueryWrapper<>();
        wrapper.lambda().eq(App::getAppName, appName);

        return appMapper.selectOne(wrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void changeStatus(ChangeStatusDTO statusDTO) {
        RouteRule routeRule = new RouteRule();
        routeRule.setId(statusDTO.getId());
        routeRule.setEnabled(statusDTO.getEnabled());
        ruleMapper.updateById(routeRule);

        AppRuleDTO appRuleDTO = new AppRuleDTO();
        BeanUtils.copyProperties(routeRule, appRuleDTO);
        appRuleDTO.setAppName(statusDTO.getAppName());

        if (EnabledEnum.ENABLE.getCode().equals(statusDTO.getEnabled())) {
            eventPublisher.publishEvent(new RuleAddEvent(this, appRuleDTO));
        } else {
            eventPublisher.publishEvent(new RuleDeleteEvent(this, appRuleDTO));
        }
    }
}
