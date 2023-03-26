package org.vanilladb.bench.benchmarks.as2.rte.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.bench.remote.SutResultSet;
import org.vanilladb.bench.remote.jdbc.VanillaDbJdbcResultSet;
import org.vanilladb.bench.rte.jdbc.JdbcJob;
import org.vanilladb.bench.server.param.as2.UpdateItemPriceParamHelper;

import java.util.Random;
import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;

public class UpdateItemPriceTxnJdbcJob implements JdbcJob {
    private static Logger logger = Logger.getLogger(UpdateItemPriceTxnJdbcJob.class
            .getName());

    private static Random rand = new Random();
    @Override
    public SutResultSet execute(Connection conn, Object[] pars) throws SQLException {
        UpdateItemPriceParamHelper paramHelper = new UpdateItemPriceParamHelper();
        paramHelper.prepareParameters(pars);

        // Output message
        try{
            Statement statement = conn.createStatement();
            ResultSet rs = null;

            for(int i = 0 ; i < 10 ; i++){
                int id = paramHelper.getRandomId();
                double updateValue = paramHelper.getUpdateValue();
                String selectSql = "SELECT i_price FROM item WHERE i_id = " + id;
                rs = statement.executeQuery(selectSql);
                rs.beforeFirst();
                double oldPrice = 0, newPrice = 0;
                if (rs.next()) {
                    oldPrice = rs.getDouble("i_price");
                } else{
                    if (logger.isLoggable(Level.INFO))
                        logger.info(String.format("%d not found.", id));
                    throw new RuntimeException("cannot find the record with i_id = " + id);
                }
                rs.close();
                if(oldPrice > As2BenchConstants.MAX_PRICE){
                    newPrice = As2BenchConstants.MIN_PRICE;
                }else{
                    newPrice = oldPrice + updateValue;
                }
                String updateSql = "UPDATE item SET i_price=" + newPrice + "WHERE i_id=" + id;
                int cnt = statement.executeUpdate(updateSql);
//                if (logger.isLoggable(Level.INFO))
//                    logger.info(String.format("[JDBC] ID:%d update price from %f to %f.\n", id, oldPrice, newPrice));
            }
            conn.commit();
            return new VanillaDbJdbcResultSet(true, "Success");
        } catch (Exception e) {
            if (logger.isLoggable(Level.WARNING))
                logger.warning(e.toString());
            return new VanillaDbJdbcResultSet(false, "");
        }
    }

}
