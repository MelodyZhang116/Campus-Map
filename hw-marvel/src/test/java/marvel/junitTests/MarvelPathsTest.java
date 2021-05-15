package marvel.junitTests;

import marvel.MarvelPaths;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.Timeout;

import java.io.IOException;

public class MarvelPathsTest {
    @Rule
    public Timeout glocalTimeout = Timeout.seconds(10);//10 seconds max per method tested

    @Test(expected=IOException.class)
    public void fileNotExist() throws IOException {
        new MarvelPaths("abc");
        new MarvelPaths("file not exist");
        new MarvelPaths("fileNotExist.csv");
    }
    @Test(expected=IllegalArgumentException.class)
    public void PathFindingWithInvalidParentOrChild() throws IOException {
        MarvelPaths graph = new MarvelPaths("staffSuperheroes.csv");
        graph.findPaths("InvalidParent","InvalidChild");
        graph.findPaths("Invalid parent","Ernst-the-Bicycling-Wizard");
        graph.findPaths("Ernst-the-Bicycling-Wizard","invalidChild");
    }
}
