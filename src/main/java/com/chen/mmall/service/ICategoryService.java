package com.chen.mmall.service;

import com.chen.mmall.common.ServerResponse;
import com.chen.mmall.pojo.Category;

import java.util.List;

public interface ICategoryService {

    ServerResponse addCategory(String categoryName, Integer parentId);

    ServerResponse updateCategoryName(String categoryName, Integer parentId);

    ServerResponse<List<Category>> getChildrenParallelCategory(Integer parentId);

    ServerResponse<List<Integer>> selectCategoryAndChildrenById(Integer categoryId);
}
