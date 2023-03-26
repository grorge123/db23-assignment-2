package org.vanilladb.bench.benchmarks.as2.rte;

import org.vanilladb.bench.benchmarks.as2.As2BenchConstants;
import org.vanilladb.bench.benchmarks.as2.As2BenchTransactionType;
import org.vanilladb.bench.rte.TxParamGenerator;
import org.vanilladb.bench.util.RandomValueGenerator;

import java.util.ArrayList;

public class UpdateItemParamGen implements TxParamGenerator<As2BenchTransactionType> {

    @Override
    public As2BenchTransactionType getTxnType() {
        return As2BenchTransactionType.UPDATE_ITEM;
    }

    @Override
    public Object[] generateParameter() {
        // [# of items]
        RandomValueGenerator rvg = new RandomValueGenerator();
        ArrayList<Object> paramList = new ArrayList<Object>();
        paramList.add(As2BenchConstants.NUM_ITEMS);
        for(int i = 0 ; i < 10 ; i++){
            paramList.add(rvg.number(1, As2BenchConstants.NUM_ITEMS));
        }
        for(int i = 0 ; i < 10 ; i++){
            paramList.add(rvg.randomDoubleIncrRange(0, 5, 0.000001));
        }
        return paramList.toArray(new Object[0]);
    }
}
