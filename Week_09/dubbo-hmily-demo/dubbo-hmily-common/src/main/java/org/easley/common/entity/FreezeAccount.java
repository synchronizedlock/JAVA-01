package org.easley.common.entity;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * FreezeAccount entity
 *
 * @author Easley
 * @date 2021/5/16
 * @since 1.0
 */
@Data
@ToString
public class FreezeAccount implements Serializable {
    private static final long serialVersionUID = 1L;

    private long userId;
    private BigDecimal usd;
    private BigDecimal cny;
}
