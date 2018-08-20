package HibernateConnector;

import HibernateClasses.Employee;
import HibernateClasses.Pallete;
import HibernateClasses.ProductionLine;
import HibernateClasses.ProductionOrder;
import com.sati.web.app.States;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;


import javax.persistence.FlushModeType;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DataBaseConnector {


    private static final String CONFIG_URL = "hibernate.cfg.xml";
    private static SessionFactory sf = null;
    private static Session s = null;
    private static Configuration cfg = null;


    public DataBaseConnector() {

        cfg = new Configuration();
        cfg.configure();
        sf = cfg.buildSessionFactory();

    }


    public static void openSession() {
        if (s != null) {
            if (s.isOpen()) {
                return;
            }
        }
        s = sf.openSession();
    }


    public static void reinitializeSession() {
        s.flush();
    }


    public static Pallete getPallete(Integer palleteNumber) {
        Pallete pallete = null;
        String hql = "FROM Pallete P WHERE P.ean128Num = :palleteNumber";
        Query q = s.createQuery(hql);
        q.setParameter("palleteNumber", palleteNumber.toString());
        List result = q.list();
        if (result.size() > 0) {
            pallete = (Pallete) result.get(0);
            s.refresh(pallete);
            s.refresh(pallete.getProductionRaportPart());
            Hibernate.initialize(pallete.getProductionRaportPart());
            Hibernate.initialize(pallete.getProductionRaportPart().getEmployee());
            Hibernate.initialize(pallete.getProductionRaportPart().getProductType());
        }
        return pallete;
    }

    public static void updateObject(Object object) {
        Transaction t = s.beginTransaction();
        s.update(object);
        t.commit();
    }

    public static Employee authorizeUser(String login, String password) {
        Employee e = null;
        String hql = "FROM Employee E WHERE ( E.login = :empLogin AND E.password = :empPassword )";
        Query q = s.createQuery(hql);
        q.setParameter("empLogin", login);
        q.setParameter("empPassword", password);
        List result = q.list();
        if (result.size() > 0) {
            e = (Employee) result.get(0);
            Hibernate.initialize(e);
        }
        return e;

    }

    public static ArrayList<ProductionOrder> getProductionOrders(ProductionLine productionLine) {
        ArrayList<ProductionOrder> toReturn = new ArrayList<>();
        String hql = "FROM ProductionOrder PO WHERE PO.productionLine = :productionLine AND ( PO.state = :state1 OR PO.state = :state2)";
        Query q = s.createQuery(hql);
        q.setParameter("productionLine", productionLine);
        q.setParameter("state1", States.PRODUCTION_ORDER_ORDERED);
        q.setParameter("state2", States.PRODUCTION_ORDER_INPROGRESS);
        toReturn.addAll(q.list());
        for (ProductionOrder productionOrder : toReturn) {
            s.refresh(productionOrder);
            Hibernate.initialize(productionOrder);
            Hibernate.initialize(productionOrder.getOrderedBy());
            Hibernate.initialize(productionOrder.getCompletedBy());
            Hibernate.initialize(productionOrder.getProductionLine());
            Hibernate.initialize(productionOrder.getProductType());
        }
        return toReturn;
    }

    public static ProductionLine getProductionLine(Integer productionLineName) {
        ProductionLine toReturn = null;
        String hql = "FROM ProductionLine PL WHERE PL.id = :productionLineName ";
        Query q = s.createQuery(hql, ProductionLine.class);
        q.setParameter("productionLineName", productionLineName);
        if (q.list().size() > 0) {
            toReturn = (ProductionLine) q.list().get(0);
        }
        return toReturn;
    }

    public static ArrayList<ProductionLine> getProductionLines() {
        ArrayList<ProductionLine> toReturn = new ArrayList<>();
        String hql = "FROM ProductionLine";
        Query q = s.createQuery(hql);
        toReturn.addAll(q.list());
        return toReturn;
    }

    public synchronized static boolean startProductionOrder(Integer id) {
        boolean toReturn = false;
        String hql = "FROM ProductionOrder PO WHERE PO.id = :id";
        Query q = s.createQuery(hql);
        q.setParameter("id", id);
        List result = q.list();
        if (!(result.size() == 0)) {
            s.getTransaction().begin();
            ProductionOrder po = (ProductionOrder) result.get(0);
            po.setState(States.PRODUCTION_ORDER_INPROGRESS);
            s.update(po);
            s.getTransaction().commit();
            toReturn = true;
        }
        return toReturn;
    }

    public synchronized static boolean finishProductionOrder(Integer id) {
        boolean toReturn = false;
        String hql = "FROM ProductionOrder PO WHERE PO.id = :id";
        Query q = s.createQuery(hql);
        q.setMaxResults(1);
        q.setParameter("id", id);
        List result = q.list();
        ArrayList<ProductionOrder> toShift;
        if (!(result.size() == 0)) {
            ProductionOrder po = (ProductionOrder) result.get(0);
            po.setState(States.PRODUCTION_ORDER_COMPLETED);
            po.setCompleteTime(new Timestamp(System.currentTimeMillis()));
            String hql2 = "FROM ProductionOrder PO WHERE PO.productionLine = :line AND PO.positionInQueue > :positionInQueue";
            Query q2 = s.createQuery(hql2);
            q2.setParameter("line", po.getProductionLine());
            q2.setParameter("positionInQueue", po.getPositionInQueue());
            s.getTransaction().begin();
            toShift = (ArrayList<ProductionOrder>) q2.list();
            shiftProductionOrders(toShift);
            s.update(po);
            s.getTransaction().commit();
            toReturn = true;
        }
        return toReturn;
    }

    private synchronized static void shiftProductionOrders(ArrayList<ProductionOrder> alpo) {
        for (ProductionOrder po : alpo) {
            po.upQueue();
            s.update(po);
        }
    }
}
