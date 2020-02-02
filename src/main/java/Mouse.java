import java.nio.file.Path;

public class Mouse {
    private String name;
    private Path location;
    private long secretHidingName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Path getLocation() {
        return location;
    }

    public void setLocation(Path location) {
        this.location = location;
    }

    public long getSecretHidingName() {
        return secretHidingName;
    }

    public void setSecretHidingName(long secretHidingName) {
        this.secretHidingName = secretHidingName;
    }

    @Override
    public String toString(){
       return name + ", also known as " + secretHidingName + ", at " + location;
    }
}
