package exceptions;

import java.beans.ExceptionListener;

public class ExtensionNotSupported extends Exception {
    public ExtensionNotSupported(String name){
        super(name);
    }
}
