package com.code.demo.modules.auth.controllers;

import com.code.demo.common.BasicAction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.code.demo.common.util.Result;
import com.code.demo.domain.bo.AuthResource;
import com.code.demo.domain.bo.AuthRole;
import com.code.demo.domain.bo.AuthUser;
import com.code.demo.modules.auth.service.ResourceService;
import com.code.demo.modules.auth.service.RoleService;
import com.code.demo.modules.auth.service.UserService;
import com.code.demo.shiro.filter.ShiroFilterChainManager;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/* *
 * @Description
 * @Date 20:02 2018/3/20
 */
@RequestMapping("/role")
@RestController
public class RoleController extends BasicAction {

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private ResourceService resourceService;

    @Autowired
    private ShiroFilterChainManager shiroFilterChainManager;

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色关联的(roleId)对应用户列表",httpMethod = "GET")
    @GetMapping("user/{roleId}/{currentPage}/{pageSize}")
    public Result getUserListByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage,pageSize);
        List<AuthUser> users = userService.getUserListByRoleId(roleId);
        users.forEach(user->user.setPassword(null));
        PageInfo pageInfo = new PageInfo(users);
        return Result.ok("return users success").put("data",pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色未关联的用户列表", httpMethod = "GET")
    @GetMapping("user/-/{roleId}/{currentPage}/{pageSize}")
    public Result getUserListExtendByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthUser> users = userService.getNotAuthorityUserListByRoleId(roleId);
        users.forEach(user -> user.setPassword(null));
        PageInfo pageInfo = new PageInfo(users);
        return Result.ok("return users success").put("data", pageInfo);
    }


    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)所被授权的API资源")
    @GetMapping("api/{roleId}/{currentPage}/{pageSize}")
    public Result getRestApiExtendByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getAuthorityApisByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return Result.ok("return api success").put("data", pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)未被授权的API资源")
    @GetMapping("api/-/{roleId}/{currentPage}/{pageSize}")
    public Result getRestApiByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getNotAuthorityApisByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return Result.ok("return api success").put("data", pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)所被授权的menu资源")
    @GetMapping("menu/{roleId}/{currentPage}/{pageSize}")
    public Result getMenusByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getAuthorityMenusByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return Result.ok("return api success").put("data", pageInfo);
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色(roleId)未被授权的menu资源")
    @GetMapping("menu/-/{roleId}/{currentPage}/{pageSize}")
    public Result getMenusExtendByRoleId(@PathVariable Integer roleId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        List<AuthResource> authResources = resourceService.getNotAuthorityMenusByRoleId(roleId);
        PageInfo pageInfo = new PageInfo(authResources);
        return Result.ok("return api success").put("data", pageInfo);
    }

    @ApiOperation(value = "授权资源给角色",httpMethod = "POST")
    @PostMapping("/authority/resource")
    public Result authorityRoleResource(HttpServletRequest request) {
        Map<String,String> map = getRequestBody(request);
        int roleId = Integer.valueOf(String.valueOf(map.get("roleId")));
        int resourceId = Integer.valueOf(String.valueOf(map.get("resourceId")));
        boolean flag = roleService.authorityRoleResource(roleId,resourceId);
        shiroFilterChainManager.reloadFilterChain();
        return flag ? Result.ok("authority success") : Result.error(1111,"authority error");
    }

    @ApiOperation(value = "删除对应的角色的授权资源",httpMethod = "DELETE")
    @DeleteMapping("/authority/resource/{roleId}/{resourceId}")
    public Result deleteAuthorityRoleResource(@PathVariable Integer roleId, @PathVariable Integer resourceId ) {
        boolean flag = roleService.deleteAuthorityRoleResource(roleId,resourceId);
        shiroFilterChainManager.reloadFilterChain();
        return flag ? Result.ok("authority success") : Result.error(1111,"authority error");
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取角色LIST", httpMethod = "GET")
    @GetMapping("{currentPage}/{pageSize}")
    public Result getRoles(@PathVariable Integer currentPage, @PathVariable Integer pageSize) {

        PageHelper.startPage(currentPage, pageSize);
        List<AuthRole> roles = roleService.getRoleList();
        PageInfo pageInfo = new PageInfo(roles);
        return Result.ok("return roles success").put("data", pageInfo);
    }

    @ApiOperation(value = "添加角色", httpMethod = "POST")
    @PostMapping("")
    public Result addRole(@RequestBody AuthRole role) {

        boolean flag = roleService.addRole(role);
        if (flag) {
            return Result.ok("add role success");
        } else {
            return Result.error(111, "add role fail");
        }
    }

    @ApiOperation(value = "更新角色", httpMethod = "PUT")
    @PutMapping("")
    public Result updateRole(@RequestBody AuthRole role) {

        boolean flag = roleService.updateRole(role);
        if (flag) {
            return Result.ok("update success");
        } else {
            return Result.error(1111, "update fail");
        }
    }

    @ApiOperation(value = "根据角色ID删除角色", httpMethod = "DELETE")
    @DeleteMapping("{roleId}")
    public Result deleteRoleByRoleId(@PathVariable Integer roleId) {

        boolean flag = roleService.deleteRoleByRoleId(roleId);
        if (flag) {
            return Result.ok("delete success");
        } else {
            return Result.error(1111, "delete fail");
        }
    }


}
