package be.pxl.ja.streamingservice.repository;

import be.pxl.ja.streamingservice.exception.DuplicateEmailException;
import be.pxl.ja.streamingservice.model.Account;

import java.util.List;

public class AccountRepository {
    private List<Account> accounts;

    public void addAccount(Account account) throws DuplicateEmailException {
        if (accounts.get(0) != null){
            int check = 0;
            for(Account acc : accounts){
                if(acc.getEmail().equals(account.getEmail())){
                    check = 1;
                }
            }
            if (check == 0){
                accounts.add(account);
            }
            else {
                throw new DuplicateEmailException();
            }
        }
    }

    public Account findAccount(String email){
        int index = -1;
        for(Account acc : accounts){
            if(acc.getEmail().equals(email)){
                index = accounts.indexOf(acc);
            }
        }
        if (index == -1){
            return null;
        }else {
            return accounts.get(index);
        }
    }
}
