package com.benzeng;

import javax.swing.table.AbstractTableModel;
import javax.swing.table.DefaultTableModel;
import java.util.ArrayList;
import java.util.List;

public abstract class ObjectTableModel<T> extends AbstractTableModel { //DisplayableObjectTableModel, but for rows this time.
    private List<T> objectRows = new ArrayList<>();

    public List<T> getObjectRows() {
        return objectRows;
    }

    public void setObjectRows(List<T> objectRows) {
        this.objectRows = objectRows;
    }

    @Override
    public int getRowCount() {
        return objectRows.size();
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        T t = objectRows.get(rowIndex);
        return getValueAt(t, columnIndex);
    }


    public abstract Object getValueAt(T t, int columnIndex);

    @Override
    public abstract String getColumnName(int column);

    public abstract String getFieldName(int column);



    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        T t = objectRows.get(rowIndex);
        if(t instanceof Editable){
            if(!((Editable) t).isEditable()){
                return false;
            }
        }
        return isColumnEditable(columnIndex);
    }

    public void setValueAt(Object value, int row, int column) {
        if(!isCellEditable(row, column)){
            return;
        }
        T t = objectRows.get(row);
        if(setObjectFieldValue(t, column, value)){
            fireTableCellUpdated(row, column);
        }
    }

    //Couple defining variables as well as booleans that allow editing of cells
    public abstract boolean isColumnEditable(int columnIndex);
    public abstract boolean setObjectFieldValue(T t, int column, Object value);

}
