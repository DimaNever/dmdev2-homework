package com.dmdevmvn.service.dao;

import com.dmdevmvn.service.entity.Car;
import com.dmdevmvn.service.entity.Order;
import com.dmdevmvn.service.entity.OrderSpareParts;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.hibernate.Session;
import org.hibernate.graph.GraphSemantic;

import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CarDaoWithEntityGraphs {

    private static final CarDaoWithEntityGraphs INSTANCE = new CarDaoWithEntityGraphs();

    public List<Car> findAll(Session session) {
        var carRootGraph = session.createEntityGraph(Car.class);
        carRootGraph.addAttributeNodes("client");

        return session.createQuery("select c from Car c", Car.class)
                .setHint(GraphSemantic.LOAD.getJpaHintName(), carRootGraph)
                .list();
    }

    public Car findAllById(Session session, Long id) {
        var carRootGraph = session.createEntityGraph(Car.class);
        carRootGraph.addAttributeNodes("client", "orders");

        var orderSubGraph = carRootGraph.addSubgraph("orders", Order.class);
        orderSubGraph.addAttributeNodes("orderSpareParts");

        var orderSpareParts = orderSubGraph.addSubgraph("orderSpareParts", OrderSpareParts.class);
        orderSpareParts.addAttributeNodes("sparePart");

        Map<String, Object> properties = Map.of(
                GraphSemantic.LOAD.getJpaHintName(), carRootGraph
        );

        return session.find(Car.class, id, properties);
    }


    public static CarDaoWithEntityGraphs getInstance() {
        return INSTANCE;
    }
}
