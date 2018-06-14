package com.sati.web.app;

import HibernateClasses.ProductionLine;
import HibernateClasses.ProductionOrder;
import HibernateConnector.DataBaseConnector;

import java.util.ArrayList;
import java.util.Collections;

public class ProductionOrders {

    static ArrayList<ProductionOrder> productionOrderArrayList;

    public static ArrayList<ProductionOrder> getProductionOrderArrayList() {
        return productionOrderArrayList;
    }

    ProductionOrders(Integer productionLineName) {

        ProductionLine productionLine = DataBaseConnector.getProductionLine(productionLineName);
        productionOrderArrayList = DataBaseConnector.getProductionOrders(productionLine);
        Collections.sort(productionOrderArrayList);

    }
}
