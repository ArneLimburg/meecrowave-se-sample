/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package rocks.limburg.meecrowave.se;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.enterprise.inject.Instance;
import javax.enterprise.inject.se.SeContainer;
import javax.enterprise.inject.se.SeContainerInitializer;
import javax.enterprise.inject.spi.CDI;

import org.apache.meecrowave.Meecrowave;
import org.junit.jupiter.api.Test;

public class SeTest {

    @Test
    public void alternativeIsSelectedWithSe() {
        try (SeContainer container = SeContainerInitializer.newInstance().initialize()) {

            Instance<MyInterface> instance = container.select(MyInterface.class);

            assertTrue(instance.isResolvable());
            assertFalse(instance.isUnsatisfied());
            assertFalse(instance.isAmbiguous());

            MyInterface bean = instance.get();
            assertEquals(MyAlternative.class.getSimpleName(), bean.getName());
        }
    }

    @Test
    public void alternativeIsSelectedWithMeecrowave() {
        try (Meecrowave meecrowave = new Meecrowave().bake()) {

            Instance<MyInterface> instance = CDI.current().select(MyInterface.class);

            assertTrue(instance.isResolvable());
            assertFalse(instance.isUnsatisfied());
            assertFalse(instance.isAmbiguous());

            MyInterface bean = instance.get();
            assertEquals(MyAlternative.class.getSimpleName(), bean.getName());
        }
    }
}
