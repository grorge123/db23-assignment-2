package org.vanilladb.bench.server.procedure.as2;

import org.vanilladb.bench.server.param.as2.ReadItemProcParamHelper;
import org.vanilladb.bench.server.param.as2.UpdateItemPriceParamHelper;
import org.vanilladb.bench.server.procedure.StoredProcedureHelper;
import org.vanilladb.core.query.algebra.Scan;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;
import org.vanilladb.core.storage.tx.Transaction;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;

public class UpdateItemPriceTxnProc extends StoredProcedure<UpdateItemPriceParamHelper> {

    public UpdateItemPriceTxnProc() {
        super(new UpdateItemPriceParamHelper());
    }
    private static Random rand = new Random();
    private static Logger logger = Logger.getLogger(As2CheckDatabaseProc.class.getName());
    @Override
    protected void executeSql() {
        if (logger.isLoggable(Level.INFO))
            logger.info("Checking database for the as2 benchmarks...");

        UpdateItemPriceParamHelper paramHelper = getParamHelper();
        Transaction tx = getTransaction();

        for(int i = 0 ; i < 10 ; i++){
            int id = rand.nextInt(paramHelper.getNumberOfItems());
            double updateValue = rand.nextDouble() * 5.0;
            String selectSql = "SELECT i_price FROM item WHERE i_id = " + id;
            Scan scan = StoredProcedureHelper.executeQuery(selectSql, getTransaction());
            scan.beforeFirst();
            if(!scan.next()){
                if (logger.isLoggable(Level.SEVERE))
                    logger.severe(String.format("%d not found.", id));
                abort("random wrong value");
            }
            double oldPrice = (Double) scan.getVal("i_price").asJavaVal();
            double newPrice = 0;
            scan.close();
            if(oldPrice > As2BenchConstants.MAX_PRICE){
                newPrice = As2BenchConstants.MIN_PRICE;
            }else{
                newPrice = oldPrice + updateValue;
            }
            String updateSql = "UPDATE item SET i_price=" + newPrice + "WHERE i_id=" + id;
            StoredProcedureHelper.executeUpdate(updateSql, tx);
//            if (logger.isLoggable(Level.INFO))
//                logger.info(String.format("[Proc] ID:%d update price from %f to %f.\n", id, oldPrice, newPrice));
        }


        if (logger.isLoggable(Level.INFO))
            logger.info("Checking completed.");
    }
}
