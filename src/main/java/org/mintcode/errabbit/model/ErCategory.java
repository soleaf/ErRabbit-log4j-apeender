package org.mintcode.errabbit.model;

import org.apache.log4j.Category;

import java.io.Serializable;

/**
 * Created by soleaf on 2/21/15.
 */
public class ErCategory implements Serializable{

    private static final long serialVersionUID = 1L;

    private String name;
    private ErLevel level;

    public ErCategory(){

    }

    public static ErCategory fromCategory(Category category){

        if (category == null){
            return null;
        }

        ErCategory erCategory = new ErCategory();
        erCategory.setName(category.getName());
        erCategory.setLevel(ErLevel.fromLevel(category.getLevel()));

        return erCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ErLevel getLevel() {
        return level;
    }

    public void setLevel(ErLevel level) {
        this.level = level;
    }

    @Override
    public String toString() {
        return "ErCategory{" +
                "name='" + name + '\'' +
//                ", parent=" + parent +
                ", level=" + level +
                '}';
    }
}
