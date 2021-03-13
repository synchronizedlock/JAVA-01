package org.easley.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easley.sharding.entity.MallOrder;
import org.easley.sharding.mapper.MallOrderMapper;
import org.easley.sharding.service.MallOrderService;
import org.springframework.stereotype.Service;

/**
 * 订单服务实现
 *
 * @author Easley
 * @date 2021/3/13
 * @since 1.0
 */
@Service
public class MallOrderServiceImpl extends ServiceImpl<MallOrderMapper, MallOrder> implements MallOrderService {

}
