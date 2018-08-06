package com.code.demo.shiro.provider.impl;

import com.code.demo.dao.master.AuthResourceMapper;
import com.code.demo.shiro.provider.ShiroFilterRulesProvider;
import com.code.demo.shiro.rule.RolePermRule;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/* *
 *
 * @Description 
 * @Date 16:46 2018/3/7
 */
@Service("ShiroFilterRulesProvider")
public class ShiroFilterRulesProviderImpl implements ShiroFilterRulesProvider {

    @Autowired
    private AuthResourceMapper authResourceMapper;

    @Override
    public List<RolePermRule> loadRolePermRules() {

        return authResourceMapper.selectRoleRules();
    }

}
