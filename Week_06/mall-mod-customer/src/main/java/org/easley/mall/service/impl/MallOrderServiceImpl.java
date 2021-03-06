package org.easley.mall.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easley.mall.entity.MallOrder;
import org.easley.mall.mapper.MallOrderMapper;
import org.easley.mall.service.MallOrderService;
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
