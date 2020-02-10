package com.blandon.test.dao;

import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import com.blandon.test.bean.User;
import com.google.common.flogger.FluentLogger;

@Repository
public class UserDao extends JdbcDaoSupport {
	
	private static final FluentLogger logger = FluentLogger.forEnclosingClass();
	
	private static final String sql ="MERGE INTO AUTHENTICATION_LOG a"
	         + " USING (SELECT 1 FROM DUAL)"
	         + " ON (a.id = :userId and a.realm= :realm)"
	         + " WHEN MATCHED THEN"
	         + " UPDATE SET a.authenticated_time= :authenticatedTime, a.version=a.version+1"
	         + " WHEN NOT MATCHED THEN"
	         + " INSERT (ID, CUID, AUTHENTICATED_TIME, REALM, VERSION, CREATOR, CREATION_INSTANT, CREATOR_APPLICATION_ID)"
	           + " VALUES (:userId, :cuid, :authenticatedTime, :realm, :version, :creator, :creationInstant, :creatorAppId)";
	
	public User findByName(String name){
		User user = new User(name);
		return user;
	}
	
	
	public User save(User user){
		return user;
	}
	
	
	public int saveAuthn() {
		int row = addOrUpdateAuthenticationLog(sql);
		return row;
	}
	
	
	
    private int addOrUpdateAuthenticationLog( String query) {

        final MapSqlParameterSource args = new MapSqlParameterSource();

        args.addValue("userId", "[S-A415-T34]S-A415-T34_ADMIN1");
        args.addValue("cuid", "[S-A415-T34]S-A415-T34_ADMIN");
        args.addValue("authenticatedTime", System.currentTimeMillis());
        args.addValue("realm", "S-A415-T34");
        args.addValue("version", 200);
        args.addValue("creator", "[S-A415-T34]S-A415-T34_ADMIN");
        args.addValue("creationInstant", System.currentTimeMillis());
        args.addValue("creatorAppId", "{application}cca");

        final int rowsUpdated = new NamedParameterJdbcTemplate(getJdbcTemplate()).update(query, args);

        logger.atFine().log("Rows touched: %s", rowsUpdated);
        
        return rowsUpdated;
    }
    
    public static void main(String[] test) {
    	 final MapSqlParameterSource args = new MapSqlParameterSource();

         args.addValue("userId", "[S-A415-T34]S-A415-T34_ADMIN1");
         args.addValue("cuid", "[S-A415-T34]S-A415-T34_ADMIN");
         args.addValue("authenticatedTime", System.currentTimeMillis());
         args.addValue("realm", "S-A415-T34");
         args.addValue("version", 200);
         args.addValue("creator", "[S-A415-T34]S-A415-T34_ADMIN");
         args.addValue("creationInstant", System.currentTimeMillis());
         args.addValue("creatorAppId", "{application}cca");
    	final int rowsUpdated=1;
    	logger.atFine().log("Rows touched: %s", args.getValues());
    	
    	logger.atFine().log("Rows touched: {}", args.getValues());
	}
	
	
}
