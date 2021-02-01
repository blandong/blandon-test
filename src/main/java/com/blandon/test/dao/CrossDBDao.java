package com.blandon.test.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class CrossDBDao {
	
	private static Logger logger = LoggerFactory.getLogger(CrossDBDao.class);

	@Qualifier("jdbcOracle")
	@Autowired
	JdbcTemplate jdbcTemplateOracle;
	
	@Qualifier("jdbcMaria")
	@Autowired
	JdbcTemplate jdbcTemplateMaria;
	
	
	private static String oracleJdbc = "";
	
	private static String requestHistorySQL = "";
	
	private static String requestHistorySQL2 = "" ;
			
	private static String mariaJdbc = "";
	
	public void crossDBCheck() {
		List<Long> requestIdList= new ArrayList<Long>();
		ResultSetExtractor<List<Long>> resultSetExtractor = rs -> {
			while(rs.next()) {
				requestIdList.add(rs.getLong(1));
			}
			return requestIdList;
		};
		List<Long> oResult = new NamedParameterJdbcTemplate(jdbcTemplateOracle).query(requestHistorySQL2, resultSetExtractor);
		logger.debug("oResult size: {}", oResult.size());
		AtomicInteger counter = new AtomicInteger(1);
		Map<Long, Long> sequenceNumberMap = new HashMap<Long, Long>();
		List<Long> missedRequestIdList = new ArrayList<Long>();
		oResult.forEach(requestId -> {
			String newMariaJdbc =  mariaJdbc.replaceAll("requestIdValue", String.valueOf(requestId));
			ResultSetExtractor<Long> mariaResultSetExtractor = rs -> rs.next()?rs.getLong(1):0;
			long sequenceNumber = new NamedParameterJdbcTemplate(jdbcTemplateMaria).query(newMariaJdbc, mariaResultSetExtractor);
			if(sequenceNumberMap.containsKey(sequenceNumber) && sequenceNumber != 0) {
				logger.error("**Existing request {} and current request {} has same sequence number {} **",sequenceNumberMap.get(sequenceNumber), requestId,  sequenceNumber);
			}else {
				sequenceNumberMap.put(sequenceNumber, requestId);
			}
			logger.debug("{} request {}, sequence number {}", counter.getAndIncrement(), requestId, sequenceNumber);
			if(sequenceNumber == 0) {
				missedRequestIdList.add(requestId);
				logger.info("#####missed request id: {}", requestId);
			}
		});
		
		logger.info("The whole list of missing request: {}", missedRequestIdList.stream().map(String::valueOf).collect(Collectors.joining(",")));
		
		logger.info("End the looping!!!!");
		
	}
	
	public void crossDBCheck2() {
		ResultSetExtractor<Integer> resultSetExtractor = rs -> rs.next()?rs.getInt("OCN"):0;
		int oResult = new NamedParameterJdbcTemplate(jdbcTemplateOracle).query(oracleJdbc, resultSetExtractor);
		System.out.println("Count of oresult is "+oResult);
		
		ResultSetExtractor<Integer> mariaResultSetExtractor = rs -> rs.next()?rs.getInt(1):0;
		int mResult = new NamedParameterJdbcTemplate(jdbcTemplateMaria).query(mariaJdbc, mariaResultSetExtractor);
		System.out.println("Count of mresult is "+mResult);
		
	}
	
}
