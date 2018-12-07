package net.dnddev.factions.tests;

import static org.junit.Assert.assertEquals;

import java.util.PriorityQueue;

import org.junit.Before;
import org.junit.Test;

import net.dnddev.factions.utils.PriorityPair;

public class PriorityQueueTest
{
    
    private PriorityQueue<PriorityPair<String>> pq = new PriorityQueue<>();
    
    @Before
    public void setup()
    {
        pq.add(new PriorityPair<String>("h", 8));
        pq.add(new PriorityPair<String>("b", 2));
        pq.add(new PriorityPair<String>("f", 6));
        pq.add(new PriorityPair<String>("e", 5));
        pq.add(new PriorityPair<String>("a", 1));
        pq.add(new PriorityPair<String>("k", 11));
        pq.add(new PriorityPair<String>("g", 7));
        pq.add(new PriorityPair<String>("c", 3));
        pq.add(new PriorityPair<String>("d", 4));
        pq.add(new PriorityPair<String>("j", 10));
        pq.add(new PriorityPair<String>("i", 9));
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
