import org.junit.jupiter.api.Test;
import software_project.Main;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestAdd {


    Main obj;

    @Test
    public void TestADDPos()
    {
        obj = new Main();
        int exp = 9;
        int actual = obj.add(4,5);

        assertTrue(exp == actual);

    }

}
