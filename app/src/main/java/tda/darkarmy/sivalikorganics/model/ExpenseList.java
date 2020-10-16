package tda.darkarmy.sivalikorganics.model;

import java.io.Serializable;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "expenses"
})
@SuppressWarnings("serial")
public class ExpenseList implements Serializable {

    @JsonProperty("expenses")
    private List<Expense> expenses = null;

    @JsonProperty("expenses")
    public List<Expense> getExpenses() {
        return expenses;
    }

    @JsonProperty("expenses")
    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }

    @Override
    public String toString() {
        return "ExpenseList{" +
                "expenses=" + expenses +
                '}';
    }
}
