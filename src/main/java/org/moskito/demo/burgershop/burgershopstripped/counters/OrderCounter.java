package org.moskito.demo.burgershop.burgershopstripped.counters;

import net.anotheria.moskito.aop.annotation.Count;

@Count(category = "business", producerId = "orders")
public class OrderCounter {
    public void orderPlaced() {

    }
}