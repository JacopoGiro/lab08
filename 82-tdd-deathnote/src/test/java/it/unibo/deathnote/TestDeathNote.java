package it.unibo.deathnote;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TestDeathNote {
    private final DeathNoteImpl dNote = new DeathNoteImpl();

    @Test
    void negativeRulesCheck() {
        try {
            dNote.getRule(0);
            dNote.getRule(-1);
            Assertions.fail("a negative number or 0 as index for .getRule() method should throw the appropriate exception");
        } catch (final IllegalArgumentException e) {
            Assertions.assertNotNull(e.getMessage(), "Exception message is null");
            Assertions.assertNotEquals("", e.getMessage(), "Exception message is empty");
        }
    }

    @Test
    void writeCheck() {
        Assertions.assertFalse(dNote.isNameWritten("pippo"), "written name: pippo is already in the DeathNote");
        dNote.writeName("puppi");
        Assertions.assertTrue(dNote.isNameWritten("puppi"), 
            "the name do not result as wirtten on the DeathNote even after calling writeName(name)"
        );
        Assertions.assertFalse(dNote.isNameWritten("L"), 
            "a name result as written on the DeathNote even if it wasn't called the writeName() method on it"
        );
        Assertions.assertFalse(dNote.isNameWritten(""), 
            "\"\" result as written in the DeathNote but it shouldn't be"
        );
    }

    @Test
    void deathCauseCheck() throws InterruptedException {
        try {
            dNote.writeDeathCause("try cause");
            Assertions.fail("Writing the cause of death before writing an actual name should throw the appropriate exception");
        } catch (final IllegalStateException e) {
            Assertions.assertNotNull(e.getMessage(), "IllegalStateException message is null");
            Assertions.assertNotEquals("", e.getMessage(), "IllegalStateException message is empty");
        }
        dNote.writeName("Masuka");
        Assertions.assertEquals("heart attack", 
            dNote.getDeathCause("Masuka"), 
            "death causa for whom is not specified shoud be \" heart attack \""
        );
        dNote.writeName("James Dean");
        Assertions.assertTrue(dNote.writeDeathCause("karting accident"), 
            "writing death cause didn't return true but it should have"
        );
        Assertions.assertEquals("karting accident", 
            dNote.getDeathCause("James Dean"), 
            "expecting \"karting accident\""
        );
        Thread.sleep(100);
        Assertions.assertFalse(dNote.writeDeathCause("assassinated"), 
            "writing death cause after the time limit should have returned false but it didn't"
        );
    }

    @Test
    void deathDetailsCheck() throws InterruptedException {
        try {
            dNote.writeDetails("try details");
            Assertions.fail("Writing the details before writing an actual name should throw the appropriate exception");
        } catch (final IllegalStateException e) {
            Assertions.assertNotNull(e.getMessage(), "IllegalStateException message is null");
            Assertions.assertNotEquals("", e.getMessage(), "IllegalStateException message is empty");
        }
        dNote.writeName("Totò Riina");
        Assertions.assertEquals(dNote.getDeathDetails("Totò Riina"), 
            "", 
            "death details if not specified should be empty \"\" but it isn't"
        );
        Assertions.assertTrue(dNote.writeDetails("ran for too long"), 
            "writing details should have returned true but it didn't"
        );
        Assertions.assertEquals("ran for too long", 
            dNote.getDeathDetails("Totò Riina"), 
            "expecting \"ran for too long\""
        );
        dNote.writeName("Joseph Stalin");
        Thread.sleep(6400);
        Assertions.assertFalse(dNote.writeDetails("assassinated"), 
            "writing death details after the time limit should have returned false but it didn't"
        );
        Assertions.assertEquals("", 
            dNote.getDeathDetails("Joseph Stalin"), 
            "the method writeDetails returned False correctly but death details are not empty as expected"
        );
    }
}
