/**
 * Copyright © 2012 Akiban Technologies, Inc.  All rights reserved.
 * 
 * This program and the accompanying materials are made available
 * under the terms of the Eclipse Public License v1.0 which
 * accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 * 
 * This program may also be available under different license terms.
 * For more information, see www.akiban.com or contact licensing@akiban.com.
 * 
 * Contributors:
 * Akiban Technologies, Inc.
 */

package com.persistit.stress;

import com.persistit.Configuration;
import com.persistit.Persistit;
import com.persistit.Transaction.CommitPolicy;
import com.persistit.stress.unit.AccumulatorRestart;

public class AccumulatorRestartSuite extends AbstractSuite {

    static String name() {
        return AccumulatorRestartSuite.class.getSimpleName();
    }

    public static void main(final String[] args) throws Exception {
        new AccumulatorRestartSuite(args).runTest();
    }

    public AccumulatorRestartSuite(final String[] args) {
        super(name(), args);
    }

    @Override
    public void runTest() throws Exception {

        deleteFiles(substitute("$datapath$/persistit*"));

        add(new AccumulatorRestart("repeat=100"));

        final Configuration config = makeConfiguration(16384, "1000", CommitPolicy.SOFT);
        config.setCheckpointInterval(AccumulatorRestart.CHECKPOINT_INTERVAL);
        final Persistit persistit = new Persistit();
        persistit.initialize(config);
        try {
            execute(persistit);
        } finally {
            persistit.close();
        }
    }
}