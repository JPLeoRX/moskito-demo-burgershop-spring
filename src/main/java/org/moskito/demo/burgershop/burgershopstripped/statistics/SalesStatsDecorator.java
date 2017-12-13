package org.moskito.demo.burgershop.burgershopstripped.statistics;

import net.anotheria.moskito.core.decorators.AbstractDecorator;
import net.anotheria.moskito.core.decorators.DecoratorRegistryFactory;
import net.anotheria.moskito.core.decorators.value.DoubleValueAO;
import net.anotheria.moskito.core.decorators.value.LongValueAO;
import net.anotheria.moskito.core.decorators.value.StatValueAO;
import net.anotheria.moskito.core.producers.IStats;
import net.anotheria.moskito.core.stats.TimeUnit;

import java.util.ArrayList;
import java.util.List;

public class SalesStatsDecorator extends AbstractDecorator {
    static final String CAPTIONS[] = {"Sales", "Volume", "Avg"};
    static final String SHORT_EXPLANATIONS[] = {"Number of sales", "Total earnings", "Avg earnings"};
    static final String EXPLANATIONS[] = {"Total number of sales for this ingredient", "Total earnings from sales of this ingredient", "Average earnings per sale for this ingredient",};

    protected SalesStatsDecorator() {
        super("Sales", CAPTIONS, SHORT_EXPLANATIONS, EXPLANATIONS);
    }

    @Override
    public List<StatValueAO> getValues(IStats iStats, String s1, TimeUnit timeUnit) {
        SalesStats salesStats = (SalesStats) iStats;
        List<StatValueAO> ret = new ArrayList<>(CAPTIONS.length);
        int i = 0;
        long totalSales = salesStats.getNumber(s1);
        long totalVolume = salesStats.getVolume(s1);
        double average = salesStats.getAverageVolume(s1);

        ret.add(new LongValueAO(CAPTIONS[i++], totalSales));
        ret.add(new LongValueAO(CAPTIONS[i++], totalVolume));
        ret.add(new DoubleValueAO(CAPTIONS[i++], average));

        return ret;
    }
}
