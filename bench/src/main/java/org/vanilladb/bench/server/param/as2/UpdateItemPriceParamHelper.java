package org.vanilladb.bench.server.param.as2;

import org.vanilladb.core.sql.DoubleConstant;
import org.vanilladb.core.sql.IntegerConstant;
import org.vanilladb.core.sql.Schema;
import org.vanilladb.core.sql.Type;
import org.vanilladb.core.sql.VarcharConstant;
import org.vanilladb.core.sql.storedprocedure.SpResultRecord;
import org.vanilladb.core.sql.storedprocedure.StoredProcedureHelper;

public class UpdateItemPriceParamHelper implements StoredProcedureHelper {
    private int numOfItems = 0;

    @Override
    public void prepareParameters(Object... pars) {
        numOfItems = (Integer) pars[0];
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
}
