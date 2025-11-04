package hexlet.code.gendiff.utils;


import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KeyComparisonResult {
    private String key;
    private Object defaultValue;
    private Object newValue;
    private int changeStatus;

    public void setIsUnchanged(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.changeStatus = 0;
    }

    public void setIsUpdate(String key, Object defaultValue, Object newValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.newValue = newValue;
        this.changeStatus = 1;
    }

    public void setIsAdd(String key, Object newValue) {
        this.key = key;
        this.newValue = newValue;
        this.changeStatus = 2;
    }

    public void setIsDelete(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.changeStatus = 3;
    }

    public boolean isUnchanged() {
        return changeStatus == 0;
    }

    public boolean isUpdate() {
        return changeStatus == 1;
    }

    public boolean isAdd() {
        return changeStatus == 2;
    }

    public boolean isDelete() {
        return changeStatus == 3;
    }
}
