package org.nami.dto;


import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Payload
 *
 * @author Easley
 * @date 2021/1/31
 * @since 1.0
 */
@Data
@AllArgsConstructor
public class Payload {

    private Integer userId;
    private String name;
}
