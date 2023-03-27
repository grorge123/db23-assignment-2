package org.vanilladb.bench.benchmarks.as2.rte;

import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;
import org.vanilladb.bench.benchmarks.as2.As2BenchTransactionType;
import org.vanilladb.bench.rte.TxParamGenerator;

public class UpdateItemParamGen implements TxParamGenerator<As2BenchTransactionType> {

    @Override
    public As2BenchTransactionType getTxnType() {
        return As2BenchTransactionType.UPDATE_ITEM;
    }

    @Override
    public Object[] generateParameter() {
        // [# of items]
        return new Object[] {As2BenchConstants.NUM_ITEMS};
    }
}
