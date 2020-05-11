package com.chen.mmall.controller.backend;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.ICategoryService;
import com.chen.mmall.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-13 19:34
 **/
@RestController
@RequestMapping("/manage/category/")
public class CategoryManageController {

    @Autowired
    private IUserService iUserService;

    @Autowired
    private ICategoryService iCategoryService;

    /**
     * 添加分类
     *
     * @param session
     * @param categoryName
     * @param parentId
     * @return
     */
    @RequestMapping(value = "add_category")
    public ServerResponse addCategory(HttpSession session, String categoryName, @RequestParam(value = "parentId", defaultValue = "0") Integer parentId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(currentUser).isSuccess()) {
            return iCategoryService.addCategory(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }

    /**
     * 更新产品分类的名字
     *
     * @param session
     * @param parentId
     * @param categoryName
     * @return
     */
    @RequestMapping(value = "set_category_name")
    public ServerResponse setCategoryName(HttpSession session, Integer parentId, String categoryName) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(currentUser).isSuccess()) {
            //更新品类名
            return iCategoryService.updateCategoryName(categoryName, parentId);
        } else {
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }

    /**
     * 获取商品分类
     * @param session
     * @param parentId
     * @return
     */
    @RequestMapping(value = "get_category")
    public ServerResponse getChildrenParallelCategory(HttpSession session, @RequestParam(value = "categoryId",defaultValue = "0") Integer parentId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(currentUser).isSuccess()) {
            //查询子节点的产品分类信息,并且不递归,保持平级
            return iCategoryService.getChildrenParallelCategory(parentId);
        } else {
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }

    /**
     * 递归获取商品分类
     * @param session
     * @param categoryId
     * @return
     */
    @RequestMapping(value = "get_deep_category")
    public ServerResponse getCategoryAndDeepChildCategory(HttpSession session, @RequestParam(value = "categoryId", defaultValue = "0") Integer categoryId) {
        User currentUser = (User) session.getAttribute(Const.CURRENT_USER);
        if (currentUser == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未登录,请登录");
        }
        if (iUserService.checkAdminRole(currentUser).isSuccess()) {
            //递归查询当前节点Id和递归子节点的ID
            return iCategoryService.selectCategoryAndChildrenById(categoryId);
        } else {
            return ServerResponse.createByErrorMessage("没有操作权限");
        }
    }
}
