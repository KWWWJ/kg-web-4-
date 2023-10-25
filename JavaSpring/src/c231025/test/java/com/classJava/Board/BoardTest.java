package c231025.test.java.com.classJava.Board;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.lang.reflect.Proxy;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.PlatformTransactionManager;

import c231024.main.java.com.classJava.board.dao.BoardDAO;
import c231024.main.java.com.classJava.board.domain.Board;
import c231024.main.java.com.classJava.board.service.BoardService;
import c231024.main.java.com.classJava.board.service.BoardServiceImpl;
import c231024.main.java.com.classJava.user.dao.UserDAO;
import c231024.main.java.com.classJava.user.domain.User;
import c231025.main.java.com.classJava.board.service.TransactionHandler;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "/c231024/dataSource.xml", "applicationContext.xml"})
public class BoardTest {
	@Autowired
	BoardDAO boardDAO;
	@Autowired
	UserDAO userDAO;
	@Autowired
	BoardService boardService;                                                                                            																							
	@Autowired
	BoardServiceImpl boardServiceImpl;
	@Autowired
	PlatformTransactionManager transactionManager ;

	@Before
	public void initialize() {
		boardDAO.deleteAll();
		User user = userDAO.get("kwj");
		boardService.add(new Board(user, "테스트1", "테스트1 내용"), 1);
		boardService.add(new Board(user, "테스트2", "테스트2 내용"), 1);
		boardService.add(new Board(user, "테스트3", "테스트3 내용"), 1);                                                             																							
	}

	@Test
	public void getAll() {
		List<Board> list = boardService.getAll();
		User user = userDAO.get("kwj");
		for (int i = 0; i < list.size(); i++) {
			assertThat(list.get(i).getTitle(), is("테스트"+(i+1)));
			assertThat(list.get(i).getContent(), is("테스트"+(i+1)+" 내용"));
			assertThat(list.get(i).getUser().getId(), is(user.getId()));
			assertThat(list.get(i).getUser().getUserId(), is(user.getUserId()));
			assertThat(list.get(i).getUser().getPassword(), is(user.getPassword()));
		}
	}

	@Test
	public void updateAll() {
		TransactionHandler txHandler = new TransactionHandler();
		txHandler.setTarget(boardService);
		txHandler.setPattern("update");
		txHandler.setTransactionManager(transactionManager);
		BoardService txBoardService = (BoardService) Proxy.newProxyInstance(
				getClass().getClassLoader(), 
				new Class[] {BoardService.class}, 
				txHandler);
		
		User user = userDAO.get("kwj");
		txBoardService.updateAll(user);

	}

	@Test
	public void add() {
		Board board = new Board(userDAO.get("kwj"), "테스트 중입니다", "23년 10월 24일 테스트");
		boardService.add(board, 1);
	}

}