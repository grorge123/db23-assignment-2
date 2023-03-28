package org.vanilladb.bench.server.param.as2;

import org.vanilladb.core.sql.DoubleConstant;
import org.vanilladb.core.sql.IntegerConstant;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureHelper;

import java.util.Random;

public class UpdateItemPriceParamHelper implements StoredProcedureHelper {
    private int numOfItems = 0;
    private int[] updateId = new int[10];
    private double[] updateValue = new double[10];
    private static Random rand = new Random();
    @Override
    public void prepareParameters(Object... pars) {
        int indexCnt = 0;
        numOfItems = (Integer) pars[indexCnt++];
        for(int i = 0 ; i < 10 ; i++){
            updateId[i] = (Integer) pars[indexCnt++];
        }
        for(int i = 0 ; i < 10 ; i++){
            updateValue[i] = (Double) pars[indexCnt++];
        }
    }

    @Override
    public Schema getResultSetSchema() {
        Schema sch = new Schema();
        return sch;
    }

    public SpResultRecord newResultSetRecord() {
        SpResultRecord rec = new SpResultRecord();
        return rec;
    }

    @Override
    public boolean isReadOnly() {
        return false;
    }

    public int getNumberOfItems(){
        return numOfItems;
    }
    public int getRandomId(int idx){
        return updateId[idx];
    }
    public double getUpdateValue(int idx){
        return updateValue[idx];
    }
}
