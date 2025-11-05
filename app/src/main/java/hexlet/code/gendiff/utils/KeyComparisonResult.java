package hexlet.code.gendiff.utils;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Getter
public final class KeyComparisonResult {
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

    /**
     * Marks this comparison result as UNCHANGED - the key exists in both data sets with the same value.
     *
     * @param keyVal the key being compared
     * @param defVal the value from both data sets (same in default and new)
     */
    public void setIsUnchanged(String keyVal, Object defVal) {
        this.key = keyVal;
        this.defaultValue = defVal;
        this.status = Status.UNCHANGED;
    }

    /**
     * Marks this comparison result as UPDATED - the key exists in both data sets but with different values.
     *
     * @param keyVal the key being compared
     * @param defVal the value from the first/default data set
     * @param newVal the value from the second/new data set
     */
    public void setIsUpdate(String keyVal, Object defVal, Object newVal) {
        this.key = keyVal;
        this.defaultValue = defVal;
        this.newValue = newVal;
        this.status = Status.UPDATED;
    }

    /**
     * Marks this comparison result as ADDED - the key exists only in the second data set.
     *
     * @param keyVal the key that was added
     * @param newVal the value from the second/new data set
     */
    public void setIsAdd(String keyVal, Object newVal) {
        this.key = keyVal;
        this.newValue = newVal;
        this.status = Status.ADDED;
    }

    /**
     * Marks this comparison result as DELETED - the key exists only in the first data set.
     *
     * @param keyVal the key that was deleted
     * @param defVal the value from the first/default data set
     */
    public void setIsDelete(String keyVal, Object defVal) {
        this.key = keyVal;
        this.defaultValue = defVal;
        this.status = Status.DELETED;
    }

    /**
     * Checks if the key is unchanged between data sets.
     *
     * @return true if the key exists in both data sets with identical values
     */
    @JsonIgnore
    public boolean isUnchanged() {
        return status == Status.UNCHANGED;
    }

    /**
     * Checks if the key was updated between data sets.
     *
     * @return true if the key exists in both data sets but with different values
     */
    @JsonIgnore
    public boolean isUpdate() {
        return status == Status.UPDATED;
    }

    /**
     * Checks if the key was added in the second data set.
     *
     * @return true if the key exists only in the second data set
     */
    @JsonIgnore
    public boolean isAdd() {
        return status == Status.ADDED;
    }

    /**
     * Checks if the key was deleted from the second data set.
     *
     * @return true if the key exists only in the first data set
     */
    @JsonIgnore
    public boolean isDelete() {
        return status == Status.DELETED;
    }
}
