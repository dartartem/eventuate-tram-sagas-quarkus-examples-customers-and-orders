package io.eventuate.examples.tram.sagas.ordersandcustomers.orders.domain;


import io.eventuate.examples.tram.sagas.ordersandcustomers.commondomain.OrderState;
import io.eventuate.examples.tram.sagas.ordersandcustomers.messaging.common.OrderDetails;
import io.eventuate.tram.events.publisher.ResultWithEvents;

import javax.persistence.*;
import java.util.Collections;

@Entity
@Table(name="orders")
@Access(AccessType.FIELD)
public class Order {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private OrderState state;

  @Embedded
  private OrderDetails orderDetails;

  @Version
  private Long version;

  public Order() {
  }

  public Order(OrderDetails orderDetails) {
    this.orderDetails = orderDetails;
    this.state = OrderState.PENDING;
  }

  public static ResultWithEvents<Order> createOrder(OrderDetails orderDetails) {
    return new ResultWithEvents<Order>(new Order(orderDetails), Collections.emptyList());
  }

  public Long getId() {
    return id;
  }

  public void noteCreditReserved() {
    this.state = OrderState.APPROVED;
  }

  public void noteCreditReservationFailed() {
    this.state = OrderState.REJECTED;
  }

  public OrderState getState() {
    return state;
  }
}
