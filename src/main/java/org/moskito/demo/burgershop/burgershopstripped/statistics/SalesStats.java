package org.moskito.demo.burgershop.burgershopstripped.statistics;

import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.predefined.Constants;
import net.anotheria.moskito.core.producers.GenericStats;
import net.anotheria.moskito.core.stats.StatValue;
import net.anotheria.moskito.core.stats.impl.StatValueFactory;


public class SalesStats extends GenericStats {
    static{
        DecoratorRegistryFactory.getDecoratorRegistry().addDecorator(SalesStats.class, new SalesStatsDecorator());
    }

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

    public Long getNumber(String intervalName) {
        return number.getValueAsLong(intervalName);
    }

    public Long getVolume(String intervalName) {
        return volume.getValueAsLong(intervalName);
    }

    public Double getAverageVolume(String interval) {
        return 1.0* getVolume(interval) / getNumber(interval);
    }
}
