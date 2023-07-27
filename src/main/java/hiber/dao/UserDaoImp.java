package hiber.dao;

import hiber.model.Car;
import hiber.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserDaoImp implements UserDao {

   @Autowired
   private SessionFactory sessionFactory;

   @Override
   public void add(User user) {
      sessionFactory.getCurrentSession().save(user);
   }

   @Override
   @Transactional
   public void add(User user, Car car) {
      sessionFactory.getCurrentSession().save(user);
      car.setUser(user);
      sessionFactory.getCurrentSession().save(car);
   }


   @Override
   @SuppressWarnings("unchecked")
   public List<User> listUsers() {
      TypedQuery<User> query = sessionFactory.getCurrentSession().createQuery("from User");
      return query.getResultList();
   }

   @Override
   @Transactional
   public User UsersByCar(String model, int series) {
      Query query = sessionFactory.getCurrentSession().createQuery("FROM User WHERE car.model = :model AND car.series = :series");
      query.setParameter("model", model);
      query.setParameter("series", series);
      return (User) query.uniqueResult();
   }
}
