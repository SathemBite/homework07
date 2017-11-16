package task1;

import java.io.Serializable;

/**
 * Created by anton on 16.11.17.
 */
public class Account implements Serializable {
    private int amount;
    private final int id;
    private static int IDer = 0;

    public Account(int initAmount){
        id = IDer++;
        amount = initAmount;
    }

    public Account(){
        this(0);
    }

    public int getId(){
        return id;
    }

    public void deposit(int deposAmount){
        amount += deposAmount;
    }

    public void withdraw(int withDrwAmount){
        amount -= withDrwAmount;
    }

    @Override
    public String toString(){
        return "Account #" + id + " : " + amount;
    }
}
