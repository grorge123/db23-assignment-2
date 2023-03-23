package org.vanilladb.bench.server.procedure.as2;

import org.vanilladb.bench.server.param.as2.ReadItemProcParamHelper;
import org.vanilladb.bench.server.param.as2.UpdateItemPriceParamHelper;
import org.vanilladb.core.sql.storedprocedure.StoredProcedure;

public class UpdateItemPriceTxnProc extends StoredProcedure<UpdateItemPriceParamHelper> {

    public UpdateItemPriceTxnProc() {
        super(new UpdateItemPriceParamHelper());
    }
    @Override
    protected void executeSql() {

    }
}
