package org.nami.dto;

import lombok.Data;

/**
 * ChangeStatusDTO
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
public class ChangeStatusDTO {

    private Integer id;
    private Byte enabled;
    private String appName;
}
