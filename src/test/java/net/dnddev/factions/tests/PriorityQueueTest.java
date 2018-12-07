package net.dnddev.factions.tests;

import net.dnddev.factions.utils.PriorityValue;
import org.junit.Before;
import org.junit.Test;

import java.util.PriorityQueue;

import static org.junit.Assert.assertEquals;

public class PriorityQueueTest
{

    private PriorityQueue<PriorityValue<String>> pq = new PriorityQueue<>();

    @Before
    public void setup()
    {
        pq.add(new PriorityValue<>("h", 8));
        pq.add(new PriorityValue<>("b", 2));
        pq.add(new PriorityValue<>("f", 6));
        pq.add(new PriorityValue<>("e", 5));
        pq.add(new PriorityValue<>("a", 1));
        pq.add(new PriorityValue<>("k", 11));
        pq.add(new PriorityValue<>("g", 7));
        pq.add(new PriorityValue<>("c", 3));
        pq.add(new PriorityValue<>("d", 4));
        pq.add(new PriorityValue<>("j", 10));
        pq.add(new PriorityValue<>("i", 9));
    }

    @Test
    public void checkOrder()
    {
        assertEquals(pq.poll(), "a");
        assertEquals(pq.poll(), "b");
        assertEquals(pq.poll(), "c");
        assertEquals(pq.poll(), "d");
        assertEquals(pq.poll(), "e");
        assertEquals(pq.poll(), "f");
        assertEquals(pq.poll(), "g");
        assertEquals(pq.poll(), "h");
        assertEquals(pq.poll(), "i");
        assertEquals(pq.poll(), "j");
        assertEquals(pq.poll(), "k");
    }

}
