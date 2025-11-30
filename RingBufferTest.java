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
    
    @Test //Prüft, ob ein Element im leeren Buffer gespeichert wird und die Größe korrekt steigt
    public void testPush()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        assertEquals(1, ringBuff1.size());
    }
    @Test //Prüft, ob das älteste Element bei vollem Puffer entfernt wird und size() entsprechend sinkt
    public void testPop1()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(20);
        ringBuff1.push(21);
        ringBuff1.push(22);
        assertEquals(20, ringBuff1.pop());
        assertEquals(2, ringBuff1.size());
    }
    @Test //Prüft, ob pop() bei leerem Buffer 0 zurückgibt und size() bei 0 bleibt
    public void testPopEmpty()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);;
        assertEquals(0, ringBuff1.pop());
        assertEquals(0, ringBuff1.size()); 
        }
    @Test // Prüft, ob peek() das älteste Element zurückgibt, ohne es zu löschen, und size() unverändert bleibt
    public void testPeek1()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        ringBuff1.push(21);
        assertEquals(19, ringBuff1.peek());
        assertEquals(3, ringBuff1.size());
    }
    @Test //Prüft Szenario: pop() entfernt zuerst den ältesten Wert und peek() zeigt den neuen ältesten Wert
    public void testPeek2()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        ringBuff1.push(21);
        assertEquals(19, ringBuff1.pop());
        assertEquals(20, ringBuff1.peek());
        assertEquals(2, ringBuff1.size());
    }
    @Test //Prüft, ob peek() bei leerem Buffer den Standardwert 0 zurückgibt und die Größe gleich bleibt
    public void testPeekEmpty()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        assertEquals(0, ringBuff1.peek());
        assertEquals(0, ringBuff1.size());
    }
    @Test //Prüft, ob size() die korrekte Anzahl gespeicherter Elemente zurückgibt
    public void testsize()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        assertEquals(2, ringBuff1.size());
    }
    @Test //Prüft, ob capacity() die festgelegte maximale Größe des Buffers zurückgibt
    public void testCapacity()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        assertEquals(3, ringBuff1.capacity());
    }
    @Test // Prüft Verhalten bei erneutem Einfügen nach Entfernen aus vollem Buffer
    public void testFullCapacity()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(1);
        ringBuff1.push(2);
        ringBuff1.push(3);
        assertEquals(1, ringBuff1.pop());
        ringBuff1.push(4);
        assertEquals(3, ringBuff1.size());
    }
    @Test // Testet den Verhalten vom Buffer mit push(), pop(), peek()
    public void testBufferBehaviour()
    {
        RingBuffer ringBuff1 = new RingBuffer(3);
        ringBuff1.push(19);
        ringBuff1.push(20);
        ringBuff1.push(21);
        assertEquals(19, ringBuff1.pop());
        ringBuff1.push(22);
        assertEquals(20, ringBuff1.peek());
        assertEquals(3, ringBuff1.size());
    }
}
