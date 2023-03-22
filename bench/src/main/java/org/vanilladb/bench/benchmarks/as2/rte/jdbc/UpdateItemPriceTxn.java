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
import org.vanilladb.bench.server.param.as2.ReadItemProcParamHelper;


public class UpdateItemPriceTxn implements JdbcJob {
    private static Logger logger = Logger.getLogger(ReadItemTxnJdbcJob.class
            .getName());
    @Override
    public SutResultSet execute(Connection conn, Object[] pars) throws SQLException {
        return new VanillaDbJdbcResultSet(false, "");
    }

}
