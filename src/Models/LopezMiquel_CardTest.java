package Models;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LopezMiquel_CardTest {

    @Test
    void upgradecard() {
        LopezMiquel_Card x = new LopezMiquel_Card("Random", "Random", 1,0, 0, "Random", "Random");
        assertEquals(50,LopezMiquel_Card.upgradecard(0,50));
    }
}