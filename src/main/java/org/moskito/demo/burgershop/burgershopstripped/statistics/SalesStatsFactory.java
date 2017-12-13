package org.moskito.demo.burgershop.burgershopstripped.statistics;

import net.anotheria.moskito.core.dynamic.IOnDemandStatsFactory;

public class SalesStatsFactory implements IOnDemandStatsFactory<SalesStats> {
    @Override
    public SalesStats createStatsObject(String s) {
        return new SalesStats(s);
    }
}