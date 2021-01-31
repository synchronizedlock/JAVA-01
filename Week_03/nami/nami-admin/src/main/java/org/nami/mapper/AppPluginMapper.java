package org.nami.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.nami.dto.AppPluginDTO;
import org.nami.entity.AppPlugin;

import java.util.List;

/**
 * AppPluginMapper
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
public interface AppPluginMapper extends BaseMapper<AppPlugin> {

    List<AppPluginDTO> queryEnabledPlugins(@Param("appIds") List<Integer> appIds);
}
