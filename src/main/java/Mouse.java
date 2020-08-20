import lombok.Getter;
import lombok.Setter;

import java.nio.file.Path;

@Getter
@Setter
public class Mouse {
    private String name;
    private Path location;
    private long secretHidingName;

    @Override
    public String toString() {
        return name + ", also known as " + secretHidingName + ", at " + location;
    }
}
