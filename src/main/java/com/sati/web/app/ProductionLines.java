package com.sati.web.app;

import HibernateClasses.ProductionLine;
import HibernateClasses.ProductionOrder;
import HibernateConnector.DataBaseConnector;

import java.util.ArrayList;
import java.util.Collections;

public class ProductionLines {

    static ArrayList<ProductionLine> productionLineArrayList;

    public static ArrayList<ProductionLine> getProductionLineArrayList() {
        return productionLineArrayList;
    }

    ProductionLines() {

        productionLineArrayList = DataBaseConnector.getProductionLines();
        Collections.sort(productionLineArrayList);

    }
}
