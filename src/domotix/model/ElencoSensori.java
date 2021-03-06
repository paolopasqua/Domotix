package domotix.model;

import domotix.model.bean.device.Dispositivo;
import domotix.model.bean.device.Sensore;
import domotix.model.bean.device.SensoreOrologio;
import domotix.model.util.SommarioDispositivi;

import java.util.Arrays;

/**
 * Classe di accesso al Model per i Sensori salvati. Implementa l'interfaccia SommarioDispositivi in quanto l'elenco
 * e' popolato in automatico con le modifiche dei sensori collegati a stanze/artefatti.
 *
 * @see Sensore
 * @see SommarioDispositivi
 * @see domotix.model.bean.system.Stanza
 * @see domotix.model.bean.system.Artefatto
 */
public class ElencoSensori extends SommarioDispositivi {

    public ElencoSensori(SensoreOrologio orologio) {
        super();
        elaboraAggiunta(orologio);
    }

    @Override
    public Sensore getDispositivo(String key) {
        return (Sensore) super.getDispositivo(key);
    }

    @Override
    public Sensore[] getDispositivi() {
        return Arrays.copyOf(super.getDispositivi(), super.getDispositivi().length, Sensore[].class);
    }

    @Override
    public void elaboraAggiunta(Dispositivo dato) {
        if (dato instanceof Sensore)
            super.elaboraAggiunta(dato);
    }
}
