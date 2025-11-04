package hexlet.code.gendiff.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public class KeyComparisonResult {
    private String key;
    private Object defaultValue;
    private Object newValue;
    private Status status;

    private enum Status {
        UNCHANGED,
        UPDATED,
        ADDED,
        DELETED
    }

    public void setIsUnchanged(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.status = Status.UNCHANGED;
    }

    public void setIsUpdate(String key, Object defaultValue, Object newValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.newValue = newValue;
        this.status = Status.UPDATED;
    }

    public void setIsAdd(String key, Object newValue) {
        this.key = key;
        this.newValue = newValue;
        this.status = Status.ADDED;
    }

    public void setIsDelete(String key, Object defaultValue) {
        this.key = key;
        this.defaultValue = defaultValue;
        this.status = Status.DELETED;
    }

    @JsonIgnore
    public boolean isUnchanged() {
        return status == Status.UNCHANGED;
    }

    @JsonIgnore
    public boolean isUpdate() {
        return status == Status.UPDATED;
    }

    @JsonIgnore
    public boolean isAdd() {
        return status == Status.ADDED;
    }

    @JsonIgnore
    public boolean isDelete() {
        return status == Status.DELETED;
    }
}
