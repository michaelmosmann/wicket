What are Wicket Models?
============================
.. toctree::
   :maxdepth: 3

In Wicket, a model holds a value for a component to display and/or edit. How exactly this value is held is determined by a given model's implementation of the :ref:`IModel <models--imodel-label>` interface. The ``IModel`` interface decouples a component from the *model object* which forms its value. This in turn decouples the whole Wicket framework from any and all details of model storage, such as the details of a given persistence technology. As far as Wicket itself is concerned, a model is anything that implements the ``IModel`` interface, no matter how it might do that. Although there are some refinements described below, conceptually, ``IModel`` looks like this:

.. _models--imodel-label:

.. literalinclude:: ../../../../wicket-core/src/main/java/org/apache/wicket/model/IModel.java
	:start-after: */
	:lines: 40-

The ``IModel`` interface defines a simple contract for getting and setting a value. The nature of the Object retrieved or set will depend on the component referencing the model. For a ``Label`` component, the value must be something which can be converted to a ``String`` (see :doc:`converter`) which will be displayed when the label is rendered. For a ``ListView``, it must be a ``java.util.List`` containing the values to be displayed as a list.

Different frameworks implement the model concept differently. Swing has a number of component-specific model interfaces. Struts requires that the model be a Java Bean and there is no explicit model interface. The ``IModel`` interface in Wicket allows models to be generic (as in Struts) but it can do things that would not be possible if components accessed their model directly (as in Swing). For example, Wicket applications can use or provide ``IModel`` implementations that read a model value from a resource file or retrieve a model value from a database only when needed.

The use of a single model interface (as compared to having multiple interfaces, or having no model interface at all) has a number of advantages:

* Wicket provides ``IModel`` implementations you can use with any component. These models can do things such as retrieve the value from a resource file, or read and write the value from a Java Bean property.
* Wicket also provides ``IModel`` implementations that defer retrieving the value until it is actually needed, and remove it from the servlet Session when the request is complete. This reduces session memory consumption and is particularly useful with large values such as lists.
* Unlike Swing, you do not have to implement an extra interface or helper class for each different component. Especially for the most often used components such as ``Labels`` and ``TextFields`` you can easily bind to a bean property.
* In many cases you can provide the required value directly to the component and it will wrap a default model implementation around it for you.
* And while you do not have to use beans as your models as you must with Struts, you may still easily use beans if you wish. Wicket provides the appropriate model implementations.


Simple Models
-------------

The HelloWorld example program demonstrates the simplest model type in Wicket:

.. includecode:: ../../../helloworld/src/main/java/org/apache/wicket/reference/helloworld/HelloWorld.java#docu
	:tabsize: 2

The constructor for this page constructs a ``Label`` component. The first parameter to the ``Label`` component's constructor is the Wicket id, which associates the ``Label`` with a tag in the HelloWorld.html markup file:

.. includecode:: ../../../helloworld/src/main/java/org/apache/wicket/reference/helloworld/HelloWorld.html
	
The second parameter to the ``Label`` component's constructor is the model data for the Label, providing content that replaces any text inside the ``<span>`` tag to which the ``Label`` is associated. The model data passed to the ``Label`` constructor above is apparently a String. Internally ``Label`` creates a Model for the String. :ref:`Model<models--model-label>` is a simple default implementation of IModel.


.. todo:: replace with real code

.. _models--model-label:

Thus instead we could have created our label this way::

	add(new Label("message", new Model<String>("Hello World!")));
	
or::

	add(new Label("message", Model.of("Hello World!")));


The ``Label`` constructor that takes a ``String`` is simply a convenience.



Dynamic Models
--------------

The data we gave to the model in the previous example, the string "Hello World", is constant. No matter how many times Wicket asks for the model data, it will get the same thing. Now consider a slightly more complex example::

	Label name = new Label ("name", Model.of(person.getName()));
	
The model data is still a String, the value of ``person.getName()`` is set at the time the model is created. Recall that Java strings are immutable: this string will never change. Even if ``person.getName()`` would later return a different value, the model data is unchanged. So the page will still display the old value to the user even if it is reloaded. Models like this, whose values never change, are known as *static* models.

