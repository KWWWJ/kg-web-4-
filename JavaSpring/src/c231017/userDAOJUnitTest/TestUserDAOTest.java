package c231017.userDAOJUnitTest;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.sql.SQLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.dao.DuplicateKeyException;

import c231017.user.UsedSpringUserDAO;
import c231017.user.UserBean;
import c231017.user.UserInterface;
import c231017.factory.DAOFactory;
import c231017.testUser.TestUserDAO;


public class TestUserDAOTest {

	private UserInterface user1 = new UserBean();
	private ApplicationContext context = new AnnotationConfigApplicationContext(DAOFactory.class);
	private TestUserDAO dao = context.getBean("testUserDAO", TestUserDAO.class); // 얘 기능 나눠서 그때그떄 생성해도 상관 없다.
																				 // 일할떄는 두가지로 나눠 테스트용과 배포용을 만들어 배포에는 테이블 삭제 및 생성 기능을 제외한다.
	@Before // test하기 전에 실행한다.
	public void initialize() {

		try {

			dao.create();
			System.out.println("user 테이블 생성 성공");
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}

		user1.setName("권원준");
		user1.setUserId("dnx2");
		user1.setPassword("qwer");
		dao.add(user1);

	}
	
	@After
	public void dropTable() {

		dao.upgradeDrop();
		System.out.println("user 테이블 삭제 성공");

	}

	@Test
	public void add() {

		UserBean user = new UserBean();
		user.setName("권원준");
		user.setUserId("dnx5");
		user.setPassword("qwer");
		dao.add(user);
		System.out.println("추가 성공 addAndGet");

	}

	@Test
	public void get() {

		UserInterface createdUser = dao.get(user1.getUserId());
		assertThat(createdUser.getName(), is(user1.getName()));
		assertThat(createdUser.getUserId(), is(user1.getUserId()));
		assertThat(createdUser.getPassword(), is(user1.getPassword()));

	}

	@Test
	public void dropAndCreate() {

		UserBean user = new UserBean();
		user.setName("권원준");
		user.setUserId("dnx4");
		user.setPassword("qwer");
		dao.add(user);
		System.out.println("추가 성공 addAndGet");

		UserInterface createdUser = dao.get(user.getUserId());
		assertThat(createdUser.getName(), is(user.getName()));
		assertThat(createdUser.getUserId(), is(user.getUserId()));
		assertThat(createdUser.getPassword(), is(user.getPassword()));

	}
	
	@Test(expected=DuplicateKeyException.class)
	public void duplicate() {
		
		UserBean user2 = new UserBean();
		user2.setName("asdf");
		user2.setUserId("asdf");
		user2.setPassword("adsf");
		dao.add(user2);
		
		UserBean user3 = new UserBean();
		user3.setName("asdf");
		user3.setUserId("asdf");
		user3.setPassword("asdf");
		dao.add(user3);
		
	}

}
