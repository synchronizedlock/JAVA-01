package org.easley.provider.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.easley.common.entity.FreezeAccount;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

@Repository
public interface FreezeAccountMapper {

    @Select("select * from freeze_account where user_id = #{userId}")
    FreezeAccount findByUserId(long userId);

    @Update("update freeze_account set usd = usd - #{money} where user_id = #{userId}")
    int subUsd(@Param("userId") long userId, @Param("money") BigDecimal money);

    @Update("update freeze_account set usd = usd + #{money} where user_id = #{userId}")
    int addUsd(@Param("userId") long userId, @Param("money") BigDecimal money);

    @Update("update freeze_account set cny = cny - #{money} where user_id = #{userId}")
    int subCny(@Param("userId") long userId, @Param("money") BigDecimal money);

    @Update("update freeze_account set cny = cny + #{money} where user_id = #{userId}")
    int addCny(@Param("userId") long userId, @Param("money") BigDecimal money);
}
