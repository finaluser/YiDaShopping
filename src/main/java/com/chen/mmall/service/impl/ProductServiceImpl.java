package com.chen.mmall.service.impl;

import com.chen.mmall.common.Const;
import com.chen.mmall.common.ResponseCode;
import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.dao.CategoryMapper;
import com.chen.mmall.dao.ProductMapper;
import com.chen.mmall.pojo.Category;
import com.chen.mmall.pojo.Product;
import com.chen.mmall.service.ICategoryService;
import com.chen.mmall.service.IProductService;
import com.chen.mmall.util.DateTimeUtil;
import com.chen.mmall.util.PropertiesUtil;
import com.chen.mmall.vo.ProductDetailVO;
import com.chen.mmall.vo.ProductListVO;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-14 15:54
 **/
@Service(value = "iProductService")
public class ProductServiceImpl implements IProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private ICategoryService iCategoryService;

    public ServerResponse saveOrUpdateProduct(Product product) {
        if (product != null) {
            //如果产品图片不为空,那么第一张图片将设为主图
            if (StringUtils.isNotBlank(product.getSubImages())) {
                String[] subImageArray = product.getSubImages().split(",");
                if (subImageArray.length > 0) {
                    product.setMainImage(subImageArray[0]);
                }
            }

            //如果前端传过来的ID不为空则更新
            if (product.getId() != null) {
                int rowCount = productMapper.updateByPrimaryKey(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("更新产品成功");
                }
                return ServerResponse.createByErrorMessage("更新产品失败");
            } else {
                int rowCount = productMapper.insert(product);
                if (rowCount > 0) {
                    return ServerResponse.createBySuccess("新增产品成功");
                }
                return ServerResponse.createByErrorMessage("新增产品失败");
            }

        }
        return ServerResponse.createByErrorMessage("新增或更新产品参数不正确");
    }


    public ServerResponse<String> setSaltStatus(Integer productId, Integer status) {
        if (productId == null && status == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }
        Product product = new Product();
        product.setId(productId);
        product.setStatus(status);
        int rowCount = productMapper.updateByPrimaryKeySelective(product);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("修改产品状态成功");
        }
        return ServerResponse.createByErrorMessage("修改产品状态失败");
    }


    public ServerResponse<ProductDetailVO> manageProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品不存在或已下架");
        }
        ProductDetailVO productDetailVO = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVO);

    }

    public ServerResponse<PageInfo> getProductList(int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        List<Product> productList = productMapper.selectList();
        List<ProductListVO> listVOList = Lists.newArrayList();

        for (Product p : productList) {
            ProductListVO productListVO = assembleProductListVO(p);
            listVOList.add(productListVO);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(listVOList);
        return ServerResponse.createBySuccess(pageResult);
    }


    public ServerResponse<PageInfo> searchProduct(String productName, Integer productId, int pageNum, int pageSize) {
        PageHelper.startPage(pageNum, pageSize);
        if (StringUtils.isNotBlank(productName)) {
            productName = new StringBuilder().append("%").append(productName).append("%").toString();
        }
        List<Product> productList = productMapper.selectByNameAndProductId(productName, productId);
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product p : productList) {
            ProductListVO productListVO = assembleProductListVO(p);
            productListVOList.add(productListVO);
        }
        PageInfo pageResult = new PageInfo(productList);
        pageResult.setList(productListVOList);
        return ServerResponse.createBySuccess(pageResult);
    }

    public ServerResponse<ProductDetailVO> getProductDetail(Integer productId) {
        if (productId == null) {
            return ServerResponse.createByErrorMessage("参数错误");
        }

        Product product = productMapper.selectByPrimaryKey(productId);
        if (product == null) {
            return ServerResponse.createByErrorMessage("产品不存在或已下架");
        }
        if (product.getStatus() != Const.ProductStatusEnum.ON_SALE.getCode()) {
            return ServerResponse.createByErrorMessage("产品已经下架或删除");
        }
        ProductDetailVO productDetailVO = assembleProductDetailVo(product);
        return ServerResponse.createBySuccess(productDetailVO);
    }


    /**
     * 产品列表的封装
     * @param product
     * @return
     */
    private ProductListVO assembleProductListVO(Product product) {
        ProductListVO productListVO = new ProductListVO();
        productListVO.setId(product.getId());
        productListVO.setName(product.getName());
        productListVO.setCategoryId(product.getCategoryId());
        productListVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));
        productListVO.setMainImage(product.getMainImage());
        productListVO.setPrice(product.getPrice());
        productListVO.setStatus(product.getStatus());
        productListVO.setSubtitle(product.getSubtitle());
        return productListVO;
    }

    /**
     * 产品详情的封装
     * @param product
     * @return
     */
    private ProductDetailVO assembleProductDetailVo(Product product) {
        ProductDetailVO productDetailVO = new ProductDetailVO();
        productDetailVO.setId(product.getId());
        productDetailVO.setSubtitle(product.getSubtitle());
        productDetailVO.setPrice(product.getPrice());
        productDetailVO.setMainImage(product.getMainImage());
        productDetailVO.setSubImage(product.getSubImages());
        productDetailVO.setCategoryId(product.getCategoryId());
        productDetailVO.setDetail(product.getDetail());
        productDetailVO.setName(product.getName());
        productDetailVO.setStatus(product.getStatus());
        productDetailVO.setStock(product.getStock());

        productDetailVO.setImageHost(PropertiesUtil.getProperty("ftp.server.http.prefix", "http://img.happymmall.com/"));

        Category category = categoryMapper.selectByPrimaryKey(product.getCategoryId());

        if (category == null) {
            productDetailVO.setParentCategoryId(0);
        } else {
            productDetailVO.setParentCategoryId(category.getParentId());
        }


        productDetailVO.setCreateTime(DateTimeUtil.dateToStr(product.getCreateTime()));
        productDetailVO.setUpdateTime(DateTimeUtil.dateToStr(product.getUpdateTime()));
        return productDetailVO;
    }

    public ServerResponse<PageInfo> getProductByKeywordCategory(String keyword, Integer categoryId,
                                                                int pageNum, int pageSize, String orderBy) {
        if (StringUtils.isBlank(keyword) && categoryId == null) {
            return ServerResponse.createByErrorCodeMessage(ResponseCode.ILLEGAL_ARGUMENT.getCode(), ResponseCode.ILLEGAL_ARGUMENT.getDesc());
        }

        List<Integer> categoryIdList = new ArrayList<>();

        if (categoryId != null) {
            Category category = categoryMapper.selectByPrimaryKey(categoryId);
            if (category == null && StringUtils.isBlank(keyword)) {
                // 没有分类和关键字的话不报错,返回一个空的结果集
                PageHelper.startPage(pageNum, pageSize);
                List<ProductListVO> productListVOList = Lists.newArrayList();
                PageInfo pageInfo = new PageInfo(productListVOList);
                return ServerResponse.createBySuccess(pageInfo);
            }
            if(category != null){
                categoryIdList = iCategoryService.selectCategoryAndChildrenById(category.getId()).getData();
            }
        }
        // 组装关键字
        if (StringUtils.isNotBlank(keyword)) {
            keyword = new StringBuilder().append("%").append(keyword).append("%").toString();
        }

        PageHelper.startPage(pageNum, pageSize);
        // OrderBy排序处理
        if (StringUtils.isNotBlank(orderBy)) {
            if (Const.ProductListOrderBy.PRICE_ASC_DESC.contains(orderBy)) {
                String[] orderByArray = orderBy.split("_");
                PageHelper.orderBy(orderByArray[0] + " " + orderByArray[1]);
            }
        }

        List<Product> productList = productMapper.selectByNameAndCategoryIds(StringUtils.isBlank(keyword) ? null : keyword, categoryIdList.size() == 0 ? null : categoryIdList);
        List<ProductListVO> productListVOList = Lists.newArrayList();
        for (Product product : productList
        ) {
            ProductListVO productListVO = assembleProductListVO(product);
            productListVOList.add(productListVO);
        }
        PageInfo pageInfo = new PageInfo(productList);
        pageInfo.setList(productListVOList);

        return ServerResponse.createBySuccess(pageInfo);

    }


}
