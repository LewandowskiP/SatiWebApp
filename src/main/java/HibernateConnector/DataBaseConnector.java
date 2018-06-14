package HibernateConnector;

import HibernateClasses.Employee;
import HibernateClasses.Pallete;
import HibernateClasses.ProductionLine;
import HibernateClasses.ProductionOrder;
import com.sati.web.app.States;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;


import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnector {


    private static final String CONFIG_URL = "hibernate.cfg.xml";
    private static SessionFactory sf = null;
    private static Session s = null;
    private static Transaction transaction = null;


    public static void openSession() {
        if (s != null) {
            if (s.isOpen()) {
                return;
            }
        }
        s = sf.openSession();

    }

    public static void closeSession() {

        if (s != null) {
            if (s.isOpen()) {
                s.flush();
                s.close();
            }
        }


    }


    public DataBaseConnector() {
        Configuration cfg = new Configuration();
        cfg.configure(this.getClass().getClassLoader().getResource(CONFIG_URL));
        sf = cfg.buildSessionFactory();
    }

    public static Pallete getPallete(Integer palleteNumber) {
        openSession();
        Pallete pallete = null;
        String hql = "FROM Pallete P WHERE P.ean128Num = :palleteNumber";
        Query q = s.createQuery(hql);
        q.setParameter("palleteNumber", palleteNumber.toString());
        List result = q.list();
        if (result.size() > 0) {
            pallete = (Pallete) result.get(0);
            Hibernate.initialize(pallete.getProductionRaportPart());
            Hibernate.initialize(pallete.getProductionRaportPart().getEmp());
            Hibernate.initialize(pallete.getProductionRaportPart().getProductType());
        }
        closeSession();
        return pallete;

    }

    public static void updateObject(Object object) {
        Transaction t = s.beginTransaction();
        s.update(object);
        t.commit();
    }

    public static Employee authorizeUser(String login, String password) {
        openSession();
        Employee e = null;
        String hql = "FROM Employee E WHERE ( E.login = :empLogin AND E.password = :empPassword )";
        Query q = s.createQuery(hql);
        q.setParameter("empLogin", login);
        q.setParameter("empPassword", password);
        List result = q.list();
        if (result.size() > 0) {
            e = (Employee) result.get(0);
        }
        closeSession();
        return e;

    }

    public static ArrayList<ProductionOrder> getProductionOrders(ProductionLine productionLine) {
        openSession();
        ArrayList<ProductionOrder> toReturn = new ArrayList<>();
        String hql = "FROM ProductionOrder PO WHERE PO.productionLine = :productionLine AND ( PO.state = :state1 OR PO.state = :state2)";
        Query q = s.createQuery(hql);
        q.setParameter("productionLine", productionLine);
        q.setParameter("state1", States.PRODUCTION_ORDER_ORDERED);
        q.setParameter("state2", States.PRODUCTION_ORDER_INPROGRESS);
        toReturn.addAll(q.list());
        for (ProductionOrder productionOrder : toReturn) {
            Hibernate.initialize(productionOrder);
            Hibernate.initialize(productionOrder.getOrderedBy());
            Hibernate.initialize(productionOrder.getCompletedBy());
            Hibernate.initialize(productionOrder.getProductionLine());
            Hibernate.initialize(productionOrder.getProductType());
        }
        closeSession();
        return toReturn;
    }

    public static ProductionLine getProductionLine(Integer productionLineName) {
        openSession();
        ProductionLine toReturn = null;
        String hql = "FROM ProductionLine PL WHERE PL.id = :productionLineName ";
        Query q = s.createQuery(hql);
        q.setParameter("productionLineName", productionLineName);
        if (q.list().size() > 0) {
            toReturn = (ProductionLine) q.list().get(0);
        }
        closeSession();
        return toReturn;
    }

    public static ArrayList<ProductionLine> getProductionLines() {
        openSession();
        ArrayList<ProductionLine> toReturn = new ArrayList<>();
        String hql = "FROM ProductionLine";
        Query q = s.createQuery(hql);
        toReturn.addAll(q.list());
        closeSession();
        return toReturn;
    }

    public static boolean startProductionOrder(Integer id) {
        openSession();
        boolean toReturn = false;
        String hql = "FROM ProductionOrder PO WHERE PO.id = :id";
        Query q = s.createQuery(hql);
        q.setParameter("id", id);
        List result = q.list();
        if (!(result.size() == 0)) {
            s.getTransaction().begin();
            ProductionOrder po = (ProductionOrder) result.get(0);
            po.setState(States.PRODUCTION_ORDER_INPROGRESS);
            s.save(po);
            s.getTransaction().commit();
            toReturn = true;
        }
        closeSession();
        return toReturn;
    }

    public static boolean finishProductionOrder(Integer id) {
        openSession();
        boolean toReturn = false;
        String hql = "FROM ProductionOrder PO WHERE PO.id = :id";
        Query q = s.createQuery(hql);
        q.setParameter("id", id);
        List result = q.list();
        ArrayList<ProductionOrder> toShift;
        if (!(result.size() == 0)) {
            ProductionOrder po = (ProductionOrder) result.get(0);
            po.setState(States.PRODUCTION_ORDER_COMPLETED);
            po.setCompleteTime(new Timestamp(System.currentTimeMillis()));

            String hql2 = "FROM ProductionOrder PO WHERE PO.productionLine = :line AND PO.positionInQueue > 0";
            Query q2 = s.createQuery(hql2);
            q2.setParameter("line", po.getProductionLine());
            s.getTransaction().begin();
            toShift = (ArrayList<ProductionOrder>) q2.list();
            shiftProductionOrders(toShift);
            s.save(po);
            s.getTransaction().commit();
            toReturn = true;
        }
        closeSession();
        return toReturn;
    }

    private static void shiftProductionOrders(ArrayList<ProductionOrder> alpo) {
        for (ProductionOrder po : alpo) {
            po.upQueue();
            s.saveOrUpdate(po);
        }
    }
}
