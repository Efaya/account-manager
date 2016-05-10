package fr.efaya;

import fr.efaya.domain.AccountRecord;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by KTIFA FAMILY on 07/05/2016.
 */
public class MonthResponse implements Serializable {
    private List<AccountRecord> records;
    private Integer month;
    private double sumIncomes;
    private double sumOutcomes;
    private Map<String, List<AccountRecord>> categorizedIncomes;
    private Map<String, List<AccountRecord>> categorizedOutcomes;

    public List<AccountRecord> getRecords() {
        return records;
    }

    public void setRecords(List<AccountRecord> records) {
        this.records = records;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }

    public double getSumIncomes() {
        return sumIncomes;
    }

    public void setSumIncomes(double sumIncomes) {
        this.sumIncomes = sumIncomes;
    }

    public double getSumOutcomes() {
        return sumOutcomes;
    }

    public void setSumOutcomes(double sumOutcomes) {
        this.sumOutcomes = sumOutcomes;
    }

    public Map<String, List<AccountRecord>> getCategorizedIncomes() {
        return categorizedIncomes;
    }

    public void setCategorizedIncomes(Map<String, List<AccountRecord>> categorizedIncomes) {
        this.categorizedIncomes = categorizedIncomes;
    }

    public Map<String, List<AccountRecord>> getCategorizedOutcomes() {
        return categorizedOutcomes;
    }

    public void setCategorizedOutcomes(Map<String, List<AccountRecord>> categorizedOutcomes) {
        this.categorizedOutcomes = categorizedOutcomes;
    }
}
