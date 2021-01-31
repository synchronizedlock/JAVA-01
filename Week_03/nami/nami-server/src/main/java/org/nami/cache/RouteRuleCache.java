package org.nami.cache;

import org.nami.dto.AppRuleDTO;
import org.nami.exception.NamiException;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

/**
 * RouteRuleCache
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public class RouteRuleCache {

    /**
     * route rule config cache map
     * key: appName
     */
    private static final Map<String, CopyOnWriteArrayList<AppRuleDTO>> ROUTE_RULE_MAP = new ConcurrentHashMap<>();

    /**
     * add rule to cache map
     */
    public static void add(Map<String, List<AppRuleDTO>> map) {
        map.forEach((key, value) -> ROUTE_RULE_MAP.put(key, new CopyOnWriteArrayList(value)));
    }

    /**
     * remove rule from cache map
     */
    public static void remove(Map<String, List<AppRuleDTO>> map) {
        for (Map.Entry<String, List<AppRuleDTO>> entry : map.entrySet()) {
            String appName = entry.getKey();
            List<Integer> ruleIds = entry.getValue().stream().map(AppRuleDTO::getId).collect(Collectors.toList());
            CopyOnWriteArrayList<AppRuleDTO> ruleDTOs = ROUTE_RULE_MAP.getOrDefault(appName, new CopyOnWriteArrayList<>());
            ruleDTOs.removeIf(r -> ruleIds.contains(r.getId()));
            if (CollectionUtils.isEmpty(ruleDTOs)) {
                // remove all
                ROUTE_RULE_MAP.remove(appName);
            } else {
                // remove some of them
                ROUTE_RULE_MAP.put(appName, ruleDTOs);
            }
        }
    }


    /**
     * get rules by appName
     */
    public static List<AppRuleDTO> getRules(String appName) {
        return Optional.ofNullable(ROUTE_RULE_MAP.get(appName))
                .orElseThrow(() -> new NamiException("please config route rule in ship-admin first!"));
    }
}
