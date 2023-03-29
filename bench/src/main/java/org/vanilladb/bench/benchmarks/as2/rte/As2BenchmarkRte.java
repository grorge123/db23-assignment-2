/*******************************************************************************
 * Copyright 2016, 2018 vanilladb.org contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package org.vanilladb.bench.benchmarks.as2.rte;

import org.vanilladb.bench.StatisticMgr;
import org.vanilladb.bench.VanillaBench;
import org.vanilladb.bench.benchmarks.as2.As2BenchTransactionType;
import org.vanilladb.bench.remote.SutConnection;
import org.vanilladb.bench.rte.RemoteTerminalEmulator;
import org.vanilladb.bench.util.BenchProperties;

import java.util.Random;
import java.util.logging.Logger;


public class As2BenchmarkRte extends RemoteTerminalEmulator<As2BenchTransactionType> {
	
	private As2BenchmarkTxExecutor readExecutor;
	private As2BenchmarkTxExecutor updateExecutor;
	private static final double READ_WRITE_TX_RATE;

	static {
		READ_WRITE_TX_RATE = BenchProperties.getLoader()
				.getPropertyAsDouble(As2BenchmarkRte.class.getName() + ".READ_WRITE_TX_RATE", 0.6);
	}

	public As2BenchmarkRte(SutConnection conn, StatisticMgr statMgr, long sleepTime) {
		super(conn, statMgr, sleepTime);
		readExecutor = new As2BenchmarkTxExecutor(new As2ReadItemParamGen());
		updateExecutor = new As2BenchmarkTxExecutor(new UpdateItemParamGen());
	}
	private static Random rand = new Random();
	protected As2BenchTransactionType getNextTxType() {
		int number = rand.nextInt(100);
		As2BenchTransactionType returnValue;
		if(number > READ_WRITE_TX_RATE * 100){
			returnValue = As2BenchTransactionType.UPDATE_ITEM;
		}else{
			returnValue = As2BenchTransactionType.READ_ITEM;
		}
		return returnValue;
	}
	
	protected As2BenchmarkTxExecutor getTxExeutor(As2BenchTransactionType type) {
		switch (type){
			case READ_ITEM:
				return readExecutor;
			case UPDATE_ITEM:
				return updateExecutor;
			default:
				throw new RuntimeException("Not implement");
		}
	}
}
