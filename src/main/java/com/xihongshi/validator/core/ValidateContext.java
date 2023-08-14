package com.xihongshi.validator.core;

import com.xihongshi.validator.model.ValidateItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidateContext {

    private final List<ValidateItem> itemList = new ArrayList<>();

    public void addItem(ValidateItem item) {
        itemList.add(item);
    }

    public void addItems(List<ValidateItem> items) {
        itemList.addAll(items);
    }

    public List<ValidateItem> getItems() {
        return Collections.unmodifiableList(itemList);
    }
}
