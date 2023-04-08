package com.driver;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public class Repository {

    HashMap<String, Order> orderDB= new HashMap<>();
    HashMap<String, DeliveryPartner>partnerDB = new HashMap<>();
    HashMap<String,String> orderToPartnerDB= new HashMap<>();

    HashMap<String, HashSet<String>>totalOrdersToPartnerDB= new HashMap<>();

    public void addOrder(Order order){
    orderDB.put(order.getId(), order);
    }

    public void addPartner(String partnerId){
    DeliveryPartner deliveryPartner = new DeliveryPartner(partnerId);
    String key = deliveryPartner.getId();
    partnerDB.put(key, deliveryPartner);
    }

    public void addOrdrPartnerPair(String orderId, String partnerId){
    if (orderDB.containsKey(orderDB)&& partnerDB.containsKey(partnerId)){
        HashSet<String>subset= new HashSet<>();
        if(totalOrdersToPartnerDB.containsKey(partnerId)){
            subset=totalOrdersToPartnerDB.get(partnerId);
        }
        subset.add(orderId);
        totalOrdersToPartnerDB.put(partnerId,subset);
        orderToPartnerDB.put(orderId,partnerId);
    }
    }

    public Order getOrderById(String id){
     Order order= orderDB.get(id);
     return order;
    }
    public DeliveryPartner getPartnerById(String id){
        DeliveryPartner deliveryPartner = partnerDB.get(id);
        return deliveryPartner;
    }

    public  Integer getOrderCountByPartnerId(String id){
//        Integer count =0;
//        if (totalOrdersToPartnerDB.containsKey(id)){
//          count =totalOrdersToPartnerDB.get(id).size();
//        }
//        return count;
        int orderCount = 0;

        if(totalOrdersToPartnerDB.containsKey(id))
            orderCount = totalOrdersToPartnerDB.get(id).size();

        return orderCount;
  }

    public List<String> getOrdersByPartnerId(String id){
    HashSet <String> ans=null;
    if (totalOrdersToPartnerDB.containsKey(id)){
        ans= totalOrdersToPartnerDB.get(id);
    }
    return new ArrayList<>(ans);
    }

    public List<String>getAllOrders(){
    return new ArrayList<>(orderDB.keySet());
    }

    public  Integer getCountOfUnassignedOrders(){
//    Integer count =0;
//    int total= orderDB.size();
//    int assignedo= orderToPartnerDB.size();
//    count =total-assignedo;
//    return count;
        Integer countOfOrders = 0;

        List<String> list = new ArrayList<>(orderDB.keySet());

        for(String st : list){
            if(!orderToPartnerDB.containsKey(st))
                countOfOrders += 1;
        }

        return countOfOrders;
    }

    public  Integer getOrderLeftAfterGivenTimeByPartnerId(String time,String partnerId){
       int coutn=0;
       int h = Integer.valueOf(time.substring(0,2));
       int m = Integer.valueOf(time.substring(3));
       int total= h*60+m;

       if (totalOrdersToPartnerDB.containsKey(partnerId)){
           HashSet<String >hs = totalOrdersToPartnerDB.get(partnerId);
           for (String s:hs){
               if (orderDB.containsKey(s)){
                   Order order = orderDB.get(s);
                   if(total<order.getDeliveryTime()){
                       coutn++;
                   }
               }
           }
       }
       return coutn;
    }

    public String getLastDeliveryTimeByPartnerId(String partnerId){
        String time = null;
        int delivery_time = 0;

        if(partnerDB.containsKey(partnerId))
        {
            HashSet<String> list = totalOrdersToPartnerDB.get(partnerId);

            for(String st : list)
            {
                if(orderDB.containsKey(st))
                {
                    Order order = orderDB.get(st);

                    if(delivery_time < order.getDeliveryTime())
                        delivery_time = order.getDeliveryTime();
                }
            }
        }
        StringBuilder str = new StringBuilder();
        int hr = delivery_time / 60;
        if(hr < 10)
            str.append(0).append(hr);
        else
            str.append(hr);

        str.append(":");

        int min = delivery_time - (hr*60);
        if(min < 10)
            str.append(0).append(min);
        else
            str.append(min);

        return str.toString();
    }

    public void deletePartnerById(String id){
     HashSet<String>hs = new HashSet<>();
     if (totalOrdersToPartnerDB.containsKey(id)){
         hs=totalOrdersToPartnerDB.get(id);
         for (String s:hs) {
             if (orderToPartnerDB.containsKey(s)) {
                 orderToPartnerDB.remove(s);
             }
         }
             totalOrdersToPartnerDB.remove(id);
     }
     if (partnerDB.containsKey(id)){
         partnerDB.remove(id);
     }
    }
    public void deleteOrderbyId(String orderId){
        if(orderToPartnerDB.containsKey(orderId))
        {
            String partnerId = orderToPartnerDB.get(orderId);

            HashSet<String> list = totalOrdersToPartnerDB.get(partnerId);
            list.remove(orderId);
            totalOrdersToPartnerDB.put(partnerId,list);

            DeliveryPartner deliveryPartner = partnerDB.get(partnerId);
            deliveryPartner.setNumberOfOrders(list.size());
        }

        if(orderDB.containsKey(orderId)) {
            orderDB.remove(orderId);
        }
    }
}