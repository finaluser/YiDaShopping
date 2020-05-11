package com.chen.mmall.controller.portal;

import com.github.pagehelper.PageInfo;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.service.IProductService;
import com.chen.mmall.vo.ProductDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: mmall
 * @description: 产品Controller
 * @author: Mr.Wang
 * @create: 2020-02-17 20:32
 **/
@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService iProductService;

    /**
     * 获取产品详情
     *
     * @param productId
     * @return
     */
    @RequestMapping("/detail")
    public ServerResponse<ProductDetailVO> detail(Integer productId) {
        return iProductService.getProductDetail(productId);
    }

    /**
     * 前端产品搜索
     *
     * @param keyword    产品关键字
     * @param categoryId 根据分类搜索
     * @param pageNum    分页页码
     * @param pageSize   分页每页的数量
     * @param orderBy    搜索排序
     * @return
     */
    @RequestMapping("/list")
    public ServerResponse<PageInfo> list(@RequestParam(value = "keyword", required = false) String keyword,
                                         @RequestParam(value = "categoryId", required = false) Integer categoryId,
                                         @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
                                         @RequestParam(value = "orderBy", defaultValue = "") String orderBy) {

        return iProductService.getProductByKeywordCategory(keyword, categoryId, pageNum, pageSize, orderBy);
    }
}
