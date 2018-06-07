package HibernateConnector;

import HibernateClasses.Employee;
import HibernateClasses.Pallete;
import org.hibernate.*;
import org.hibernate.cfg.Configuration;


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
        }
        return e;
    }

}
