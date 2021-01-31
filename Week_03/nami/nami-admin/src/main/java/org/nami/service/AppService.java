package org.nami.service;

import org.nami.dto.AppInfoDTO;
import org.nami.dto.ChangeStatusDTO;
import org.nami.dto.RegisterAppDTO;
import org.nami.dto.UnregisterAppDTO;
import org.nami.vo.AppVO;

import java.util.List;

/**
 * AppService
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface AppService {

    void register(RegisterAppDTO registerAppDTO);

    void unregister(UnregisterAppDTO unregisterAppDTO);

    List<AppInfoDTO> getAppInfos(List<String> appNames);

    List<AppVO> getList();

    void updateEnabled(ChangeStatusDTO statusDTO);

    void delete(Integer id);
}
