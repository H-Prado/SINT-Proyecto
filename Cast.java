package p2;

public class Cast implements Comparable<Cast> {
    private String name;
    private String id;
    private String role;
    private String contact;

    public Cast(String name, String id, String role, String contact) {
        this.name = name;
        this.id = id;
        this.role = role;
        this.contact = contact;
    }

    public String getName() {
        return this.name;
    }

    public String getId() {
        return this.id;
    }

    public String getRole() {
        return this.role;
    }

    public String getContact() {
        return this.contact;
    }

    public void setId(String id){
        this.id = id;
    }

    public void setRole(String role){
        this.role = role;
    }

    public void setContact(String contact){
        this.contact = contact;
    }

    public void setName(String name){
        this.name = name;
    }

    public int compareTo(Cast cast) {
        return this.getName().compareTo(cast.name);
    }

}