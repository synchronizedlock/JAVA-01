package org.easley.common.service;

import org.dromara.hmily.annotation.Hmily;
import org.easley.common.entity.Account;

/**
 * Transfer Service
 *
 * @author Easley
 * @date 2021/5/16
 * @since 1.0
 */
public interface TransMoneyService {

    Account findAccountByUserId(long userId);

    @Hmily
    Boolean transMoney(long userId, Account trans);
}
