package model.classes;

public abstract class User {
    private String name, cellphone, email, password;
    private Integer id;

    public User(String name, String cellphone, String email, String password, Integer id) {
        this.name = name;
        this.cellphone = cellphone;
        this.email = email;
        this.password = password;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCellphone() {
        return cellphone;
    }

    public void setCellphone(String cellphone) {
        this.cellphone = cellphone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getId() {
        if(id == 2){
        return id;

        }
    }

    public void setId(Integer id) {
        this.id = id;
    }
}
