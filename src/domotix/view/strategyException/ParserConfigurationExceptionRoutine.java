package domotix.view.strategyException;
import static domotix.view.ViewUtil.ASSISTENZA;

/**
 * Classe che realizza la routine di gestione ParserConfigurationException
 * @author Edoardo Coppola
 * @see ExceptionStrategy
 */
public class ParserConfigurationExceptionRoutine implements ExceptionStrategy{

    private static final String ERRORE_CARICAMENTO_STRUMENTI_LETTURA_SCRITTURA = "Si è verificato un errore durante il caricamento degli strumenti per la lettura e la scrittura dei dati salvati"
            + ASSISTENZA;

    @Override
    public String doExceptionRuotine(Exception e) {
        return ERRORE_CARICAMENTO_STRUMENTI_LETTURA_SCRITTURA;
    }
}
