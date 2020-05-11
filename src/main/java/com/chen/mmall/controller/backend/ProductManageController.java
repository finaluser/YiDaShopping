package com.chen.mmall.controller.backend;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.Product;
import com.chen.mmall.pojo.User;
import com.chen.mmall.service.IFileService;
import com.chen.mmall.service.IProductService;
import com.chen.mmall.service.IUserService;
import com.chen.mmall.util.PropertiesUtil;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

/**
 * @program: mmall
 * @description: 产品Controller
 * @author: Mr.Wang
 * @create: 2020-02-14 15:49
 **/
@RestController
@RequestMapping("/manage/product")
public class ProductManageController {
    @Autowired
    private IUserService iUserService;

    @Autowired
    private IFileService iFileService;

    @Autowired
    private IProductService iProductService;

    /**
     * 产品添加或保存
     *
     * @param session
     * @param product
     * @return
     */
    @GetMapping(value = "/save")
    public ServerResponse productSave(HttpSession session, Product product) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未来登录,请登录");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.saveOrUpdateProduct(product);
        } else {
            return ServerResponse.createByErrorMessage("无操作权限");
        }
    }

    /**
     * 修改产品的状态(上架/下架)
     *
     * @param session
     * @param productId 产品ID
     * @param status    状态
     * @return
     */
    @GetMapping("/set_sale_status")
    public ServerResponse setSaleStatus(HttpSession session, Integer productId, Integer status) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未来登录,请登录");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.setSaltStatus(productId, status);
        } else {
            return ServerResponse.createByErrorMessage("无操作权限");
        }
    }

    /**
     * 获取产品详情
     *
     * @param session
     * @param productId
     * @return
     */
    @GetMapping("/detail")
    public ServerResponse getDetail(HttpSession session, Integer productId) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未来登录,请登录");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.manageProductDetail(productId);
        } else {
            return ServerResponse.createByErrorMessage("无操作权限");
        }
    }


    /**
     * 产品列表的动态分页获取
     *
     * @param session
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping(value = "/list")
    public ServerResponse getList(HttpSession session, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未来登录,请登录");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            return iProductService.getProductList(pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无操作权限");
        }
    }

    /**
     * 产品搜索
     *
     * @param session
     * @param productName 产品名
     * @param productId   产品ID
     * @param pageNum     分页页码
     * @param pageSize    每页大小
     * @return
     */
    @GetMapping(value = "/search")
    public ServerResponse productSearch(HttpSession session, String productName, Integer productId, @RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam(value = "pageSize", defaultValue = "10") int pageSize) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未来登录,请登录");
        }

        if (iUserService.checkAdminRole(user).isSuccess()) {
            //
            return iProductService.searchProduct(productName, productId, pageNum, pageSize);
        } else {
            return ServerResponse.createByErrorMessage("无操作权限");
        }
    }

    /**
     * 上传商品
     *
     * @param session
     * @param file
     * @param request
     * @return
     */
    @GetMapping(value = "/upload")
    public ServerResponse upload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request) {
        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.NEED_LOGIN.getCode(), "用户未来登录,请登录");
        }
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            Map fileMap = Maps.newHashMap();
            fileMap.put("uri", targetFileName);
            fileMap.put("url", url);
            return ServerResponse.createBySuccess(fileMap);
        } else {
            return ServerResponse.createByErrorMessage("无操作权限");
        }
    }

    /**
     * 富文本商品图片上传
     *
     * @param session
     * @param file
     * @param request
     * @param response
     * @return
     */
    @RequestMapping(value = "/richtext_img_upload")
    public Map richTextImgUpload(HttpSession session, @RequestParam(value = "upload_file", required = false) MultipartFile file, HttpServletRequest request, HttpServletResponse response) {
        Map resultMap = Maps.newHashMap();

        User user = (User) session.getAttribute(Const.CURRENT_USER);
        if (user == null) {
            resultMap.put("success", false);
            resultMap.put("msg", "请登录管理员");
            return resultMap;
        }
        // 富文本中
        if (iUserService.checkAdminRole(user).isSuccess()) {
            String path = request.getSession().getServletContext().getRealPath("upload");
            String targetFileName = iFileService.upload(file, path);
            if (!StringUtils.isNotBlank(targetFileName)) {
                resultMap.put("success", false);
                resultMap.put("msg", "上传失败");
                return resultMap;
            }
            String url = PropertiesUtil.getProperty("ftp.server.http.prefix") + targetFileName;
            resultMap.put("success", true);
            resultMap.put("msg", "上传成功");
            resultMap.put("file_path", url);
            response.addHeader("Access-Control-Allow-Headers", "X-File-Name");
            return resultMap;
        } else {
            resultMap.put("success", false);
            resultMap.put("msg", "没有操作权限");
            return resultMap;
        }
    }

}
