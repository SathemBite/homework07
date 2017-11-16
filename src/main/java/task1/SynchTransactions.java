package task1;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by anton on 16.11.17.
 */
public class SynchTransactions {
    public static synchronized void deposit(int accId, int amount){
        System.out.println("Thread #" + Thread.currentThread() + " in deposit");
        ArrayList<Account> account = readAccounts();
        account.stream().
                filter(acc -> acc.getId() == accId).
                forEach(acc -> acc.deposit(amount));
        writeAccounts(account);
    }

    public static synchronized void withdraw(int accId, int amount) {
        ArrayList<Account> account = readAccounts();
        account.stream().
                filter(acc -> acc.getId() == accId).
                forEach(acc -> acc.withdraw(amount));
        writeAccounts(account);
    }

    public static synchronized void createAccount(int initialAmount){
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account(initialAmount));
        writeAccounts(accounts);

    }

    private static ArrayList<Account> readAccounts(){
        ArrayList<Account> accounts = new ArrayList<>();
        try(ObjectInputStream iner =
                    new ObjectInputStream(
                            new FileInputStream("resources/accounts"))){

            accounts = (ArrayList<Account>) iner.readObject();

        } catch(IOException ex){
            throw new RuntimeException(ex);
        } catch(ClassNotFoundException ex){
            throw new RuntimeException(ex);
        }

        return accounts;
    }

    private static void writeAccounts(ArrayList<Account> accounts){
        try (ObjectOutputStream outer =
                     new ObjectOutputStream(
                             new FileOutputStream("resources/accounts"))){

            outer.writeObject(accounts);

        } catch(IOException ex){
            throw new RuntimeException(ex);
        }
    }
}
