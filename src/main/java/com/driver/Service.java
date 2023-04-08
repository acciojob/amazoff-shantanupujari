package com.driver;

import java.util.List;

public class Service {
Repository repository = new Repository();
public void addOrder(Order order){
   repository.addOrder(order);
}

public void addPartner(String partnerId){
repository.addPartner(partnerId);
}

public void addOrderPartnerPair(String orderId, String partnerId){
 repository.addOrdrPartnerPair(orderId,partnerId);
}
public Order getOrderById(String id){
  Order order= repository.getOrderById(id);
  return order;
}

public DeliveryPartner getPartnerById(String id){
   DeliveryPartner deliveryPartner= repository.getPartnerById(id);
   return deliveryPartner;
}
public  Integer getOrderCountByPartnerId(String id){
    Integer ans = getOrderCountByPartnerId(id);
    return ans;
}
public List<String> getOrdersByPartnerId(String id){
   List<String >ans = repository.getOrdersByPartnerId(id);
   return ans;
}

public List<String>getAllOrders(){
    List<String >ans = null;
    ans= repository.getAllOrders();
    return ans;
}
public  Integer getCountOfUnassignedOrders(){
  return repository.getCountOfUnassignedOrders();
}

public  Integer getOrderLeftAfterGivenTimeByPartnerId(String time,String partnerId){
return repository.getOrderLeftAfterGivenTimeByPartnerId(time,partnerId);
}
public String getLastDeliveryTimeByPartnerId(String partnerId){
   return repository.getLastDeliveryTimeByPartnerId(partnerId);
}
public void deletePartnerById(String id){
    repository.deletePartnerById(id);
}
public void deleteOrderbyId(String orderId){
 repository.deleteOrderbyId(orderId);
}
}