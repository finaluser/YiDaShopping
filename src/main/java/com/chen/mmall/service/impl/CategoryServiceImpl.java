package com.chen.mmall.service.impl;

import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.dao.CategoryMapper;
import com.chen.mmall.pojo.Category;
import com.chen.mmall.service.ICategoryService;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Set;

/**
 * @program: mmall
 * @description:
 * @author: Mr.Wang
 * @create: 2020-02-13 19:46
 **/
@Service(value = "iCategoryService")
public class CategoryServiceImpl implements ICategoryService {

    private static Logger logger = LoggerFactory.getLogger(CategoryServiceImpl.class);

    @Autowired
    private CategoryMapper categoryMapper;

    public ServerResponse addCategory(String categoryName, Integer parentId) {
        if (StringUtils.isBlank(categoryName) && parentId == null) {
            return ServerResponse.createByErrorMessage("添加品类失败,参数错误");
        }

        Category category = new Category();
        category.setParentId(parentId);

        System.out.println(categoryName);

        category.setName(categoryName);
        category.setStatus(true);

        int rowCount = categoryMapper.insert(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("添加品类成功");
        }
        return ServerResponse.createByErrorMessage("添加品类失败");
    }

    public ServerResponse updateCategoryName(String categoryName, Integer parentId) {
        if (StringUtils.isBlank(categoryName) && parentId == null) {
            return ServerResponse.createByErrorMessage("添加品类失败,参数错误");
        }

        Category category = new Category();
        category.setId(parentId);
        category.setName(categoryName);

        int rowCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (rowCount > 0) {
            return ServerResponse.createBySuccess("产品分类名字更新成功");
        }
        return ServerResponse.createByErrorMessage("产品名字更新失败");
    }

    public ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId) {
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(parentId);
        if (CollectionUtils.isEmpty(categoryList)) {
            logger.info("未找到当前分类的子类");
        }
        return ServerResponse.createBySuccess(categoryList);
    }

    /**
     * 递归查询某节点和其子节点的ID
     *
     * @param parentId 节点ID
     * @return
     */
    public ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId) {
        Set<Category> categorySet = Sets.newHashSet();
        findChildCategory(categorySet, categoryId);

        List<Integer> categoryIdList = Lists.newArrayList();
        if (categoryId != null) {
            for (Category categoryItem : categorySet) {
                categoryIdList.add(categoryItem.getId());
            }
        }
        return ServerResponse.createBySuccess(categoryIdList);

    }


    //递归遍历子节点
    private Set<Category> findChildCategory(Set<Category> categorySet, Integer categoryId) {
        Category category = categoryMapper.selectByPrimaryKey(categoryId);
        if (category != null) {
            categorySet.add(category);
        }
        List<Category> categoryList = categoryMapper.selectCategoryChildrenByParentId(categoryId);
        for (Category categoryItem : categoryList) {
            findChildCategory(categorySet, categoryItem.getId());
        }
        return categorySet;
    }


}
