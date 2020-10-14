package com.teksystem.codechallenge;

import com.teksystem.codechallenge.algorithm.Algorithm;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class AlgorithmTest {

    @Test
    void testCodeChallenge() {

        Algorithm algorithm = new Algorithm();

        // Test zip code validation
        assertThrows(AssertionError.class, () -> { algorithm.setMinRange(null);});
        assertThrows(AssertionError.class, () -> { algorithm.setMinRange(new String[][]{{"00000","10000","64638"},{"29345","35384"}});});
        assertThrows(AssertionError.class, () -> { algorithm.setMinRange(new String[][]{{"000000","10000"},{"29345","35384"}});});
        assertThrows(AssertionError.class, () -> { algorithm.setMinRange(new String[][]{{"00a00","10000"},{"29345","35384"}});});
        assertThrows(AssertionError.class, () -> { algorithm.setMinRange(new String[][]{{"00000","10000"},{"99345","35384"}});});

        // Test zip code range edge problem
        algorithm.setMinRange(new String[][]{});
        assertEquals("[]", algorithm.toString());
        algorithm.setMinRange(new String[][]{{"00100","10000"}});
        assertEquals("[[00100, 10000]]", algorithm.toString());
        algorithm.setMinRange(new String[][]{{"00100","10000"},{"29345","35384"}});
        assertEquals("[[00100, 10000], [29345, 35384]]", algorithm.toString());
        algorithm.setMinRange(new String[][]{{"00100","29344"},{"29345","35384"}});
        assertEquals("[[00100, 35384]]", algorithm.toString());
        algorithm.setMinRange(new String[][]{{"00100","30000"},{"29345","35384"}});
        assertEquals("[[00100, 35384]]", algorithm.toString());
        algorithm.setMinRange(new String[][]{{"00100","50000"},{"29345","35384"}});
        assertEquals("[[00100, 50000]]", algorithm.toString());
        algorithm.setMinRange(new String[][]{{"00100","29344"},{"35384","35384"}});
        assertEquals("[[00100, 29344], [35384, 35384]]", algorithm.toString());

        // Test zip code range total performance
        algorithm.setMinRange(new String[][]{
                {"00100","29344"},
                {"35384","47573"},
                {"73875","82463"},
                {"80632","82762"},
                {"17274","22048"},
                {"03836","16264"},
                {"82784","93726"},
                {"57383","57383"},
                {"34527","47823"},
                {"73644","83725"}});
        assertEquals("[[00100, 29344], [34527, 47823], [57383, 57383], [73644, 93726]]", algorithm.toString());
    }

}
