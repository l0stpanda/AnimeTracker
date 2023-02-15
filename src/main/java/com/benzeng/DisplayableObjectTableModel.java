package com.benzeng;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DisplayableObjectTableModel<T> extends ObjectTableModel<T> { //Table Creation

    private Map<Integer, ColumnInfo> columnInfoMap;

    public DisplayableObjectTableModel(Class<T> tClass) {
        init(tClass);
    }

    private void init(Class<T> tClass) {
        try { //Try Catch for errors
            BeanInfo beanInfo = Introspector.getBeanInfo(tClass);
            this.columnInfoMap = new HashMap<>();
            for (PropertyDescriptor pd : beanInfo.getPropertyDescriptors()) {
                Method m = pd.getReadMethod();
                DisplayAs displayAs = m.getAnnotation(DisplayAs.class);
                if (displayAs == null) {
                    continue;
                }
                ColumnInfo columnInfo = new ColumnInfo(); //this part just allows everything to be editable
                columnInfo.displayName = displayAs.value();
                columnInfo.index = displayAs.index();
                columnInfo.editable = displayAs.editable();
                if (displayAs.editable()) {
                    columnInfo.setterMethod = pd.getWriteMethod();
                }
                columnInfo.getterMethod = m;
                columnInfo.propertyName = pd.getName();
                columnInfoMap.put(columnInfo.index, columnInfo);
            }

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Object getValueAt(T t, int columnIndex) { //Gets value at the cell
        try {
            return columnInfoMap.get(columnIndex).getterMethod.invoke(t);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getColumnCount() {
        return columnInfoMap.size();
    } //Column count

    @Override
    public String getColumnName(int column) { //Column name
        ColumnInfo columnInfo = columnInfoMap.get(column);
        if (columnInfo == null) {
            throw new RuntimeException("No column found for index " + column);
        }
        return columnInfo.displayName;
    }
    public Class<?> getColumnClass(int columnIndex) {
        ColumnInfo columnInfo = columnInfoMap.get(columnIndex);
        return columnInfo.getterMethod.getReturnType();
    }

    @Override
    public String getFieldName(int column) { //Name of the field
        ColumnInfo columnInfo = columnInfoMap.get(column);
        return columnInfo.propertyName;
    }


    @Override
    public boolean isColumnEditable(int columnIndex) { //Determines if column is editable
        ColumnInfo columnInfo = columnInfoMap.get(columnIndex);
        return columnInfo.editable;
    }

    @Override
    public boolean setObjectFieldValue(T t, int column, Object value) { //Sets field value of columns
        ColumnInfo columnInfo = columnInfoMap.get(column);
        try {
            if (columnInfo.setterMethod != null) {
                columnInfo.setterMethod.invoke(t, value);
                return true;
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return false;
    }




}
