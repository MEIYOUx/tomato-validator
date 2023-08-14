package com.xihongshi.validator.core;

import com.xihongshi.validator.model.ValidationItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ValidationContext {

    private final List<ValidationItem> itemList = new ArrayList<>();

    public void addItem(ValidationItem item) {
        itemList.add(item);
    }

    public void addItems(List<ValidationItem> items) {
        itemList.addAll(items);
    }

    public List<ValidationItem> getItems() {
        return Collections.unmodifiableList(itemList);
    }
}
