package com.projetgrotte.algorithm.drapery;

import com.projetgrotte.algorithm.stalactite.Stalactite;

import java.util.List;

public class Drapery {

    private List<Stalactite> stalactites;

    public Drapery(List<Stalactite> stalactites) {
        this.stalactites = stalactites;
    }

    public void addStalactite(Stalactite stalactite) {
        stalactites.add(stalactite);
    }

    public void removeStalactite(Stalactite stalactite) {
        stalactites.remove(stalactite);
    }

    public List<Stalactite> getStalactites() {
        return stalactites;
    }
}
