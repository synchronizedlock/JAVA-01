package org.easley.demo.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.easley.demo.mapper.GirlMapper;
import org.easley.demo.entity.Girl;
import org.easley.demo.service.GirlService;
import org.springframework.stereotype.Service;

/**
 * Girl服务实现
 *
 * @author Easley
 * @date 2021/2/21
 * @since 1.0
 */
@Service
public class GirlServiceImpl extends ServiceImpl<GirlMapper, Girl> implements GirlService {
}
