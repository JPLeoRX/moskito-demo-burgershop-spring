package org.moskito.demo.burgershop.burgershopstripped.statistics;

import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;

public class SalesStats extends GenericStats {
    private StatValue number;
    private StatValue volume;

    public SalesStats(String aName) {
        super(aName);

        number = StatValueFactory.createStatValue(Long.valueOf(0), "number", Constants.getDefaultIntervals());
        volume = StatValueFactory.createStatValue(Long.valueOf(0), "volume", Constants.getDefaultIntervals());
    }

    public void addSale(int priceCents) {
        number.increase();
        volume.increaseByInt(priceCents);
    }

    public StatValue getNumber() {
        return number;
    }

    public StatValue getVolume() {
        return volume;
    }
}
