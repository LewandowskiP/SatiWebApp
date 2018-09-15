package com.sati.web.app;

import HibernateConnector.DataBaseConnector;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestHandler {

    @GetMapping("/productionLine")
    public synchronized String productionLine(@RequestParam(value = "name") Integer name) {
        String serialized = null;
        try {
            serialized = new ObjectMapper().writeValueAsString(new ProductionOrders(name).getProductionOrderArrayList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return serialized;
    }

    @GetMapping("/productionLines")
    public synchronized String productionLine() {
        String serialized = null;
        try {
            serialized = new ObjectMapper().writeValueAsString(new ProductionLines().getProductionLineArrayList());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return serialized;
    }

    @GetMapping("/startProductionOrder")
    public synchronized String startProductionOrder(@RequestParam(value = "id") Integer id) {
        String toReturn = "Wystąpił błąd.";
        if (DataBaseConnector.startProductionOrder(id)) toReturn = "Zaakceptowano zlecenie.";
        return toReturn;
    }

    @GetMapping("/finishProductionOrder")
    public synchronized String finishProductionOrder(@RequestParam(value = "id") Integer id) {
        String toReturn = "Wystąpił błąd.";
        if (DataBaseConnector.finishProductionOrder(id)) toReturn = "Zlecenie zakończone";
        return toReturn;
    }


    @GetMapping("/pauseProductionOrder")
    public synchronized String pauseProductionOrder(@RequestParam(value = "id") Integer id) {
        String toReturn = "Wystąpił błąd.";
        if (DataBaseConnector.pauseProductionOrder(id)) toReturn = "Zlecenie zakończone";
        return toReturn;
    }

    @GetMapping("/unpauseProductionOrder")
    public synchronized String unpauseProductionOrder(@RequestParam(value = "id") Integer id) {
        String toReturn = "Wystąpił błąd.";
        if (DataBaseConnector.unpauseProductionOrder(id)) toReturn = "Zlecenie zakończone";
        return toReturn;
    }
}
