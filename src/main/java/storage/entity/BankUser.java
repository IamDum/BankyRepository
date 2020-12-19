package storage.entity;

import javax.persistence.*;

@Entity
@Table(name = "bankuser_new_3")
public class BankUser {
    @Id
    @SequenceGenerator(name = "id_seq_user_new_3")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)

    private Long id;
    @Column(unique = true)
    private String name;
    private String password;
    private boolean admin;
    private int account;


    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }


    public boolean isAdmin() { return admin;}
    public void setAdmin(boolean admin) {
        this.admin = admin;
    }


    public String getPassword() {
        return this.password;
    }
    public void setPassword(String password) {
        this.password = password;
    }


    public Integer getAccount() {
        return this.account;
    }
    public void setAccount(Integer account) {
        this.account = account;
    }


}





