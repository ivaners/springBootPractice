package com.code.demo.modules.auth.controllers;

import com.code.demo.common.BasicAction;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.code.demo.common.util.Result;
import com.code.demo.domain.bo.AuthResource;
import com.code.demo.domain.vo.MenuTreeNode;
import com.code.demo.modules.auth.service.ResourceService;
import com.code.demo.common.util.TreeUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/* *
 * @Description 资源URL管理
 * @Date 21:36 2018/3/17
 */
@RestController
@RequestMapping("/resource")
public class ResourceController extends BasicAction {

    @Autowired
    private ResourceService resourceService;

    @ApiOperation(value = "获取用户被授权菜单",notes = "通过uid获取对应用户被授权的菜单列表,获取完整菜单树形结构")
    @GetMapping("authorityMenu")
    public Result getAuthorityMenu(HttpServletRequest request) {
        String uid = request.getHeader("appId");
        List<MenuTreeNode> treeNodes = new ArrayList<>();
        List<AuthResource> resources = resourceService.getAuthorityMenusByUid(uid);

        for (AuthResource resource : resources) {
            MenuTreeNode node = new MenuTreeNode();
            BeanUtils.copyProperties(resource,node);
            treeNodes.add(node);
        }
        List<MenuTreeNode> menuTreeNodes = TreeUtil.buildTreeBy2Loop(treeNodes,-1);
        return Result.ok("return menu list success").put("menuTree",menuTreeNodes);
    }

    @ApiOperation(value = "获取全部菜单列", httpMethod = "GET")
    @GetMapping("menus")
    public Result getMenus() {

        List<MenuTreeNode> treeNodes = new ArrayList<>();
        List<AuthResource> resources = resourceService.getMenus();

        for (AuthResource resource: resources) {
            MenuTreeNode node = new MenuTreeNode();
            BeanUtils.copyProperties(resource,node);
            treeNodes.add(node);
        }
        List<MenuTreeNode> menuTreeNodes = TreeUtil.buildTreeBy2Loop(treeNodes,-1);
        return Result.ok("return menus success").put("menuTree",menuTreeNodes);
    }

    @ApiOperation(value = "增加菜单",httpMethod = "POST")
    @PostMapping("menu")
    public Result addMenu(@RequestBody AuthResource menu ) {

        Boolean flag = resourceService.addMenu(menu);
        if (flag) {
            return Result.ok("add menu success");
        } else {
            return Result.error(1111,"add menu fail");
        }
    }

    @ApiOperation(value = "修改菜单",httpMethod = "PUT")
    @PutMapping("menu")
    public Result updateMenu(@RequestBody AuthResource menu) {

        Boolean flag = resourceService.modifyMenu(menu);
        if (flag) {
            return Result.ok("update menu success");
        } else {
            return Result.error(1111, "update menu fail");
        }
    }

    @ApiOperation(value = "删除菜单", notes = "根据菜单ID删除菜单", httpMethod = "DELETE")
    @DeleteMapping("menu/{menuId}")
    public Result deleteMenuByMenuId(@PathVariable Integer menuId) {

        Boolean flag = resourceService.deleteMenuByMenuId(menuId);
        if (flag) {
            return Result.ok("delete menu success");
        } else {
            return Result.error(1111, "delete menu fail");
        }
    }

    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取API list", notes = "需要分页,根据teamId判断,-1->获取api分类,0->获取全部api,id->获取对应分类id下的api",httpMethod = "GET")
    @GetMapping("api/{teamId}/{currentPage}/{pageSize}")
    public Result getApiList(@PathVariable Integer teamId, @PathVariable Integer currentPage, @PathVariable Integer pageSize) {

        List<AuthResource> resources = null;
        if (teamId == -1) {
            // -1 为获取api分类
            resources = resourceService.getApiTeamList();
            return Result.ok("return apis success").put("data",resources);
        }
        PageHelper.startPage(currentPage, pageSize);
        if (teamId == 0) {
            // 0 为获取全部api
            resources = resourceService.getApiList();
        } else {
            // 其他查询teamId 对应分类下的apis
            resources = resourceService.getApiListByTeamId(teamId);
        }
        PageInfo pageInfo = new PageInfo(resources);
        return Result.ok("return apis success").put("data",pageInfo);
    }

    @ApiOperation(value = "增加API",httpMethod = "POST")
    @PostMapping("api")
    public Result addApi(@RequestBody AuthResource api ) {

        Boolean flag = resourceService.addMenu(api);
        if (flag) {
            return Result.ok("add api success");
        } else {
            return Result.error(1111,"add api fail");
        }
    }

    @ApiOperation(value = "修改API",httpMethod = "PUT")
    @PutMapping("api")
    public Result updateApi(@RequestBody AuthResource api) {

        Boolean flag = resourceService.modifyMenu(api);
        if (flag) {
            return Result.ok("update api success");
        } else {
            return Result.error(1111, "update api fail");
        }
    }

    @ApiOperation(value = "删除API", notes = "根据API_ID删除API", httpMethod = "DELETE")
    @DeleteMapping("api/{apiId}")
    public Result deleteApiByApiId(@PathVariable Integer apiId) {

        Boolean flag = resourceService.deleteMenuByMenuId(apiId);
        if (flag) {
            return Result.ok("delete api success");
        } else {
            return Result.error(1111, "delete api fail");
        }
    }

}
