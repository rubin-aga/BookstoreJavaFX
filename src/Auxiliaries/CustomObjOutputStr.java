package Auxiliaries;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
public class CustomObjOutputStr extends ObjectOutputStream {
	public CustomObjOutputStr(OutputStream out) throws IOException {
        super(out);
    }
    public void writeStreamHeader()
    {
        // don't do anything
    }
}
