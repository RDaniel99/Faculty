package com.company;

import com.company.MainFrame;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {
    public static void main(String[] args) throws IntrospectionException, IllegalAccessException, InvocationTargetException {
        MainFrame main = new MainFrame();
        main.setVisible(true);
        Class beanClass = main.getClass();
        BeanInfo info = Introspector.getBeanInfo(beanClass);
        for (PropertyDescriptor pd : info.getPropertyDescriptors()) {
            System.out.println(pd.getName());
            if (pd.getName().equals("class")){
                continue;
            }
            final Method getter = pd.getReadMethod();
            if (getter!=null){
                final Object attrvalue=getter.invoke(main);
                if (attrvalue!=null)System.out.println(attrvalue.toString());
                else System.out.println();
            }
        }
    }
}
