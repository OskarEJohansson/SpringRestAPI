package dev.oskarjohansson.projektarbetev2.exception.customExceptions;

public class RetrievePublicRSAKeyException extends RuntimeException
{
    public RetrievePublicRSAKeyException(String message, Throwable cause){
        super(message, cause);
    }

}
