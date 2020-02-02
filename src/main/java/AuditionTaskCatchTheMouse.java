import java.nio.file.Path;
import java.nio.file.Paths;

public class AuditionTaskCatchTheMouse {
    //Oh no! There's a mouse walking through my living room (there actually is one and I can see him while typing this ugh....)
    //Please help me with the little piece of Java to catch him
    //It will also help you learning about Strings, loops, converting to a number
    //and reading and writing files
    //and walking paths!
    //I know it's a bit random, but please help me with this mouse

    public static long changeNameToSecretName(String name) {

        //write here the code to get the secret name, replace the letters of the mouse name letter by letter.
        //the letter should be replaced with the corresponding number of that letter in the alphabet

        //replace this with the secret name
        return 0;
    }

    public static Path findTheMouse(Mouse mouse){
        //walk through the folders in the resources to find the file that contains the mouse
        return Paths.get("the path to the file that contains the mouse");
    }

    public static Path animalFriendlyMouseRemoval(Mouse mouse){
        //write the code here to modify the file that contains the mouse to contain no mouse

        //we don't kill the poor thing
        //instead add the mouse to the file that contains its natural habitat (file: farAwayFromMyHouseMouseWonderland.txt)
        //so I mean really adding the long to the above mentioned file
        //write the code to add it to the file here


        //change the location of the mouse
        mouse.setLocation(Paths.get("path/to/new/location/mouse"));
        return Paths.get("path/to/new/location/mouse");
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
