import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AuditionTaskCatchTheMouseSolution {

    public static long changeNameToSecretName(String name) {

        String str = name.toUpperCase();
        String secret = "";
        char[] ch  = str.toCharArray(); // so we can loop through it letter by letter
        for(char c : ch){
            int temp = (int)c;
            int temp_integer = 64; //for upper case, this is where the asci code starts
            if(temp<=90 & temp>=65) //ignore any other character like a white space or -
                secret = secret + (temp-temp_integer); //we cant go to long directly, cause we don't want the sum
        }
        return Long.parseLong(secret);
    }

    public static Path findTheMouse(Mouse mouse){
        //walk through the folders in the resources to find the file that contains the mouse
        try (Stream<Path> walk = Files.walk(Paths.get("src/main/resources"))) {

            //get a list of all the files in which our mouse might hide
            List<String> listOfFiles = walk.filter(Files::isRegularFile)
                    .map(x -> x.toString()).collect(Collectors.toList());

            //check the content of each file for our mouse
            for(String file : listOfFiles)
            {
                List<String> lines = Files.readAllLines(Paths.get(file));
                //see if any of the lines contains the mouse
                for(String line : lines){
                    if(line.contains(Long.toString(mouse.getSecretHidingName()))){
                        return Paths.get(file);
                    }
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }

        return Paths.get("mouse/not/found");
    }

    public static Path animalFriendlyMouseRemoval(Mouse mouse){
        //write the code here to modify the file that contains the mouse to contain no mouse
        File file = new File(mouse.getLocation().toString());
        try {
            List<String> out = Files.lines(file.toPath())
                .filter(line -> !line.contains(Long.toString(mouse.getSecretHidingName())))
                .collect(Collectors.toList());
            Files.write(file.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

        //we don't kill the poor thing
        //instead add the mouse to the file that contains its natural habitat (file: farAwayFromMyHouseMouseWonderland.txt)
        //so I mean really adding the long to the above mentioned file
        File file2 =  new File("src/main/resources/park/farAwayFromMyHouseMouseWonderland.txt");
        try {
            BufferedWriter out = new BufferedWriter(
                    new FileWriter(file2, true));
            out.write(Long.toString(mouse.getSecretHidingName()));
            out.close();
        }
        catch (IOException e) {
            System.out.println("exception occoured" + e);
        }

        //change the location of the mouse
        mouse.setLocation(Paths.get("src", "main", "resources", "park", "farAwayFromMyHouseMouseWonderland.txt"));
        return mouse.getLocation();
    }

    public static void main(String[] args) {
        //micky is being born
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
