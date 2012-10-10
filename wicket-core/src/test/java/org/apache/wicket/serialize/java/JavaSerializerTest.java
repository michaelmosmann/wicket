/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.wicket.serialize.java;

import java.io.Serializable;

import junit.framework.Assert;

import org.apache.wicket.core.util.io.ISerializableCheck;
import org.apache.wicket.core.util.io.check.AttachedModelsShouldNotApearInSerializableCheck;
import org.apache.wicket.core.util.io.check.SerializableChecks;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.junit.Test;

public class JavaSerializerTest implements Serializable
{
	@Test
	public void testSomeStuff()
	{
		ISerializableCheck serializableCheck = new SerializableChecks(
			new AttachedModelsShouldNotApearInSerializableCheck());
		JavaSerializer javaSerializer = new JavaSerializer("debugApp", serializableCheck);

		IModel<String> model = new LoadableDetachableModel<String>()
		{
			@Override
			protected String load()
			{
				return "do not care about this value";
			}
		};

		// should work as expected, because model is in detached state
		Assert.assertNotNull(javaSerializer.serialize(new SomeBeam(model)));

		// the call to getObject will attach the model
		Assert.assertNotNull("get a value and attach", model.getObject());

		// now serializing fails because there is an undetached model
		Assert.assertNull(javaSerializer.serialize(new SomeBeam(model)));
	}

	public static class SomeBeam implements Serializable
	{

		final IModel<String> model;

		public SomeBeam(IModel<String> model)
		{
			this.model = model;
		}
	}
}
