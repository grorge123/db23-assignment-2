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

public class UpdateItemPriceTxn implements JdbcJob {
    private static Logger logger = Logger.getLogger(ReadItemTxnJdbcJob.class
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

            StringBuilder debugMsg = new StringBuilder("");
            for(int i = 0 ; i < 10 ; i++){
                int id = rand.nextInt(paramHelper.getNumberOfItems());
                debugMsg.append(String.format("ID:%d' change price from ", id));
                double updateValue = rand.nextDouble(0.0, 5.0);
                String selectSql = "SELECT i_price FROM item WHERE i_id = " + id;
                rs = statement.executeQuery(selectSql);
                rs.beforeFirst();
                double oldPrice = 0, newPrice = 0;
                if (rs.next()) {
                    oldPrice = rs.getDouble("i_price");
                    debugMsg.append(String.format("%f to", oldPrice));
                } else
                    throw new RuntimeException("cannot find the record with i_id = " + id);
                rs.close();
                if(oldPrice > As2BenchConstants.MAX_PRICE){
                    newPrice = As2BenchConstants.MIN_PRICE;
                }else{
                    newPrice = oldPrice + updateValue;
                }
                debugMsg.append(String.format("%f.", newPrice));
                String updateSql = "UPDATE item SET i_price=" + oldPrice + "WHERE i_id=" + id;
                int cnt = statement.executeUpdate(updateSql);
                debugMsg.append("\n");
            }
            logger.info(debugMsg.toString());
            return new VanillaDbJdbcResultSet(true, "Success");
        } catch (Exception e) {
            if (logger.isLoggable(Level.WARNING))
                logger.warning(e.toString());
            return new VanillaDbJdbcResultSet(false, "");
        }
    }

}
