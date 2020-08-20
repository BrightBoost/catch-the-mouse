import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuditionTaskCatchTheMouse {

    private static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    //Oh no! There's a mouse walking through my living room (there actually is one and I can see him while typing this ugh....)
    //Please help me with the little piece of Java to catch him
    //It will also help you learning about Strings, loops, converting to a number
    //and reading and writing files
    //and walking paths!
    //I know it's a bit random, but please help me with this mouse

    public static long changeNameToSecretName(String name) {
        //write here the code to get the secret name, replace the letters of the mouse name letter by letter.
        //the letter should be replaced with the corresponding number of that letter in the alphabet.
        String value = "";
        for (char c : name.toLowerCase().toCharArray()) {
            if (ALPHABET.indexOf(c) != -1) {
                value += Character.getNumericValue(c) - 9;
            }
        }
        //replace this with the secret name
        return Long.parseLong(value);
    }

    public static Path findTheMouse(Mouse mouse) {
        //walk through the folders in the resources to find the file that contains the mouse
        String secretName = String.valueOf(mouse.getSecretHidingName());
        String startDir = "src/main/resources/house/";

        try (Stream<Path> pathStream = Files.walk(Path.of(startDir))) {
            List<Path> mousePlaces = pathStream.filter(path -> Files.isRegularFile(path))
                    .filter(path -> containsValue(path, secretName))
                    .collect(Collectors.toList());

            if (mousePlaces.size() != 1) {
                System.out.println("wtf");
            } else {
                return mousePlaces.get(0);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static boolean containsValue(Path path, String value) {
        try {
            return Files.lines(path).anyMatch(line -> line.contains(value));
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
    }

    public static void animalFriendlyMouseRemoval(Mouse mouse) {
        //write the code here to modify the file that contains the mouse to contain no mouse
        try {
            String charset = "UTF-8";
            String secretName = String.valueOf(mouse.getSecretHidingName());

            removeCurrentLocation(mouse, charset, secretName);
            setNewLocation(mouse, charset, secretName);

        } catch (IOException e) {
            System.out.println("aaahhhhh");
            e.printStackTrace();
        }
    }

    private static void removeCurrentLocation(Mouse mouse, String charset, String secretName) throws IOException {
        File currentLocation = new File(mouse.getLocation().toString());

        File tempFile = File.createTempFile("blub", ".txt", currentLocation.getParentFile());
        PrintWriter tempWriter = new PrintWriter(new OutputStreamWriter(new FileOutputStream(tempFile), charset));

        Files.lines(mouse.getLocation())
                .filter(str -> !str.contains(secretName))
                .forEach(tempWriter::println);
        tempWriter.close();
        currentLocation.delete();
        tempFile.renameTo(currentLocation);
    }

    private static void setNewLocation(Mouse mouse, String charset, String secretName) throws UnsupportedEncodingException, FileNotFoundException {
        //we don't kill the poor thing
        //instead add the mouse to the file that contains its natural habitat (file: farAwayFromMyHouseMouseWonderland.txt)
        //so I mean really adding the long to the above mentioned file
        //write the code to add it to the file here
        String newLocationStr = "src/main/resources/park/farAwayFromMyHouseMouseWonderland.txt";
        File newLocation = new File(newLocationStr);
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(newLocation), charset));
        writer.println(secretName);
        writer.close();

        //change the location of the mouse
        mouse.setLocation(Path.of(newLocationStr));
    }

    public static void main(String[] args) {
        //micky is born
        Mouse micky = new Mouse();
        micky.setName("Micky");

        //let's give him a name to hide, so he's safe from cats
        micky.setSecretHidingName(changeNameToSecretName(micky.getName()));
        //let's find his location and set it (which is a path)
        micky.setLocation(findTheMouse(micky));
        //let's give him a better home
        animalFriendlyMouseRemoval(micky);

        System.out.println(micky);
    }
}
