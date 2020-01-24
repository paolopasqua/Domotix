package domotix.model.util;

import domotix.model.bean.device.Dispositivo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ElencoDispositivi implements ListaOsservabile<Dispositivo> {
    private Map<String, Dispositivo> elenco;
    private ArrayList<OsservatoreLista<Dispositivo>> osservatori;

    public ElencoDispositivi(Map<String, Dispositivo> elencoIniziale) {
        elenco = elencoIniziale;
        osservatori = new ArrayList<>();
    }

    public ElencoDispositivi() {
        this(new HashMap<>());
    }

    public Dispositivo getDispositivo(String key) {
        return elenco.get(key);
    }

    public Dispositivo[] getDispositivi() {
        return elenco.values().toArray(new Dispositivo[0]);
    }

    public boolean contains(String key) {
        return elenco.get(key) != null;
    }

    public void remove(String key) {
        Dispositivo dispositivo = elenco.get(key);

        if (dispositivo != null) {
            //rimuovo solo se presente
            elenco.remove(key);

            //decremento il numero associazioni e informo gli osservatori
            dispositivo.decrNumAssociazioni();
            informaRimozione(dispositivo);
        }
    }

    public void remove(Dispositivo dispositivo) {
        this.remove(dispositivo.getNome());
    }

    public boolean add(Dispositivo dispositivo, String key) {
        if (contains(key))
            return false;

        //se non presente aggiungo
        elenco.put(key, dispositivo);

        //incremento il numero di associazioni e informo gli osservatori
        dispositivo.incrNumAssociazioni();
        informaAggiunta(dispositivo);
        return true;
    }

    public boolean add(Dispositivo dispositivo) {
        return add(dispositivo, dispositivo.getNome());
    }

    @Override
    public void aggiungiOsservatore(OsservatoreLista<Dispositivo> oss) {
        elenco.forEach((s, dispositivo) -> oss.elaboraAggiunta(dispositivo)); //in modo da informare immediatamente l'osservatore dei dati gia' contenuti
        osservatori.add(oss);
    }

    @Override
    public void rimuoviOsservatore(OsservatoreLista<Dispositivo> oss) {
        osservatori.remove(oss);
    }

    @Override
    public List<OsservatoreLista<Dispositivo>> getOsservatori() {
        return osservatori;
    }

    @Override
    public void svuotaOsservatori() {
        osservatori.clear();
    }

    @Override
    public void informaRimozione(Dispositivo dato) {
        osservatori.forEach(osservatore -> osservatore.elaboraRimozione(dato));
    }

    @Override
    public void informaAggiunta(Dispositivo dato) {
        osservatori.forEach(osservatore -> osservatore.elaboraAggiunta(dato));
    }
}