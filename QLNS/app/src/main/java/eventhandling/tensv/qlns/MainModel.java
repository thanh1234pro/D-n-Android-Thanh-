package eventhandling.tensv.qlns;

public class MainModel {
    String name,course,email,turl;
    public  MainModel() {
    }
    public MainModel(String name, String course, String email, String turl) {
        this.name = name;
        this.course = course;
        this.email = email;
        this.turl = turl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTurl() {
        return turl;
    }

    public void setSurl(String Turl) {
        this.turl = turl;
    }
}
