package org.easley.dynamic.config.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.executor.keygen.SelectKeyGenerator;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.easley.dynamic.utils.DynamicDataSourceContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationManager;

/**
 * 读写分离的mybatis拦截器实现
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
@Slf4j
@Component
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class})})
public class DynamicDataSourceInterceptor implements Interceptor {

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        String lookupKey;

        // 是否开启了事务
        boolean transactionActive = TransactionSynchronizationManager.isActualTransactionActive();
        // 1.没开事务
        if (!transactionActive) {
            // 获取sql参数列表
            Object[] objects = invocation.getArgs();
            // 第一个参数包含了sql命令的类型
            MappedStatement ms = (MappedStatement) objects[0];
            // a.如果是select
            if (ms.getSqlCommandType().equals(SqlCommandType.SELECT)) {
                if (ms.getId().contains(SelectKeyGenerator.SELECT_KEY_SUFFIX)) {
                    // 且查询条件包含了主键，那走主库，避免刚插入的立刻查从没有
                    lookupKey = DynamicDataSourceContextHolder.DB_MASTER;
                } else {
                    // 否则走从库
                    lookupKey = DynamicDataSourceContextHolder.DB_SLAVE;
                }
            }
            // b.非查询语句（insert、update、delete）走主库
            else {
                lookupKey = DynamicDataSourceContextHolder.DB_MASTER;
            }
        }
        // 2.开了事务，走主库
        else {
            lookupKey = DynamicDataSourceContextHolder.DB_MASTER;
        }
        // 设置当前应该使用的数据源名称
        DynamicDataSourceContextHolder.setLookupKey(lookupKey);

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        if (target instanceof Executor) {
            return Plugin.wrap(target, this);
        }
        return target;
    }
}
