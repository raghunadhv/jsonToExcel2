package models;

import java.util.ArrayList;
import java.util.List;

public class EntityData {
    private String entityName;
    private String displayName;
    private List<EntityData> entityDataList=new ArrayList<>();
    private List<FieldData> fieldsList =new ArrayList<>();
    private int depth;

    public int getDepth() {
        return depth;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public String getEntityName() {
        return entityName;
    }

    public void setEntityName(String entityName) {
        this.entityName = entityName;
    }

    public List<EntityData> getEntityDataList() {
        return entityDataList;
    }

    public void setEntityDataList(List<EntityData> entityDataList) {
        this.entityDataList = entityDataList;
    }

    public List<FieldData> getFieldsList() {
        return fieldsList;
    }

    public void setFieldsList(List<FieldData> fieldsList) {
        this.fieldsList = fieldsList;
    }

}
