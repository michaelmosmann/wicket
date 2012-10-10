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
package org.apache.wicket.core.util.io.check;

import org.apache.wicket.WicketRuntimeException;
import org.apache.wicket.core.util.io.IPrettyPrinter;

public class TypesNotAllowedSerializableCheck extends AbstractSerializableCheck
{
	private final Class<?>[] types;

	public TypesNotAllowedSerializableCheck(Class<?>... types)
	{
		this.types = types;
	}

	@Override
	public void check(Object obj, IPrettyPrinter printer) throws WicketRuntimeException
	{
		for (Class<?> type : types)
		{
			if (type.isInstance(obj))
				failed(obj, printer, "type " + type + " not allowed");
		}
	}

}
