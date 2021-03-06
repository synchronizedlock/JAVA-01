package org.easley.dynamic.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easley.dynamic.entity.MallOrder;
import org.easley.dynamic.mapper.MallOrderMapper;
import org.easley.dynamic.service.MallOrderService;
import org.springframework.stereotype.Service;

/**
 * mall_order表服务实现
 *
 * @author Easley
 * @date 2021/3/7
 * @since 1.0
 */
@Service
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallOrderService {

}
