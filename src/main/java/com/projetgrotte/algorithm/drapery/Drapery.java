package com.projetgrotte.algorithm.drapery;

import com.projetgrotte.algorithm.stalactite.Stalactite;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Drapery {

    private List<Stalactite> stalactites;

    public static String draperiesToString(List<Drapery> draperies) {
        StringBuilder draperiesStringified = new StringBuilder();
        final int[] index = {1};
        draperiesStringified.append("\n\n---------- DRAPERIES ----------");
        draperies.forEach(drapery -> {
                    draperiesStringified.append("\nDraperie N°").append(index[0])
                            .append("\n\tContient les stalactites :");
                    for (Stalactite stalactite : drapery.stalactites) {
                        draperiesStringified.append(" ")
                                .append(stalactite.getIndex())
                                .append(" ;");
                    }
                    index[0]++;
                }
        );
        return draperiesStringified.toString();
    }

    /*public void addStalactite(Stalactite stalactite) {
        stalactites.add(stalactite);
    }

    public void removeStalactite(Stalactite stalactite) {
        stalactites.remove(stalactite);
    }

    */


}
