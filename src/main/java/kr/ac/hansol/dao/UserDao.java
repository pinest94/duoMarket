package kr.ac.hansol.dao;

import java.util.List;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import kr.ac.hansol.model.User;

@Repository // bean으로 등록
@Transactional // 각 메소드들을 transaction으로 등록
public class UserDao {

	@Autowired
	private PasswordEncoder passwordEncoder; // hashing을 이용. 암호화 x

	@Autowired
	private SessionFactory sessionFactory;

	public void addUser(User user) {

		Session session = sessionFactory.getCurrentSession();
		user.setPassword(passwordEncoder.encode(user.getPassword())); // 사용자가 입력한 plain text를 encoding 해주고 저장한다

		session.saveOrUpdate(user);

		session.flush();
	}

	public User getUserById(int userId) {

		Session session = sessionFactory.getCurrentSession();

		return (User) session.get(User.class, userId); // userId에 해당하는 객체를 return
	}

	@SuppressWarnings("unchecked")
	public User getUserByUsername(String username) {

		Session session = sessionFactory.getCurrentSession();

		TypedQuery<User> query = session.createQuery("from User where username = ?0");
		query.setParameter(0, username);

		return query.getSingleResult();
	}

	@SuppressWarnings("unchecked")
	public List<User> getAllUsers() {

		Session session = sessionFactory.getCurrentSession();

		TypedQuery<User> query = session.createQuery("from User");
		List<User> userList = query.getResultList();

		return userList;
	}

}
