package id.fathi.admindifcom;

public class Admin {

    private String id, email, password, nama;

    public Admin(String email, String password, String nama) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.nama = nama;
    }

    public Admin() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }
}
