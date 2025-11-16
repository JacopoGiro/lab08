package it.unibo.deathnote;

import java.util.HashMap;
import java.util.Map;

import it.unibo.deathnote.api.DeathNote;

/**
 * Implementazione di DeathNote usata per l'esercizio TDD.
 * Questa classe registra nomi, cause e dettagli come richiesto.
 */
final class DeathNoteImpl implements DeathNote {

    private final Map<String, DeathData> dNote = new HashMap<>();
    private long lastWriteTime;
    private String lastNameWritten;

    @Override
    public String getRule(final int ruleNumber) {
        if (ruleNumber <= 0 || ruleNumber > RULES.size()) {
            throw new IllegalArgumentException("rules index cannot be 0, negative or more than the number of rules");
        }
        return RULES.get(ruleNumber - 1);
    }

    @Override
    public void writeName(final String name) {
        if (name == null) {
            throw new NullPointerException("name parameter shouldn't be null"); // NOPMD
        }
        if (name.isEmpty()) {
            return;
        }
        lastWriteTime = System.currentTimeMillis();
        dNote.put(name, new DeathData());
        lastNameWritten = name;
    }

    @Override
    public boolean writeDeathCause(final String cause) {
        if (cause == null || dNote.isEmpty()) {
            throw new IllegalStateException("either cause is null or dNote is empty");
        }
        final long delay = 40;
        //if the cause is written within 40ms 
        if ((System.currentTimeMillis() - lastWriteTime) <= delay) {
            dNote.get(lastNameWritten).setDeathCause(cause);
            return true;
        }
        return false;
    }

    @Override
    public boolean writeDetails(final String details) {
        if (details == null || dNote.isEmpty()) {
            throw new IllegalStateException("cause is null or dNote is empty");
        }
        final long delay = 6400;
        //if the cause is written within 6400ms 
        if ((System.currentTimeMillis() - lastWriteTime) <= delay) {
            dNote.get(lastNameWritten).setDeathDetails(details);
            return true;
        }
        return false;
    }

    @Override
    public String getDeathCause(final String name) {
        if (!dNote.containsKey(name)) {
            throw new IllegalArgumentException("name is not in the death note");
        }
        return dNote.get(name).getDeathCause();
    }

    @Override
    public String getDeathDetails(final String name) {
        if (!dNote.containsKey(name)) {
            throw new IllegalArgumentException("name isn't on the death note");
        }
        return dNote.get(name).getDeathDetails();
    }

    @Override
    public boolean isNameWritten(final String name) {
        return dNote.containsKey(name);
    }

    private final class DeathData {
        private String deathCause;
        private String deathDetails;

        DeathData() {
            deathCause = "heart attack";
            deathDetails = "";
        }

        public String getDeathCause() {
            return deathCause;
        }

        public String getDeathDetails() {
            return deathDetails;
        }

        public void setDeathCause(final String cause) {
            deathCause = cause;
        }

        public void setDeathDetails(final String details) {
            deathDetails = details;
        }
    }
}
