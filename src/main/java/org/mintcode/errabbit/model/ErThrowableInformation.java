package org.mintcode.errabbit.model;

import org.apache.log4j.Category;
import org.apache.log4j.spi.ThrowableInformation;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.util.Arrays;

/**
 * Created by soleaf on 2/21/15.
 */
public class ErThrowableInformation implements Serializable {

    private static final long serialVersionUID = 1L;

    private ErThrowable throwable;
    private ErCategory category;
    private String[] rep;

    public ErThrowableInformation() {
    }

    public static ErThrowableInformation fromThrowableInformation(ThrowableInformation tw) {

        ErThrowableInformation ert = new ErThrowableInformation();
        ert.setThrowable(ErThrowable.formThrowable(tw.getThrowable()));
        ert.setRep(tw.getThrowableStrRep());

        try {
            Field field = tw.getClass().getDeclaredField("category");
            field.setAccessible(true);
            ert.setCategory(ErCategory.fromCategory((Category) field.get(tw)));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return ert;
    }

    public ErThrowable getThrowable() {
        return throwable;
    }

    public void setThrowable(ErThrowable throwable) {
        this.throwable = throwable;
    }

    public ErCategory getCategory() {
        return category;
    }

    public void setCategory(ErCategory category) {
        this.category = category;
    }

    public String[] getRep() {
        return rep;
    }

    public void setRep(String[] rep) {
        this.rep = rep;
    }

    @Override
    public String toString() {
        return "ErThrowableInformation{" +
                "throwable=" + throwable +
                ", category=" + category +
                ", rep=" + Arrays.toString(rep) +
                '}';
    }
}
