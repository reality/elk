/*
 * #%L
 * elk-reasoner
 * 
 * $Id$
 * $HeadURL$
 * %%
 * Copyright (C) 2011 Oxford University Computing Laboratory
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
/**
 * @author Yevgeny Kazakov, May 15, 2011
 */
package org.semanticweb.elk.reasoner.taxonomy;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;
import java.util.TreeSet;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.log4j.Logger;
import org.semanticweb.elk.owl.implementation.ElkObjectFactoryImpl;
import org.semanticweb.elk.owl.interfaces.ElkClass;
import org.semanticweb.elk.owl.interfaces.ElkObjectFactory;
import org.semanticweb.elk.owl.iris.ElkIri;
import org.semanticweb.elk.owl.predefined.PredefinedElkClass;
import org.semanticweb.elk.owl.util.Comparators;
import org.semanticweb.elk.util.collections.Operations;
import org.semanticweb.elk.util.collections.Operations.Condition;

/**
 * Class taxonomy that is suitable for concurrent processing. It also implements
 * method for the bottom node.
 * 
 * @author Yevgeny Kazakov
 * @author Frantisek Simancik
 * 
 */
class ConcurrentClassTaxonomy implements Taxonomy<ElkClass>,
		TaxonomyNode<ElkClass> {

	// logger for events
	private static final Logger LOGGER_ = Logger
			.getLogger(ConcurrentClassTaxonomy.class);

	/* thread safe map from class IRIs to class nodes */
	protected final ConcurrentMap<ElkIri, NonBottomNode> nodeLookup;
	/* thread safe set of all nodes */
	protected final Set<TaxonomyNode<ElkClass>> allNodes;
	/* boolean to guard access to the set of all nodes */
	protected final AtomicBoolean processingNewNodes;
	/* counts the number of nodes which have non-bottom sub-classes */
	protected final AtomicInteger countNodesWithSubClasses;
	/* thread safe set of unsatisfiable classes */
	protected final Set<ElkClass> unsatisfiableClasses;

	ConcurrentClassTaxonomy() {
		this.nodeLookup = new ConcurrentHashMap<ElkIri, NonBottomNode>();
		this.allNodes = Collections
				.newSetFromMap(new ConcurrentHashMap<TaxonomyNode<ElkClass>, Boolean>());
		allNodes.add(this);
		this.processingNewNodes = new AtomicBoolean(false);
		this.countNodesWithSubClasses = new AtomicInteger(0);
		this.unsatisfiableClasses = Collections
				.synchronizedSet(new TreeSet<ElkClass>(
						Comparators.ELK_CLASS_COMPARATOR));
		this.unsatisfiableClasses.add(PredefinedElkClass.OWL_NOTHING);
	}

	@Override
	public Set<TaxonomyNode<ElkClass>> getNodes() {
		return Collections.unmodifiableSet(allNodes);
	}

	/**
	 * Returns the IRI of the given ELK class.
	 * 
	 * @return the IRI of the given ELK class
	 */
	static ElkIri getKey(ElkClass elkClass) {
		return elkClass.getIri();
	}

	/**
	 * Get non-bottom node assigned to the given {@link ElkClass}, or
	 * <tt>null</tt> if none is assigned.
	 * 
	 * @param elkClass
	 *            the class for which to find the node non-bottom
	 * @return the non-bottom node for the given {@link ElkClass}
	 */
	protected NonBottomNode getNonBottomNode(ElkClass elkClass) {
		return nodeLookup.get(getKey(elkClass));
	}

	final ElkObjectFactory objectFactory = new ElkObjectFactoryImpl();

	/**
	 * Obtain a ClassNode object for a given {@link ElkClass}, <tt>null</tt> if
	 * none assigned
	 * 
	 * @param elkClass
	 * @return ClassNode object for elkClass, possibly still incomplete
	 */
	@Override
	public TaxonomyNode<ElkClass> getNode(ElkClass elkClass) {
		TaxonomyNode<ElkClass> result = getNonBottomNode(elkClass);
		if (result != null)
			return result;
		if (unsatisfiableClasses.contains(elkClass))
			return this;
		LOGGER_.error("No taxonomy node for class "
				+ elkClass.getIri().asString());
		return null;
	}

	NonBottomNode getCreate(Collection<ElkClass> members) {

		ElkClass someMember = null;
		for (ElkClass member : members) {
			someMember = member;
			break;
		}

		NonBottomNode previous = nodeLookup.get(getKey(someMember));
		if (previous != null)
			return previous;

		NonBottomNode node = new NonBottomNode(this, members);
		// we assign first for the node to the canonical member to avoid
		// concurrency problems
		ElkClass canonical = node.getCanonicalMember();
		previous = nodeLookup.putIfAbsent(getKey(canonical), node);
		if (previous != null)
			return previous;

		allNodes.add(node);
		if (LOGGER_.isTraceEnabled()) {
			LOGGER_.trace(canonical + ": node created");
		}
		for (ElkClass member : members) {
			if (member != canonical)
				nodeLookup.put(getKey(member), node);
		}
		return node;
	}

	/* functions required by the {@link ClassNode} representing the bottom node */

	@Override
	public Set<ElkClass> getMembers() {
		return unsatisfiableClasses;
	}

	@Override
	public ElkClass getCanonicalMember() {
		return PredefinedElkClass.OWL_NOTHING;
	}

	@Override
	public Set<TaxonomyNode<ElkClass>> getDirectSuperNodes() {
		return Operations.filter(allNodes,
				new Condition<TaxonomyNode<ElkClass>>() {
					public boolean holds(TaxonomyNode<ElkClass> element) {
						return element.getDirectSubNodes().contains(
								ConcurrentClassTaxonomy.this);
					}
					/*
					 * the direct super nodes of the bottom node are all nodes
					 * except the nodes that have no non-bottom sub-classes and
					 * the bottom node
					 */
				}, allNodes.size() - countNodesWithSubClasses.get() - 1);
	}

	@Override
	public Set<TaxonomyNode<ElkClass>> getAllSuperNodes() {
		/* all nodes except this one */
		return Operations.filter(allNodes, new Condition<Object>() {
			@Override
			public boolean holds(Object element) {
				return element != this;
			}
		}, allNodes.size() - 1);
	}

	@Override
	public Set<TaxonomyNode<ElkClass>> getDirectSubNodes() {
		return Collections.emptySet();
	}

	@Override
	public Set<TaxonomyNode<ElkClass>> getAllSubNodes() {
		return Collections.emptySet();
	}

	@Override
	public Taxonomy<ElkClass> getTaxonomy() {
		return this;
	}

}