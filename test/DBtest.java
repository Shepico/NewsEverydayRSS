import org.junit.Assert;
import org.junit.Test;
import ru.shepico.utils.DBaccess;

import java.io.File;
import java.nio.file.Files;

public class DBtest extends Assert {

    //DBaccess db = new DBaccess();

    @Test
    public void testFileDBexistence(){
        File file = new File(new DBaccess().getPATH_FILE());
        assertTrue(file.exists());
    }
}
