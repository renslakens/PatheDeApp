package com.groep3.pathedeapp.domain;

import static org.junit.Assert.*;

import org.junit.Test;

class MovieTest {

    @Test
    public void MovieVoteAverage_dividedBy2_1(){
        Movie movie = new Movie();
        movie.setVoteAverage(6.4);

        assertEquals(movie.getVoteAverage() / 2, 3.2, 0.01);
    }
    @Test
    public void MovieVoteAverage_dividedBy2_2(){
        Movie movie = new Movie();
        movie.setVoteAverage(9.0);

        assertEquals(movie.getVoteAverage() / 2, 4.5, 0.01);
    }
}