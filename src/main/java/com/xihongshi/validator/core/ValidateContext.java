package com.xihongshi.validator.core;

import com.xihongshi.validator.model.ValidateItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 验证上下文，每个上下文中包含了一个验证项的List。
 * @see ValidateItem
 * @author iuhay
 */
public class ValidateContext {

    /**
     * 保存验证项的 List
     */
    private final List<ValidateItem> itemList = new ArrayList<>();

    /**
     * 添加验证项。
     * @param item 验证项
     */
    public void addItem(ValidateItem item) {
        itemList.add(item);
    }

    /**
     * 批量添加验证项。
     * @param items 验证项 List
     */
    public void addItems(List<ValidateItem> items) {
        itemList.addAll(items);
    }

    /**
     * 获取验证项 List
     * @return 不可修改的验证项 List
     */
    public List<ValidateItem> getItems() {
        return Collections.unmodifiableList(itemList);
    }
}
