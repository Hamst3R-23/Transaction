package main.transaction.model;

import java.math.BigDecimal;

public class Valute {

    private String name; //USD

    private BigDecimal value;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getValue() {
        return value;
    }

    public void setValue(BigDecimal value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Valute{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}
