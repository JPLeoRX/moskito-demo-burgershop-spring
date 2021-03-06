package org.moskito.demo.burgershop.burgershopstripped.service;

import net.anotheria.moskito.aop.annotation.Monitor;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducer;
import net.anotheria.moskito.core.dynamic.OnDemandStatsProducerException;
import net.anotheria.moskito.core.registry.ProducerRegistryFactory;
import org.moskito.demo.burgershop.burgershopstripped.counters.IngredientCounter;
import org.moskito.demo.burgershop.burgershopstripped.counters.OrderCounter;
import org.moskito.demo.burgershop.burgershopstripped.statistics.SalesStats;
import org.moskito.demo.burgershop.burgershopstripped.statistics.SalesStatsFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedList;
import java.util.List;

/**
 * Implementation of the ShopService.
 *
 * @author lrosenberg
 * @since 16.11.13 22:47
 */
@Monitor
public class ShopServiceImpl implements ShopService {

	private LinkedList<ShopableItem> items;

	private static Logger log = LoggerFactory.getLogger(ShopServiceImpl.class);

	private OrderCounter orderCounter = new OrderCounter();
	private IngredientCounter ingredientCounter = new IngredientCounter();
	private OnDemandStatsProducer<SalesStats> salesProducer;

	@Autowired
	private NotificationService notificationService;

	public ShopServiceImpl(){
		items = new LinkedList<ShopableItem>();
		items.add(new ShopableItem("wheat", 585, Category.BREAD));
		items.add(new ShopableItem("wholemeal", 285, Category.BREAD));
		items.add(new ShopableItem("brioche", 585, Category.BREAD));
		items.add(new ShopableItem("burned", 585, Category.BREAD));
		items.add(new ShopableItem("leibniz", 1085, Category.BREAD));

		items.add(new ShopableItem("cow", 1385, Category.MEAT));
		items.add(new ShopableItem("pork", 1185, Category.MEAT));
		items.add(new ShopableItem("lamb", 1584, Category.MEAT));
		items.add(new ShopableItem("dog", 585, Category.MEAT));
		items.add(new ShopableItem("rat", 10, Category.MEAT));

		items.add(new ShopableItem("mushrooms", 285, Category.EXTRAS));
		items.add(new ShopableItem("broccoli", 185, Category.EXTRAS));
		items.add(new ShopableItem("cheese", 85, Category.EXTRAS));
		items.add(new ShopableItem("sauce", 85, Category.EXTRAS));
		items.add(new ShopableItem("cockroach", 2085, Category.EXTRAS));

		salesProducer = new OnDemandStatsProducer<>("sales", "buisness", "sales", new SalesStatsFactory());
        ProducerRegistryFactory.getProducerRegistryInstance().registerProducer(salesProducer);
	}

	@Override
	public List<ShopableItem> getShopableItems() {
		return items;
	}

	@Override
	public Order placeOrder(String customerId, String... items) {
		//log.error("Temp directory: " + System.getProperty("java.io.tmpdir"));

		//first find the order

		if (items==null)
			throw new IllegalArgumentException("No items for order");

		Order order = new Order();
		for (String item : items){
			order.addItem(findItemByName(item));

			// Ingredient counter
			ingredientCounter.ingredientUsed(item);
		}

		//now prepare notification.
		sendNotification(customerId, order.toString());

		// Order counter
        orderCounter.orderPlaced();

        int priceInCents = order.getTotalPrice();
        salesProducer.getDefaultStats().addSale(priceInCents);
        for (String item : items) {
            try {
                salesProducer.getStats(item).addSale(priceInCents);
            } catch (OnDemandStatsProducerException e) {
                log.warn("Cannot mark items");
            }
        }


		return order;
	}

	private void sendNotification(String customerId, String orderDescription){
		//the following line is a bug, it has been put here to demonstrate detection of call methods.
		log.debug("Should send notification for message to customer "+customerId+" -> "+notificationService.shouldNotificationBeSentForCustomer(customerId));
		if (notificationService.shouldNotificationBeSentForCustomer(customerId)){
			notificationService.sendNotificationAboutOrder(customerId, orderDescription);
		}

	}

	private ShopableItem findItemByName(String name){
		for (ShopableItem item : items){
			if (item.getName().equals(name))
				return item;
		}
		throw new IllegalArgumentException("No such shopable item: "+name);
	}
}
