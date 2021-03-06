/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package cz.zcu.kiv.crce.it.example;

import cz.zcu.kiv.crce.it.IntegrationTestBase;
import static org.junit.Assert.assertEquals;
import static org.ops4j.pax.exam.CoreOptions.*;

import java.io.IOException;

import cz.zcu.kiv.crce.it.Options.Felix;
import cz.zcu.kiv.crce.it.Options.Osgi;
import org.apache.felix.dm.Component;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.Option;
import org.ops4j.pax.exam.Configuration;
import org.osgi.service.packageadmin.PackageAdmin;

import org.ops4j.pax.exam.junit.PaxExam;

/**
 * This class serves as a minimal example of our integration tests. Also, if this test fails, something is likely
 * wrong with the environment
 */
@RunWith(PaxExam.class)
public class ExampleIT extends IntegrationTestBase {

    @Configuration
    public Option[] configuration() throws Exception {
        return options(
            // you can add additional directives, e.g. systemProperty or VMOptions here
            junitBundles(),
            Felix.dependencyManager(),
            Osgi.compendium()
        );
    }

    @Override
    protected void before() throws IOException {
        // configure the services you need; you cannot use the injected members yet
    }

    @SuppressWarnings("deprecation")
    @Override
    protected Component[] getDependencies() {
        return new Component[] {
                // create Dependency Manager components that should be started before the
                // test starts.
                createComponent()
                    .setImplementation(this)
                    .add(createServiceDependency()
                        .setService(PackageAdmin.class)
                        .setRequired(true))
        };
    }

    // You can inject services as usual.
    @SuppressWarnings("deprecation")
    private volatile PackageAdmin packageAdmin;

    @Test
    public void exampleTest() {
        assertEquals("Hey, who stole my package!", 0, packageAdmin.getExportedPackage("org.osgi.framework").getExportingBundle().getBundleId());
    }

}
