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
package org.apache.wicket.core.util.io;

import java.io.IOException;
import java.io.Serializable;

import junit.framework.Assert;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.util.io.SerializableChecker.WicketNotSerializableException;
import org.apache.wicket.core.util.io.check.AttachedModelsShouldNotApearInSerializableCheck;
import org.apache.wicket.core.util.io.check.SerializableChecks;
import org.apache.wicket.core.util.io.check.TypesNotAllowedSerializableCheck;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.junit.Test;

/**
 * Test some standard cases which this class should detect
 * 
 * @see org.apache.wicket.util.io.SerializableCheckerTest
 * @author mosmann
 */
public class SerializableCheckerTest implements Serializable
{
	/**
	 * check a not serializable bean which should throw an exception
	 * 
	 * @throws IOException
	 */
	@Test(expected = WicketNotSerializableException.class)
	public void notSerializableBean() throws IOException
	{
		checkWith(new SimpleBean());
	}

	@Test(expected = WicketNotSerializableException.class)
	public void beanIsNotSerializable() throws IOException
	{
		checkWith(new SimpleBean());
	}

	@Test
	public void beanIsSerializable() throws IOException
	{
		checkWith(new SerialBean());
	}

	@Test(expected = WicketRuntimeException.class)
	public void customTypeIsNotSerializable() throws IOException
	{
		checkWith(new CouldBeADomainType());
	}

	@Test(expected = WicketRuntimeException.class)
	public void notDetachedLDM() throws IOException
	{
		IModel<String> model = new LoadableDetachableModel<String>()
		{
			@Override
			protected String load()
			{
				return "dont care about the value here";
			}
		};
		Assert.assertNotNull(model.getObject());
		checkWith(new CouldBeAPanel("panel", model));
	}


	private void checkWith(Object object) throws IOException
	{
		new SerializableChecker(check()).writeObject(object);
	}

	private ISerializableCheck check()
	{
		return new SerializableChecks(new AttachedModelsShouldNotApearInSerializableCheck(),
			new TypesNotAllowedSerializableCheck(DoNotSerializeMe.class));
	}

	public static class SimpleBean
	{

	}


	static class SerialBean implements Serializable
	{
		String name = "killer";
	}

	static class CouldBeADomainType implements Serializable, DoNotSerializeMe
	{

	}

	static class CouldBeAPanel implements Serializable
	{
		final Object someValue;

		public CouldBeAPanel(String id, Object someValue)
		{
			this.someValue = someValue;
		}
	}

	static interface DoNotSerializeMe
	{

	}
}