In many cases the underlying data can change, and you want the user to see those changes. For example, the user might use a form to change a person's name. Models which can automatically reflect change are known as *dynamic* models. While the :ref:`Model<models--model-label>` class is static, most of the other core Wicket model classes are dynamic.

It's instructive to see how to make a dynamic model by subclassing Model.

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/dynamic/CustomModelFormPage.java#customModel

It would be inconvenient to have to do this for every component that needs a dynamic model. Instead, you can use the :ref:`PropertyModel<models--propertymodel-label>` class or one of the other classes described below.

.. _models--propertymodel-label:

Property Models
---------------

The PropertyModel class allows you to create a model that accesses a particular property of its associated model object at runtime. This property is accessed using a simple expression language with a dot notation (e.g. ``'name'`` means property ``'name'``, and ``'person.name'`` means property name of object person). The PropertyModel constructor looks like:

.. literalinclude:: ../../../../wicket-core/src/main/java/org/apache/wicket/model/PropertyModel.java
	:start-after: */
	:end-before: {
	:lines: 90-
		
which takes a model object and a property expression. When the property model is asked for its value by the framework, it will use the property expression to access the model object's property. For example, if we have a Java Bean or "POJO" (Plain Old Java Object) like this:

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/dynamic/Person.java#classOnly

then the property expression "name" can be used to access the "name" property of any Person object via the ``getName()`` getter method.

.. todo:: replace with real code

::

	personForm.add(new RequiredTextField("personName", new PropertyModel(person, "name")));

Nested property expressions are possible as well. You can access sub-properties via reflection using a dotted path notation, which means the property expression ``'person.name'`` is equivalent to calling ``getPerson().getName()`` on the given model object.

.. warning::

	If the Field is accesible and has the same name, the ``PropertyModel`` would try to access the field first.

There are three principal reasons why you might use PropertyModel instead of Model:

* PropertyModel instances are dynamic
* the property expression language is more compact than the analogous Java code
* it's much simpler to create a property model than to subclass Model












Compound Property Models
------------------------

Compound models allow containers to share models with their children. This saves memory, but more importantly, it makes replication of models much cheaper in a clustered environment. The basic idea is that the contained components usually want model data that can be easily derived at runtime from the model of their container. So, give the contained components no explicit model, and when a model is needed, Wicket will search up the containment hierarchy for a compound model. The compound model can retrieve model data for any of its contained components.

``CompoundPropertyModel`` is the most commonly used compound model. An instance of this class uses the name of the contained component as a property expression to retrieve data from its own model data.

To use a ``CompoundPropertyModel``, simply set one as the model for a container, such as a Form or a Page. Create the contained components with no model of their own. Insure that the component identifier names match the appropriate property names.

Here's a simple example using a ``CompoundPropertyModel``. Suppose we have a Person class, with two properties: Name and Age. We want a simple form for the user to edit a Person.

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/compound/CompoundModelPanel.java#form

.. note::

	A complete working example would require a save button and so forth but the use of a compound model doesn't change those.

The component name can in fact be a more complicated property expression. Suppose for example that the Person class also has an address property, of class Address, and that class in turn has a city property. To define this field in the form we can do this:

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/compound/CompoundModelPanel.java#addressCity

The corresponding input field in the html must have a wicket id of ``'address.city'``. This works, but it does expose the internal structure of the model data in the html. ``CompoundPropertyModel`` has a method that can be used to rectify this.

The model associates a different property expression with the component being bound.

.. todo:: replace with real code

::

	public <S> IModel<S> bind(String property)
	
With this association in place the child component can have whatever name we like, rather than having the match the property expression.

To use ``CompoundPropertyModel.bind`` for the city field discussed above we might do something like this:

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/compound/CompoundModelBindPanel.java#bind
	
Also, note that if you are using a component that you do not want to reference the compound property model, but is a child of the form, that you define a model for that component. For example:

.. todo:: replace with real code

::

	// throws exception
	personForm.add(new Label("non-compound-model-reference"));
	// does not throw an exception
	personForm.add(new Label("non-compound-model-reference", new Model<String>()));
















Wrapped Object Models
---------------------

.. todo:: IMHO this is not the best way to explain and implement this concept

It is possible to create Models that explicitly define in normal java code what is to be returned as the model object for each property within the object being wrapped. So instead of specifying via a string the name of the property to fetch the value you from the specification is done in Java.

While creating Model's in this pattern takes longer (more classes) than using Reflection based PropertyModels it prevents the problems that can occur when critical functionality is defined in String based context that most IDE's do not refactor properly.

It also helps with readability when the models are added to components to be able to easily see the types involved.

These are the Address and Person classes used in the previous examples:

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/Address.java#classOnly

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/Person.java#classOnly

The first step is to create a Wrapped Object Model for the Address and Person classes:

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/AddressModel.java#classOnly

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/PersonModel.java#classOnly

Notice how each wrapped model contains an inner model that contains the actual pojo instance. This allows for the wrapped model to be a plain Model or a LoadableDetachableModel, or even another wrapped model where its .getObject() results in a suitably typed input value (see the "address.city" field in the example below).

At this point to create a form using our wrapped object models looks like:

.. todo:: IMHO this needs refactoring (improve generic type handling)

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/WrappedModelFormPage.java#form

A wrapped object model also makes working with DataTables's easier as one IColumn implementation can be written for each object class which makes the declaration of the table much simpler.

e.g.

.. todo:: IMHO refactoring needed

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/PersonTableColumn.java#classOnly
	
So the table could be declared like:

.. todo:: IMHO refactoring needed

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/WrappedModelFormPage.java#datatable
  
Another option with the complex object is to create a custom ``IConverter`` that will take in this case the Address instance from the PersonModel and render the string value as the city name.

e.g.

.. todo:: IMHO refactoring needed

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/CustomLabel.java#classOnly


With the populate from above as:

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/wrapped/PersonTableColumnWithCustomLabel.java#refactor





Resource Models
---------------

ResourceModel
^^^^^^^^^^^^^

Localization of resources in Wicket is supported by the ``Localizer`` class. This provides a very convenient way to retrieve and format application-wide or component-specific resources according to the Locale that the user's Session is running under. You can retrieve a String using Localizer and use the result as a model object, but it is usually more convenient to use one of Wicket's resource model classes.

ResourceModel is the simplest of these. It simply takes a resource key and uses it to determine the model object (by looking for the resource in a property file). Thus for a example a label can be created this way:

.. todo:: replace with real code

::

	add(new Label("greetings", new ResourceModel("label.greetings")));
	
.. note:: 

	Note however that you can also accomplish the same thing using the ``wicket:message`` tag in the html.

ResourceModel has another constructor which takes an additional string to be used as a default if the resource is not found.

The model objects created via ResourceModel vary according to the locale and the contents of the resource bundle, but they don't otherwise depend on the program state. For example, the greeting message produced above doesn't contain the user's name. More dynamic models can be constructed using StringResourceModel.

Note that the ResourceModel, like other models that use the ComponentStringResourceLoader, takes two attempts to load each key it's looking for. I.e., in addition to the various style/locale/component-path retries, it looks using both a relative path and an absolute path. To try & clarify using the example above, it will look for both the property "greetings.label.greetings" (assuming the Label is directly added to the page) as well as the absolute "label.greetings" key. If the label was part of a form, it could be "formName.greetings.label.greetings" (etc...).

StringResourceModel
^^^^^^^^^^^^^^^^^^^

StringResourceModels have a resource key, a Component, an optional model and an optional array of parameters that can be passed to the Java MessageFormat facility. The constructors look like this:

.. todo:: replace with real code

::

	public StringResourceModel(final String resourceKey, final Component component, final IModel model)
	public StringResourceModel(final String resourceKey, final Component component, final IModel model, final Object[] parameters)
	
The resourceKey is used to find the resource in a property file. The property file used depends on the component.

A very simple example of a StringResourceModel might be a Label constructed like this:

.. todo:: replace with real code

::

	add(new Label("greetings", new StringResourceModel("label.greetings", this, null)));
	
where the resource bundle for the page contains an entry like this:

.. code-block:: properties

	label.greetings=Welcome!
	
This label is essentially the same as that constructed with ResourceModel above. However, with StringResourceModel we can add the name from a User object to the greeting. We pass the user object as the model parameter to the constructor. Wicket will find the localized string in the resource bundle, find any substrings within it of the form ${propertyexpression}, and replace these substrings with their values as property expressions. The model is used to evaluate the property expressions.

For example, suppose we wanted to add the name from a User object to the greeting above. We'd say:

.. todo:: replace with real code

::

	User user;
	...
	add(new Label("greetings", new StringResourceModel("label.greetings", this, new Model(user))));
	
and have a resource like:

.. code-block:: properties

	label.greetings=Welcome, ${name}!
	
where User has a getName() method exposing its "name" property.

Note that StringResourceModel is dynamic: the property expression is re-evaluated every time getObject() is called. In contrast, if the application code called Localizer itself and used the resulting string as model data, the result would be a static (non-changing) model.








Detachable Models
-----------------

At the end of each request cycle the active page is serialized into a byte array and stored for the next request (some in the session and some to disk: see IPageStore for more info). The entire object graph of the page including all components and their associated models are included in generated byte stream (byte array). Once you get past all but the most trivial of applications this object graph becomes huge and the time required to serialize it becomes a bottle neck. Keeping the size of the serialized pages low will increase the number of concurrent users your application can support. It will also minimize the amount of time required to fail over to another cluster node and the bandwidth to keep the sessions replicated.

The Wicket solution to this problem is to create the concept of detachability where by at the end of the request cycle all the components contained in a page are recursively detached (See Component.detatch()) which dramatically reduces the size of the object graph being serialized. Each component in turn then detaches its internal state including the value of its default model by calling the IModel.detach() method.

So a Detachable Model is an IModel that releases most of its object graph when you call detatch() retaining just enough detail (typically a unique identifier) that can be used to reconstitute the object on the next call to getObject() which will occur on the next page render.

Two commonly used built-in Wicket IModel implementations are:

1. **AbstractReadOnlyModel**
2. **LoadableDetachableModel** (also commonly referred to as an **LDM**)

It is also possible to create a custom IModel implementation that mirrors the LoadableDetchableModel inner workings but also supporting IModel.setObject().

Managing the lifecycle of non default detachable IModel's
^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

Wicket provides the detachability hooks and will automatically detach the default model of each component. However its common to use more than a single IModel implementation per component especially when using nested or chaining models. In this case the developer is responsible for registering their custom models to participate in the detachment process.

The best way to handle this registration is to make sure it occurs in the same scope as where the IModel implementation was instantiated.

For example:

.. todo:: replace with real code

::

	public class MyPanel extends Panel {

		private final DetatchableModelA detachableModelA;

	 public MyPanel (String id) {
		     super(id);

		     this.detachableModelA = new DetachableModelA();

		     add (new CustomFormPanel ("formPanel", new CListModelFromA (this.detachableModelA)));
		 }

		 @Override
		 public void detach() {

		   // remember to call the parent detach logic
		   super.detach();

		   this.detachableModelA.detach();

		 }
	}
	
Here you see that the DetachableModelA instance is created in the MyPanel constructor and then passed in as the inner model to the CListModelFromA model which will load a List<C> instances based on the details of the A instance contained in the model. Because the this.detachableModelA instance could possibly be shared like this in many sub component models its important that the CListModelFromA implementation does not try and call detach() on it but rather leave the detachment to occur in the same scope that the model was instantiated. In this case the detachment is done in the MyPanel.detach.

AbstractReadOnlyModel
^^^^^^^^^^^^^^^^^^^^^

When you subclass AbstractReadOnlyModel you implement the:

.. todo:: replace with real code

::

	public abstract T getObject();

So long as your subclass does not contain any private fields then no business data will be serialized when the the request cycle ends. The model object value is computed at the time the method is called and not cached anywhere.

For example:

.. todo:: replace with real code

::

	public class PersonNameModel extends AbstractReadOnlyModel<String> {

		  private final IModel<Person>personModel;

		  public PersonNameModel (IModel<Person>personContainingModel> personModel) {
		      this.personModel = personModel;
		  }

		  public String getObject() {
		      Person p = this.personModel.getObject();

		      if (p == null)
		          return "";

		      return p.getName();
		  }

	}
	
You can see it takes in an existing IModel that contains the Person object and that the getObject() method extracts the name of the person.

LoadableDetachableModel
^^^^^^^^^^^^^^^^^^^^^^^

This class extends AbstractReadOnlyModel and finalizes the IModel.getObject() method requiring you to implement:

.. todo:: replace with real code

::

	protected abstract T load();
	
This method is called once per request cycle, the first time that the getObject() method is invoked. The T object value is then cached in a transient private field and used as the return value to all subsequent calls to getObject().

For example:

.. todo:: replace with real code

::

	class LoadablePersonModel extends LoadableDetachableModel<Person> {

		Long id;

		LoadablePersonModel(Long id)	 {
			this.id = id;
		}

		@Override
		protected Person load() {
			return DataManager.getPersonFromDatabase(id);
		}
	}
	
When getObject() is called for the first time on a given instance of this class, it will call load() to retrieve the data. At this point the data is said to be attached. Subsequent calls to getObject() will return the attached data. At some point (after the page has been rendered) this data will be removed (the internal references to it will be set to null). The data is now detached. If getObject() is subsequently called (for example, if the page is rendered again), the data will be attached again, and the cycle starts anew.

Read/Write Detachable Model
^^^^^^^^^^^^^^^^^^^^^^^^^^^

Here is an example of how to create a model that is loadable and detachable but also supports using the IModel.setObject(T value) to store a different value into the model.

.. todo:: replace with real code

::

	public class LoadableDetachableAttributeModel implements
			IModel<Attribute> {


		private transient Attribute cachedAttribute = null;
		      private transient boolean attached = false;
	
		private String attributeName = null;

		      private final AttributeService service;

		public LoadableDetachableAttributeModel (AttributeService service) {
		          this.service = service;
		      }
		  

		public LoadableDetachableAttributeModel (AttributeService service, Attribute defaultValue) {
			    this (service);
		            if (defaultValue != null) {
		                 setObject(defaultValue);
		            }
		}
	
		/* (non-Javadoc)
		 * @see org.apache.wicket.model.IDetachable#detach()
		 */
		@Override
		public void detach() {

		              attached = false;
			cachedAttribute = null;
		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.model.IModel#getObject()
		 */
		@Override
		public Attribute getObject() {

		              if (!attached) {
		              
		                  // load the attribute
		                  attached = true;
		                  
		                  if (attributeName != null) {

		                     // load the attribute from the service
		                     this.cachedAttribute = service.loadAttribute (this.attributeName);
		                  }
		              }
		              return this.cachedAttribute;

		}

		/* (non-Javadoc)
		 * @see org.apache.wicket.model.IModel#setObject(java.lang.Object)
		 */
		@Override
		public void setObject(Attribute object) {

		              this.attached = true;

			if (object == null) {
				    this.attributeName = null;
		                    this.cachedAttribute = null;			
			}
			else {
				attributeName = object.getName();
		                      this.cachedAttribute = object;
			}		
		
		}


Chaining models
---------------

Suppose you want a model that is both a loadable model and a compound model, or some other combination of the model types described above. One way to achieve this is to chain models, like this:

.. todo:: replace with real code

::

	CompoundPropertyModel personModel = new CompoundPropertyModel(new LoadablePersonModel(personId));
	
.. note:: here LoadablePersonModel is a subclass of CompoundPropertyModel, as described earlier

The model classes in the core Wicket distribution support chaining where it make sense.


More about the IModel interface
-------------------------------

.. warning:: This is outdated.

We are now in a position to understand the complete IModel interface:

::

	public interface IModel extends IDetachable
	{
		public Object getNestedModel();
		public Object getObject(final Component component);
		public void setObject(final Component component, final Object object);
	}
	
IModel extends IDetachable, which means that all models must provide a method public void detach(). Some model classes (Model for one) provide an empty, no-op implementation.

In some cases model instances form a natural hierarchy. For example, several CompoundPropertyModels share the same model object. The getNestedModel() method will return this common shared object. In cases where there is no such object it returns null.

Compound models are also the motivation for the component parameters to getObject and setObject. Several components may share the same CompoundPropertyModel object. If getObject (or setObject) is called it must return something different depending on the component (e.g., evaluate the appropriate property expression). Thus these two methods must be passed a component as a parameter.

The IModel interface was simplified in Wicket 2.0:

::

	public interface IModel<T> extends IDetachable
	{
		T getObject();
		void setObject(final T object);
	}
	
The get and set methods do not take a component argument anymore. Instead, Wicket 2.0 has specialized model interfaces to do with specific issues like recording the 'owning' component of a model. See IAssignmentAwareModel and IInheritableModel (though you typically don't need to know these interfaces directly).

Another change is that IModel does now support generics. This is especially interesting when authoring custom components where you allow only models (compile time) that produce a certain type. ListView for instance only accepts models that produces instances of java.util.List.












Refactor Safe Property Models
-----------------------------

Annotation processors
^^^^^^^^^^^^^^^^^^^^^

There are a number of annotation processors that generate a meta data that can be used to build safe property models. Examples of such processors:

* https://github.com/42Lines/metagen MetaGen
* http://bindgen.org/ Bindgen together with http://code.google.com/p/bindgen-wicket/ Bindgen-Wicket module
* Hibernate metamodel processor's metamodel can be used for this purpose with a custom model implementation


LambdaJ
^^^^^^^

With a little bit of help from the LambdaJ project we can stop using fragile PropertyModels.

::

	/* www.starjar.com
	 * Copyright (c) 2011 Peter Henderson. All Rights Reserved.
	 * Licensed under the Apache License, Version 2.0
	 */
	package com.starjar.wicket;

	import ch.lambdaj.function.argument.Argument;
	import ch.lambdaj.function.argument.ArgumentsFactory;
	import org.apache.wicket.model.PropertyModel;

	public class ModelFactory {

		/**
		 * Use with the on() function from Lambdaj to have refactor safe property models.
		 *
		 * e.g.
		 * <pre>
		 * import static com.starjar.wicket.ModelFactory.*;
		 * import static ch.lambdaj.Lambda.*;
		 *
		 * Contact contact = getContactFromDB();
		 *
		 * Label l = new Label("id", model(contact, on(Contact.class).getFirstName()));
		 *
		 *
		 * </pre>
		 *
		 * OR
		 *
		 * <pre>
		 * import static com.starjar.wicket.ModelFactory.*;
		 * import static ch.lambdaj.Lambda.*;
		 *
		 * ContactLDM contactLDM = new ContactLDM(contactId);
		 *
		 * Label l = new Label("id", model(contactLDM, on(Contact.class).getFirstName()));
		 * </pre>
		 *
		 * Works as expected for nested objects
		 *
		 * <pre>
		 * Label l = new Label("address", model(contactLDM, on(Contact.class).getAddress().getLine1()));
		 * </pre>
		 *
		 *
		 * @param <T> Type of the model value
		 * @param value
		 * @param proxiedValue
		 * @return
		 */
		public static <T> PropertyModel<T> model(Object value, T proxiedValue) {
		  Argument<T> a = ArgumentsFactory.actualArgument(proxiedValue);
		  String invokedPN = a.getInkvokedPropertyName();
		  PropertyModel<T> m = new PropertyModel<T>(value, invokedPN);
		  return m;
		}
	}
	
Which can then be used

::

	import static ch.lambdaj.Lambda.*;
	import static com.starjar.wicket.ModelFactory.*;


	public class MyPanel extends Panel {
		public MyPanel(String id) {
		  Label l = new Label("firstName", model(contact, on(Contact.class).getFirstName());
		  add(l);
		  Label addr = new Label("address", model(contact, on(Contact.class).getAddress().getLine1());
		  add(addr);
		}
	}







Ignore Following Stuff
----------------------





Models are a important part of any wicket application. Despite it's simple interface its a complex topic. But let's start with some easy examples.

A very simple Model
-------------------

There is a simple model implementation, which can hold any data, which is serializable (see :ref:`models--detach-label`). This implementation implements two methods from the IModel interface for interacting with the model value.

.. includecode:: ../../../models/src/main/java/org/apache/wicket/reference/models/SerializableModelPage.java#docu

This examples shows an easy way to create a model instance for a value and how the value can be changed afterwards. The ``Label`` component accepts any serializable model value (not only strings, see :doc:`converter`).

TODO
-------------------

.. todo:: custom detach
.. todo:: cascading models

.. _models--detach-label:

Model and detach (TODO)
-----------------------

As any page contains mainly components and models. Most data is stored in models, it is important to know, that models are detached after the page is rendered (see :doc:`requestcycle`).  to remove anything from the page which is not needed anymore. 


