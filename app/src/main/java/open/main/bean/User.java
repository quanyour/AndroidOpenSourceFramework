package open.main.bean;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

/**
 * Created by Cain on 2017/6/6.
 *
 */

@Entity
public class User {
    @Id
    private Long id;
    private String name;
    private int age;
    private int money;
    private String address;

    @Generated(hash = 1122203377)
    public User(Long id, String name, int age, int money, String address) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.money = money;
        this.address = address;
    }

    @Generated(hash = 586692638)
    public User() {
    }

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

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", age=" + age +
                ", money=" + money +
                ", address='" + address + '\'' +
                '}';
    }
}
