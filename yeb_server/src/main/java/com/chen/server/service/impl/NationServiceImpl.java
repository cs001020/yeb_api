package com.chen.server.service.impl;

import com.chen.server.pojo.Nation;
import com.chen.server.mapper.NationMapper;
import com.chen.server.service.NationService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author CHEN
 * @since 2022-09-29
 */
@Service
public class NationServiceImpl extends ServiceImpl<NationMapper, Nation> implements NationService {

}
