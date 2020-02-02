import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;


public class TestAuditionTask {

    Mouse mouse = new Mouse();

    @BeforeEach
    public void init(){
        //set the mouse
        mouse.setName("Micky");
        mouse.setSecretHidingName(13931125);
        mouse.setLocation(Paths.get("src", "main", "resources", "house", "downstairs", "livingRoom.txt"));
        //make sure the mouse is in the default living room
        File file = new File(mouse.getLocation().toString());
        try {
            long miceInLivingRoom = Files.lines(file.toPath())
                    .filter(line -> line.contains(Long.toString(mouse.getSecretHidingName())))
                    .count();
            if(miceInLivingRoom < 1) {
                BufferedWriter out = new BufferedWriter(new FileWriter(file, true));
                out.write(Long.toString(mouse.getSecretHidingName()));
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //make sure it's not in the park
        File filePark = Paths.get("src", "main", "resources", "park", "farAwayFromMyHouseMouseWonderland.txt").toFile();
        try {
            List<String> out = Files.lines(Paths.get("src", "main", "resources", "park", "farAwayFromMyHouseMouseWonderland.txt"))
                    .filter(line -> !line.contains(Long.toString(mouse.getSecretHidingName())))
                    .collect(Collectors.toList());
            Files.write(filePark.toPath(), out, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void testChangeNameToSecretName(){

        long secretName = AuditionTaskCatchTheMouse.changeNameToSecretName(mouse.getName());
        assertEquals(13931125, secretName, "the numbers don't match, if it is 0, please implement the methode changeNameToSecretName");
        assertNotEquals(13+9+3+11+25, secretName, "you've added the numbers instead of concatting them");
        assertNotEquals(13, secretName, "it seems that your method can only handle capitals");
        assertNotEquals(931125, secretName, "it seems that your method can only handle lower case");
        assertNotEquals(12821024, secretName, "you've started with A is 0, you're a true Java developer, but please start with A is 1");
    }

    @Test
    public void testChangeNameToSecretNameWhiteSpace(){
        long secretName = AuditionTaskCatchTheMouse.changeNameToSecretName(" Micky ");
        assertEquals(13931125, secretName, "the numbers don't match, if it is 0, please implement the methode changeNameToSecretName, if it gives an error, please make sure that your mehod can deal with white space");

    }

    @Test
    public void testChangeNameToSecretNameForOnlyWorkingForMicky(){
        long secretName = AuditionTaskCatchTheMouse.changeNameToSecretName("ab");
        assertEquals(12, secretName, "Make sure that your method changeNameToSecretName also works for other names than Micky.");

    }

    @Test
    public void testFindTheMouse(){
        Path p = AuditionTaskCatchTheMouse.findTheMouse(mouse);
        assertEquals(Paths.get("src", "main", "resources", "house", "downstairs", "livingRoom.txt"), p, "you're test did not lead to the file containing the mouse");
        assertFalse(p.isAbsolute(), "You've accidentally returned an absolute path, and we want to work with relative paths here");
    }

    @Test
    public void  testAnimalFriendlyMouseRemovalOutOfLivingRoom() {
        AuditionTaskCatchTheMouse.animalFriendlyMouseRemoval(mouse);

        //check if mouse is still in the living room
        Path p = Paths.get("src", "main", "resources", "house", "downstairs", "livingRoom.txt");
        try {
            long miceInLivingRoom = Files.lines(p).filter(line -> line.contains(Long.toString(mouse.getSecretHidingName()))).count();
            assertEquals(0, miceInLivingRoom, "aaaaah the mouse is still in the living room file");
        } catch (IOException e) {
            e.printStackTrace();
        }
        assertNotEquals(Paths.get("src", "main", "resources", "house", "downstairs", "livingRoom.txt").toString(), mouse.getLocation().toString(), "The location of the mouse is still in the living room, did you forget to set the location of the mouse to the new location?");
    }

    @Test
    public void testAnimalFriendlyMouseRemovalMouseInPark() {
        AuditionTaskCatchTheMouse.animalFriendlyMouseRemoval(mouse);

        // check if mouse is in the required new file
        Path p2 = Paths.get("src", "main", "resources", "park", "farAwayFromMyHouseMouseWonderland.txt");
        try {
            long miceInPark = Files.lines(p2).filter(line -> line.contains(Long.toString(mouse.getSecretHidingName()))).count();
            assertEquals(1, miceInPark, "I don't know where the mouse went, but it certainly isn't in farAwayFromMyHouseMouseWonderland");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testAnimalFriendlyMouseRemovalMouseInMoreThanOneFileAfterBeingRemoved() {
        AuditionTaskCatchTheMouse.animalFriendlyMouseRemoval(mouse);

        //check if the mouse is no longer in the living room before checking whether it's in more than one file
        Path p1 = Paths.get("src", "main", "resources", "house", "downstairs", "livingRoom.txt");
        try {
            long miceInLivingRoom = Files.lines(p1).filter(line -> line.contains(Long.toString(mouse.getSecretHidingName()))).count();
            assertEquals(0, miceInLivingRoom, "I don't care in how many files it is, it is still in the living room!!!!");
        } catch (IOException e) {
            e.printStackTrace();
        }

        //check if mouse is in any other file
        List<String> filesInWhichMouseIs = new ArrayList<>();
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
                        filesInWhichMouseIs.add(file);
                    }
                }

            }
            assertEquals(1, filesInWhichMouseIs.size(), "the mouse is in more than 1 file");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
