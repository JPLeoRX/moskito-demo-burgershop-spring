package org.moskito.demo.burgershop.burgershopstripped.counters;

import net.anotheria.moskito.aop.annotation.CountByParameter;

public class IngredientCounter {
    @CountByParameter public void ingredientUsed(String ingredient) {

    }
}