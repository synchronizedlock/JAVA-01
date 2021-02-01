package org.nami.mapstruct;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Generated;
import org.nami.entity.App;
import org.nami.vo.AppVO;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2021-02-01T11:04:17+0800",
    comments = "version: 1.4.1.Final, compiler: javac, environment: Java 1.8.0_271 (Oracle Corporation)"
)
public class AppVOMapStructImpl implements AppVOMapStruct {

    @Override
    public AppVO mapToVO(App app) {
        if ( app == null ) {
            return null;
        }

        AppVO appVO = new AppVO();

        appVO.setId( app.getId() );
        appVO.setAppName( app.getAppName() );
        appVO.setDescription( app.getDescription() );
        appVO.setContextPath( app.getContextPath() );
        appVO.setEnabled( app.getEnabled() );

        appVO.setCreateTime( org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(app.getCreateTime()) );
        appVO.setUpdateTime( org.nami.utils.DateUtils.formatToYYYYMMDDHHmmss(app.getUpdateTime()) );

        return appVO;
    }

    @Override
    public List<AppVO> mapToVOList(List<App> appList) {
        if ( appList == null ) {
            return null;
        }

        List<AppVO> list = new ArrayList<AppVO>( appList.size() );
        for ( App app : appList ) {
            list.add( mapToVO( app ) );
        }

        return list;
    }
}
