package com.neoforth.sample.mysql.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.ExecutorType;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.neoforth.sample.mysql.vo.MysqlVO;


@Repository("sample.mysqlDAO")
public class MysqlDaoImpl implements MysqlDao{

	@Autowired
	private SqlSession sqlSession;
	
	
	@Override
	public int mysqlListCnt(MysqlVO paramVO) throws Exception {
		return sqlSession.selectOne("sample.mysql.mysql.mysqlListCnt", paramVO);
	}
	
	
	@Override
	public List<MysqlVO> mysqlList(MysqlVO paramVO) throws Exception{
		return sqlSession.selectList("sample.mysql.mysql.mysqlList", paramVO);
	}
	
	
	@Override
	public MysqlVO mysqlView(MysqlVO paramVO) throws Exception{
		return sqlSession.selectOne("sample.mysql.mysql.mysqlView", paramVO);
	}

	
	@Override
	public int mysqlInsert(MysqlVO paramVO) throws Exception{
		return sqlSession.insert("sample.mysql.mysql.mysqlInsert", paramVO);
	}

	
	@Override
	public int mysqlUpdate(MysqlVO paramVO) throws Exception{
		return sqlSession.update("sample.mysql.mysql.mysqlUpdate", paramVO);
	}

	
	@Override
	public int mysqlDelete(MysqlVO paramVO) throws Exception{
		return sqlSession.update("sample.mysql.mysql.mysqlDelete", paramVO);
	}

	
	@Override
	public List<Map<String, Object>> excelDownload(MysqlVO paramVO) throws Exception{
		return sqlSession.selectList("sample.mysql.mysql.excelDownload", paramVO);
	}

	@Override
	@Transactional
	public int mysqlInsertExcel(List<MysqlVO> paramVO) throws Exception {
		SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(sqlSession.getConfiguration());
		SqlSession session = factory.openSession(ExecutorType.BATCH);
		int result = 0;
		
		try {
			for (int i = 0; i < paramVO.size(); i++) {
				result = session.insert("sample.mysql.mysql.mysqlInsertExcel", paramVO.get(i));
				if (i != 0 && (i % 10000) == 0) {
					session.flushStatements();
				}
				paramVO.set(i, null);
			}
			session.flushStatements();
			session.close();
		} catch (Exception e) {
			session.rollback();
			e.printStackTrace();
		}
		return result;
	}
}

