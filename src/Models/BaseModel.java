package Models;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import Auxiliaries.CustomObjOutputStr;
public abstract class BaseModel {
    public abstract boolean saveInFile();
    public boolean save(File dataFile) {
        if (!isValid())
            return false;
        try {
            ObjectOutputStream outputStream;
            FileOutputStream fileOutputStream = new FileOutputStream(dataFile, true);
            if (dataFile.length() == 0)
                outputStream = new ObjectOutputStream(fileOutputStream);
            else
                outputStream = new CustomObjOutputStr(fileOutputStream);
            outputStream.writeObject(this);
            outputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        
        return true;
    }
    public abstract boolean isValid();
    public abstract boolean deleteFromFile();
}