// Importiert assertEquals usw. sowie Test-Annotationen
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Diese Klasse definiert die Tests für die Klasse <Klasse ergänzen>.
 *
 * @author Lilian Okoye
 */
public class RingBufferTest
{      
    @BeforeEach
    public void setUp()
    {
        // Hier Anweisungen einfügen, die vor jedem Test ausgeführt werden
    }

    @Test
    public void testPop()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(20);
        assertEquals(20, ringBuff1.pop());
        assertEquals(0, ringBuff1.size());
    }

    @Test
    public void testPeek()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        ringBuff1.push(21);
        assertEquals(19, ringBuff1.peek());
    }
    
    @Test
    public void testsize()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        ringBuff1.push(21);
        assertEquals(3, ringBuff1.size());
    }
    
    @Test
    public void RingBufferTest()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        ringBuff1.push(21);
        assertEquals(19, ringBuff1.pop());
        ringBuff1.push(22);
        assertEquals(20, ringBuff1.peek());
    }
}





